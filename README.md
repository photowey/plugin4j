# `plugin4j`

`Plugin` development kit (`PDK`) for `Java`. Defines the Plugin contract and supporting utilities to enable dynamic loading, version isolation, and automatic discovery of extensions in modular applications.

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



## 2.`Usage`

Add this to your `pom.xml`

### 2.1.`Properties`

```xml
<version>${latest.version}</version>
```

### 2.2.`Maven`

> ⚠️ **Warning**: Direct downloading of dependencies from the Maven Central Repository is not supported yet. It is recommended to use local installation as a temporary workaround.

```shell
# Step1:
$ git clone https://github.com/photowey/plugin4j.git

# Step2:
$ cd plugin4j
$ mvn clean source:jar deploy
# make deploy
```

```xml
<dependency>
    <groupId>io.github.photowey</groupId>
    <artifactId>plugin4j-hammer</artifactId>
    <version>${plugin4j.version}</version>
</dependency>
```



## 3.`APIs`

### 3.1.`plugin.json`

- Add a `JSON-formatted` `plugin` configuration file: `plugin.json`.
- `Examples`

```json
{
    "metadata": {
        "name": "echo",
        "version": "1.0.0",
        "description": "Echo message plugin",
        "extensions": {}
    },
    "className": "io.github.photowey.plugin4j.plugin.manager.samples.EchoPlugin",
    "capabilities": [
        "sample",
        "echo"
    ],
    "classpath": []
}

```

### 3.2.`JsonConverter`

- It must implement the `JsonConverter` interface and be registered via the `Jsons.register` method.

```java
// @see io.github.photowey.plugin4j.plugin.core.converter.JsonConverter
// @see io.github.photowey.plugin4j.plugin.manager.core.json.JacksonJsonConverter
```



## 3.2.`API`

```java
// Create a temporary directory to serve as the root folder for plugins.
Path pluginsRoot = Files.createTempDirectory("plugins-root-");

// Initialize and register a JSON converter (e.g., based on Jackson) for plugin metadata serialization/deserialization.
JsonConverter converter = new JacksonJsonConverter();
Jsons.register(converter);

// Create a thread pool for asynchronous plugin operations.
ExecutorService executor = Executors.newFixedThreadPool(2);

// Initialize the plugin manager with the plugins root directory and executor service.
PluginManager manager = new DefaultPluginManager(pluginsRoot, executor);

// Discover available plugins in the plugins root and attempt to load them.
manager.discover();

// Prepare plugin execution context properties.
Map<String, Object> props = new HashMap<>();
props.put("message", "hello");

// Acquire a session for the specified plugin ("echo", version "1.0.0") and execute it.
try (PluginSession session = manager.acquire("echo", "1.0.0", props)) {
    PluginResult result = session.execute();
    // Process the result as needed...
} finally {
    // Ensure all resources (e.g., threads, file handles) are properly released.
    manager.close();
}
```



