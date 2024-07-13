# 编码指南


## 环境
在这里，我会给你介绍如何搭建项目的开发环境。

确保已安装下列软件:

* [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
* [JDK17](https://www.oracle.com/java/technologies/downloads/)  
* Maven
* [JetBrains IDEA](https://www.jetbrains.com/idea/) 

> 对于开发使用的IDE，虽然理论上讲每个工具都可以开发，但我仍然强烈建议你使用JetBrains IDEA

## 安装

### 克隆源代码

接下来可以拉代码到你本地。

```shell
git clone git@gitee.com:forward-seen/share.git
```

### IDEA 配置

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

- Alibaba Java Coding Guidelines(XenoAmess TPM)
- Lombok


[//]: # (## 运行)




