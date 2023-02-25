plugins {
    java
    `java-library`
    `maven-publish`
    signing
}

repositories {
    mavenLocal()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    // lss的库
    maven("https://lss233.littleservice.cn/repositories/minecraft")
    mavenCentral()
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.15.2-R0.1-SNAPSHOT")
}

group = "top.zoyn.particlelib"
version = "1.5.1"
description = "ParticleLib"

java{
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withSourcesJar()
    withJavadocJar()
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

tasks.javadoc {
    options.encoding = "UTF-8"
}

publishing {
    repositories {
        if (project.version.toString().endsWith("-SNAPSHOT")) {
            maven("https://s01.oss.sonatype.org/content/repositories/snapshots/"){
                credentials {
                    username = System.getenv("OSSRH_USERNAME")
                    password = System.getenv("OSSRH_PASSWORD")
                }
            }
        }else {
            maven("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/") {
                credentials {
                    username = System.getenv("OSSRH_USERNAME")
                    password = System.getenv("OSSRH_PASSWORD")
                }
            }
        }
    }

    publications {
        create<MavenPublication>("mavenJar") {
            pom {
                name.set("ParticleLib")
                description.set("ParticleLib 是一个基于 BukkitAPI 编写的粒子特效程序库。它提供极其简洁易懂的 API，使你可以毫无压力地开始验证你的绝佳创意。")
                url.set("https://github.com/602723113/ParticleLib")
                properties.set(mapOf(

                ))
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/licenses/MIT")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("zoyn")
                        name.set("Zoyn")
                        email.set("zoynnn@qq.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/602723113/ParticleLib.git")
                    developerConnection.set("scm:git:ssh://github.com/602723113/ParticleLib.git")
                    url.set("https://github.com/602723113/ParticleLib")
                }
            }
            from(components["java"])
        }
    }
}

signing {
    useInMemoryPgpKeys(System.getenv("GPG_KEY_ID"), System.getenv("GPG_KEY_SECRET"), System.getenv("GPG_KEY_PASSWORD"))
    sign(publishing.publications["mavenJar"])
}