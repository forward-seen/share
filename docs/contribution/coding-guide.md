# 编码指南


## 环境
在这里，我会给你介绍如何搭建项目的开发环境。

确保已安装下列软件:

* [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
* [JDK17](https://www.oracle.com/java/technologies/downloads/) 
* Maven
* [JetBrains IDEA](https://www.jetbrains.com/idea/) 

> 其中Maven在使用IDEA时可自动下载；
> 
> 配好环境变量，保证Java、Git以及Maven的命令可用。

## 安装

### 克隆源代码

接下来可以拉代码到你本地。

```shell
git clone git@atomgit.com:xinfengwen/share.git
```

除了Open Atom，我们在Github、Gitee、GitCode也有仓库，你可以根据你的偏好选择平台。

```shell
git clone git@github.com:forward-seen/share.git
```
```shell
git clone git@gitee.com:forward-seen/share.git
```
```shell
git clone git@gitcode.com:forward-seen/share.git
```

### IDEA 配置

使用IDEA打开项目，并且需要完成以下项目和工具的配置。

**编码统一改为UTF-8：**
> File > Settings > Editor > File Encodings

**使用IDEA默认的编码风格**
> File > Settings > Editor > Code Style

**配置类的注释模板：**
> File > Settings > Editor > File and Code Templates

选择class，interface，enum配置以下前缀：

```bazaar
#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
#parse("File Header.java")
public class ${NAME} {
}
```
Scheme选择Project；

在File Header中配置以下注释模板：

```java
/**
 * TODO to describe ${NAME}
 * 
 * @author 您的名字
 * @since 1.0 
 */
```

**安装以下插件并启用：**
> File > Settings > Plugins

- Lombok
- Alibaba Java Coding Guidelines

### 运行

- 方式一：IDEA工具运行

> share-xxx > src > main > java > com.shine.share.xxx.xxxApplication.class > 点击运行

- 方式二：使用Maven编译运行

```shell
mvn clean install

cd share-xxx/target
java -jar share-xxx-${version}.jar
```

# 贡献代码

### 提交PR

1. 前提是你需要掌握Git以及云上托管平台（Atom/Github/Gitee/GitCode）的基本使用和配置；
2. fork仓库到你的平台账户；
3. 在本地提交你代码，并push到你的平台仓库分支；
4. 在你的分支向源仓库申请PR，按照提示要求检查和填写。

> 注意：由于提交模板限制，本仓库不支持轻量级PR，且你的PR标题应与release-note新增记录保持一致。

### 发布版本 

若你是仓库管理成员，你可以标记版本。

你可以使用update_version脚本批量更新pom文件的版本号：

```shell
cd bin
.\update_version.bat 1.0.0
```

若代码中增加了新的带有pom.xml文件的模块，需要在`bin/update_version.ps1`文件中加入维护列表：

```shell
$pomFiles = @(
    "..\share-api\pom.xml",
    "..\share-applications\pom.xml",
    "..\pom.xml"
)
```

### 发布release版本

release版本应创建单独的保护分支，并以`版本号.release`形式命名分支。
