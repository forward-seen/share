package com.shine.share.oss;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * S3 存储协议调用 所有兼容S3协议的云厂商均支持
 *
 * @author 辛凤文
 * @since 1.0
 */
@Component
public class OssClient {

    private final OssSettings ossSettings;

    private final AmazonS3 client;

    public OssClient(OssSettings ossSettings) {
        this.ossSettings = ossSettings;
        try {
            AwsClientBuilder.EndpointConfiguration endpointConfig =
                    new AwsClientBuilder.EndpointConfiguration(ossSettings.getEndpoint(), ossSettings.getRegion());
            AWSCredentials credentials = new BasicAWSCredentials(ossSettings.getAccessKey(), ossSettings.getSecretKey());
            AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
            AmazonS3ClientBuilder build = AmazonS3Client.builder()
                    .withEndpointConfiguration(endpointConfig)
                    .withCredentials(credentialsProvider)
                    .disableChunkedEncoding()
                    .enablePathStyleAccess();
            this.client = build.build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to use oss settings!", e);
        }
    }

    public ObjectListing listObjects(String prefix, String marker, Integer maxKeys) {
        ListObjectsRequest request = new ListObjectsRequest(ossSettings.getBucketName(), prefix, marker, "/", maxKeys);
        return client.listObjects(request);
    }

    public List<OssObject> listObjects(String prefix) {
        return listObjects(prefix, "/");
    }

    public List<OssObject> listObjects(String prefix, String delimiter) {
        List<OssObject> objects = new ArrayList<>();
        List<OssObject> directories = new ArrayList<>();

        ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request()
                .withBucketName(ossSettings.getBucketName())
                .withPrefix(prefix)
                .withDelimiter(delimiter);
        ListObjectsV2Result objectsResult;
        do {
            objectsResult = client.listObjectsV2(listObjectsRequest);
            objects.addAll(objectsResult.getObjectSummaries().stream().map(OssObject::new).toList());
            directories.addAll(objectsResult.getCommonPrefixes().stream().map(OssObject::new).toList());

            listObjectsRequest.setContinuationToken(objectsResult.getNextContinuationToken());
        } while (objectsResult.isTruncated());

        directories.addAll(objects);
        return directories;
    }

    public void upload(byte[] data, String path, String contentType) {
        upload(new ByteArrayInputStream(data), path, contentType);
    }

    public void upload(InputStream inputStream, String path, String contentType) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            metadata.setContentLength(inputStream.available());
            PutObjectRequest putObjectRequest = new PutObjectRequest(ossSettings.getBucketName(), path, inputStream, metadata);
            // 设置上传对象的 Acl 为公共读
            putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);

            client.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file!", e);
        }
    }

    public OssFile download(String key) {
        try {
            S3Object object = client.getObject(ossSettings.getBucketName(), key);
            OssFile ossFile = new OssFile();
            ossFile.setContentLength(object.getObjectMetadata().getContentLength());
            ossFile.setContentType(object.getObjectMetadata().getContentType());
            ossFile.setInputStream(new BufferedInputStream(object.getObjectContent()));
            return ossFile;
        } catch (Exception e) {
            return null;
        }
    }

    public byte[] downloadFolder(String prefix) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zipOut = new ZipOutputStream(baos)) {
            long zipSize = 0;
            String bucketName = ossSettings.getBucketName();
            Long maxFileSize = ossSettings.getMaxFileSize();
            List<OssObject> items = listObjects(prefix, null);
            for (OssObject item : items) {
                String key = item.getKey();
                ZipEntry zipEntry = new ZipEntry(key.substring(prefix.length()));
                zipOut.putNextEntry(zipEntry);

                S3Object object = client.getObject(bucketName, key);
                BufferedInputStream inputStream = new BufferedInputStream(object.getObjectContent());
                byte[] buffer = new byte[4096];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    if (zipSize + length > maxFileSize) {
                        throw new RuntimeException("Exceeds file size limit(byte): " + maxFileSize);
                    }
                    zipOut.write(buffer, 0, length);
                    zipSize += length;
                }

                zipOut.closeEntry();
                inputStream.close();
            }
        }
        return baos.toByteArray();
    }

    public void deleteObject(OssObject obj) {
        try {
            String bucketName = ossSettings.getBucketName();
            if (obj.getIsObject()) {
                client.deleteObject(bucketName, obj.getKey());
            } else {
                ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request()
                        .withBucketName(bucketName)
                        .withPrefix(obj.getKey());
                ListObjectsV2Result objectsResult;
                do {
                    objectsResult = client.listObjectsV2(listObjectsRequest);
                    for (S3ObjectSummary objectSummary : objectsResult.getObjectSummaries()) {
                        client.deleteObject(bucketName, objectSummary.getKey());
                    }

                    listObjectsRequest.setContinuationToken(objectsResult.getNextContinuationToken());
                } while (objectsResult.isTruncated());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete file!", e);
        }
    }

    public void deleteObject(String key) {
        String bucketName = ossSettings.getBucketName();
        client.deleteObject(bucketName, key);
    }

    public void deleteObjects(List<OssObject> objs) {
        for (OssObject obj : objs) {
            deleteObject(obj);
        }
    }

    public void deleteObjects(String[] keys) {
        List<DeleteObjectsRequest.KeyVersion> keyVersions = Arrays.stream(keys)
                .map(DeleteObjectsRequest.KeyVersion::new).collect(Collectors.toList());
        String bucketName = ossSettings.getBucketName();
        DeleteObjectsRequest request = new DeleteObjectsRequest(bucketName);
        request.setKeys(keyVersions);
        client.deleteObjects(request);
    }

    public long sumFileSize(String prefix) {
        long totalSize = 0;

        ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request()
                .withBucketName(ossSettings.getBucketName())
                .withPrefix(prefix);
        ListObjectsV2Result objectsResult;
        do {
            objectsResult = client.listObjectsV2(listObjectsRequest);
            for (S3ObjectSummary objectSummary : objectsResult.getObjectSummaries()) {
                totalSize += objectSummary.getSize();
            }
            listObjectsRequest.setContinuationToken(objectsResult.getNextContinuationToken());
        } while (objectsResult.isTruncated());

        return totalSize;
    }

    public void createBucket(String bucketName) {
        if (!client.doesBucketExistV2(bucketName)) {
            client.createBucket((bucketName));
        }
    }

    public String getObjectUrl(String key) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(ossSettings.getBucketName(), key);
        generatePresignedUrlRequest.setMethod(HttpMethod.GET);
        ResponseHeaderOverrides responseHeaderOverrides = new ResponseHeaderOverrides();
        responseHeaderOverrides.setContentType(getFileContentType(key));
        responseHeaderOverrides.setCacheControl("max-age=31536000");
        generatePresignedUrlRequest.setResponseHeaders(responseHeaderOverrides);

        URL url = client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    private String getFileContentType(String key) {
        String fileSuffix = key.substring(key.lastIndexOf("."));
        return switch (fileSuffix) {
            case ".jpeg", ".png", ".jpg" -> "image/jpeg";
            case ".mp4" -> "video/mp4";
            case ".html" -> "text/html";
            case ".css" -> "text/css";
            case ".js" -> "application/javascript";
            case ".pdf" -> "application/pdf";
            case ".doc", ".docx" -> "application/msword";
            default -> "application/octet-stream";
        };
    }
}
