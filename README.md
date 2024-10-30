# JavetBuddy

[![Maven Central](https://img.shields.io/maven-central/v/com.caoccao.javet.buddy/javet-buddy?style=for-the-badge)](https://central.sonatype.com/artifact/com.caoccao.javet.buddy/javet-buddy) [![Discord](https://img.shields.io/discord/870518906115211305?label=join%20our%20Discord&style=for-the-badge)](https://discord.gg/R4vvKU96gw)

[![Build](https://github.com/caoccao/JavetBuddy/actions/workflows/build.yml/badge.svg)](https://github.com/caoccao/JavetBuddy/actions/workflows/build.yml)

JavetBuddy is [Javet](https://github.com/caoccao/Javet) + [ByteBuddy](https://bytebuddy.net/). JavetBuddy enhances JVM via bytecode by JavaScript.

## Features

* Inject ad-hoc non-final Java Class by JavaScript
* Extend non-final Java Class by JavaScript
* Call `$super` in Derived Class

## Dependencies

### Maven

```xml
<dependency>
    <groupId>com.caoccao.javet.buddy</groupId>
    <artifactId>javet-buddy</artifactId>
    <version>0.4.0</version>
</dependency>
<dependency>
    <groupId>net.bytebuddy</groupId>
    <artifactId>byte-buddy</artifactId>
    <version>1.15.5</version>
</dependency>
```

### Gradle Kotlin DSL

```kotlin
implementation("com.caoccao.javet.buddy:javet-buddy:0.4.0")
implementation("net.bytebuddy:byte-buddy:1.15.5")
```

### Gradle Groovy DSL

```groovy
implementation 'com.caoccao.javet.buddy:javet-buddy:0.4.0'
implementation 'net.bytebuddy:byte-buddy:1.15.5'
```

## Documentation

* [Access the Whole JVM](https://www.caoccao.com/Javet/tutorial/advanced/access_the_whole_jvm.html)
* [Dynamic: Anonymous Object for Class](https://www.caoccao.com/Javet/reference/converters/proxy_converter.html#dynamic-anonymous-object-for-class)
* [Development](docs/development.md)
* [Release Notes](docs/release_notes.md)

## License

[APACHE LICENSE, VERSION 2.0](LICENSE)
