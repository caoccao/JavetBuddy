/*
 * Copyright (c) 2024. caoccao.com Sam Cao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.gradle.internal.os.OperatingSystem

object Config {
    const val GROUP_ID = "com.caoccao.javet.buddy"
    const val NAME = "JavetBuddy"
    const val VERSION = Versions.JAVET_BUDDY
    const val URL = "https://github.com/caoccao/JavetBuddy"

    object Pom {
        const val ARTIFACT_ID = "javet-buddy"
        const val DESCRIPTION =
            "JavetBuddy is Javet + ByteBuddy. JavetBuddy enhances JVM via bytecode by JavaScript."

        object Developer {
            const val ID = "caoccao"
            const val EMAIL = "sjtucaocao@gmail.com"
            const val NAME = "Sam Cao"
            const val ORGANIZATION = "caoccao.com"
            const val ORGANIZATION_URL = "https://www.caoccao.com"
        }

        object License {
            const val NAME = "APACHE LICENSE, VERSION 2.0"
            const val URL = "https://github.com/caoccao/JavetBuddy/blob/main/LICENSE"
        }

        object Scm {
            const val CONNECTION = "scm:git:git://github.com/JavetBuddy.git"
            const val DEVELOPER_CONNECTION = "scm:git:ssh://github.com/JavetBuddy.git"
        }
    }

    object Projects {
        // https://mvnrepository.com/artifact/net.bytebuddy/byte-buddy
        const val BYTE_BUDDY = "net.bytebuddy:byte-buddy:${Versions.BYTE_BUDDY}"

        const val JAVET = "com.caoccao.javet:javet-core:${Versions.JAVET}"
        const val JAVET_NODE_LINUX_ARM64 = "com.caoccao.javet:javet-node-linux-arm64:${Versions.JAVET}"
        const val JAVET_NODE_LINUX_X86_64 = "com.caoccao.javet:javet-node-linux-x86_64:${Versions.JAVET}"
        const val JAVET_NODE_MACOS_ARM64 = "com.caoccao.javet:javet-node-macos-arm64:${Versions.JAVET}"
        const val JAVET_NODE_MACOS_X86_64 = "com.caoccao.javet:javet-node-macos-x86_64:${Versions.JAVET}"
        const val JAVET_NODE_WINDOWS_X86_64 = "com.caoccao.javet:javet-node-windows-x86_64:${Versions.JAVET}"
        const val JAVET_V8_LINUX_ARM64 = "com.caoccao.javet:javet-v8-linux-arm64:${Versions.JAVET}"
        const val JAVET_V8_LINUX_X86_64 = "com.caoccao.javet:javet-v8-linux-x86_64:${Versions.JAVET}"
        const val JAVET_V8_MACOS_ARM64 = "com.caoccao.javet:javet-v8-macos-arm64:${Versions.JAVET}"
        const val JAVET_V8_MACOS_X86_64 = "com.caoccao.javet:javet-v8-macos-x86_64:${Versions.JAVET}"
        const val JAVET_V8_WINDOWS_X86_64 = "com.caoccao.javet:javet-v8-windows-x86_64:${Versions.JAVET}"
    }

    object Versions {
        const val BYTE_BUDDY = "1.15.5"
        const val JAVA_VERSION = "1.8"
        const val JAVET = "4.0.0"
        const val JAVET_BUDDY = "0.5.0"
        const val JUNIT = "5.11.3"
    }
}

val buildDir = layout.buildDirectory.get().toString()

plugins {
    java
    `java-library`
    `maven-publish`
}

group = Config.GROUP_ID
version = Config.VERSION

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    val os = OperatingSystem.current()
    val cpuArch = System.getProperty("os.arch")
    val isArm64 = cpuArch == "aarch64" || cpuArch == "arm64";
    compileOnly(Config.Projects.JAVET)
    testImplementation(Config.Projects.JAVET)
    if (os.isLinux) {
        if (isArm64) {
            compileOnly(Config.Projects.JAVET_NODE_LINUX_ARM64)
            testImplementation(Config.Projects.JAVET_NODE_LINUX_ARM64)
            compileOnly(Config.Projects.JAVET_V8_LINUX_ARM64)
            testImplementation(Config.Projects.JAVET_V8_LINUX_ARM64)
        } else {
            compileOnly(Config.Projects.JAVET_NODE_LINUX_X86_64)
            testImplementation(Config.Projects.JAVET_NODE_LINUX_X86_64)
            compileOnly(Config.Projects.JAVET_V8_LINUX_X86_64)
            testImplementation(Config.Projects.JAVET_V8_LINUX_X86_64)
        }
    } else if (os.isMacOsX) {
        if (isArm64) {
            compileOnly(Config.Projects.JAVET_NODE_MACOS_ARM64)
            testImplementation(Config.Projects.JAVET_NODE_MACOS_ARM64)
            compileOnly(Config.Projects.JAVET_V8_MACOS_ARM64)
            testImplementation(Config.Projects.JAVET_V8_MACOS_ARM64)
        } else {
            compileOnly(Config.Projects.JAVET_NODE_MACOS_X86_64)
            testImplementation(Config.Projects.JAVET_NODE_MACOS_X86_64)
            compileOnly(Config.Projects.JAVET_V8_MACOS_X86_64)
            testImplementation(Config.Projects.JAVET_V8_MACOS_X86_64)
        }
    } else if (os.isWindows && !isArm64) {
        compileOnly(Config.Projects.JAVET_NODE_WINDOWS_X86_64)
        testImplementation(Config.Projects.JAVET_NODE_WINDOWS_X86_64)
        compileOnly(Config.Projects.JAVET_V8_WINDOWS_X86_64)
        testImplementation(Config.Projects.JAVET_V8_WINDOWS_X86_64)
    }
    compileOnly(Config.Projects.BYTE_BUDDY)
    testImplementation(Config.Projects.BYTE_BUDDY)
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use JUnit Jupiter test framework
            useJUnitJupiter(Config.Versions.JUNIT)
        }
    }
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    withSourcesJar()
    withJavadocJar()
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

tasks.jar {
    manifest {
        attributes["Automatic-Module-Name"] = Config.GROUP_ID
    }
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
    withType<Javadoc> {
        options.encoding = "UTF-8"
    }
    withType<Test> {
        systemProperty("file.encoding", "UTF-8")
    }
    withType<GenerateMavenPom> {
        destination = file("$buildDir/libs/${Config.Pom.ARTIFACT_ID}-${Config.VERSION}.pom")
    }
}

publishing {
    publications {
        create<MavenPublication>("generatePom") {
            from(components["java"])
            pom {
                artifactId = Config.Pom.ARTIFACT_ID
                description.set(Config.Pom.DESCRIPTION)
                groupId = Config.GROUP_ID
                name.set(Config.NAME)
                url.set(Config.URL)
                version = Config.VERSION
                licenses {
                    license {
                        name.set(Config.Pom.License.NAME)
                        url.set(Config.Pom.License.URL)
                    }
                }
                developers {
                    developer {
                        id.set(Config.Pom.Developer.ID)
                        email.set(Config.Pom.Developer.EMAIL)
                        name.set(Config.Pom.Developer.NAME)
                        organization.set(Config.Pom.Developer.ORGANIZATION)
                        organizationUrl.set(Config.Pom.Developer.ORGANIZATION_URL)
                    }
                }
                scm {
                    connection.set(Config.Pom.Scm.CONNECTION)
                    developerConnection.set(Config.Pom.Scm.DEVELOPER_CONNECTION)
                    tag.set(Config.Versions.JAVET)
                    url.set(Config.URL)
                }
                properties.set(
                    mapOf(
                        "maven.compiler.source" to Config.Versions.JAVA_VERSION,
                        "maven.compiler.target" to Config.Versions.JAVA_VERSION,
                    )
                )
            }
        }
    }
}
