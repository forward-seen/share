param (
    [string]$newVersion
)

# 定义 pom.xml 文件位置列表
$pomFiles = @(
    "..\share-api\pom.xml",
    "..\share-applications\pom.xml",
    "..\share-auth\pom.xml",
    "..\share-common\pom.xml",
    "..\share-common\share-core\pom.xml",
    "..\share-common\share-core\share-protocol\pom.xml",
    "..\share-common\share-manager\pom.xml",
    "..\share-common\share-starter\pom.xml",
    "..\share-common\share-starter\oss-spring-boot-starter\pom.xml",
    "..\share-domain\pom.xml",
    "..\share-gateway\pom.xml",
    "..\share-system\pom.xml",
    "..\pom.xml"
)

foreach ($pomFile in $pomFiles) {
    if (Test-Path $pomFile) {
        Write-Host "Updating version in $pomFile to $newVersion"

        $content = Get-Content -Path $pomFile -Encoding UTF8
        $updated = $false

        for ($i = 0; $i -lt $content.Length; $i++) {
            if ($content[$i] -match '<version>.*?</version>') {
                $content[$i] = $content[$i] -replace '<version>.*?</version>', "<version>$newVersion</version>"
                $updated = $true
                break
            }
        }

        if ($updated) {
            $content | Set-Content -Path $pomFile -Encoding UTF8
            Write-Host "Updated version in $pomFile"
        } else {
            Write-Host "No version tag found in $pomFile"
        }
    } else {
        Write-Host "File not found: $pomFile"
    }
}

Write-Host "Update completed."
