# `plugin4j`

Plugin development kit (`PDK`) for `Java`. Defines the Plugin contract and supporting utilities to enable dynamic loading, version isolation, and automatic discovery of extensions in modular applications.

## 1.`Development`

### 1.1.`Environment`

> Add `$MAVEN_REPO` environment variable.

```shell
$ vim ~/.bashrc

# ...

# Local repo
# /home/xxx/maven/repository
MAVEN_REPO=/path/to/your/maven/local/repository

# ...

export MAVEN_REPO

# :wq!

$ source ~/.bashrc
```

```xml
<!-- @see pom.xml -->
<distributionManagement>
    <repository>
        <id>dev</id>
        <name>dev</name>
        <url>file:///${env.MAVEN_REPO}</url>
    </repository>
</distributionManagement>
```

## 1.2.`Act actions`

```shell
$ act --action-offline-mode -v -W .act/workflows/maven.act.yml -P ubuntu-latest=photowey/ubuntu:act-latest
```
