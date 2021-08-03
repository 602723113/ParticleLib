# ParticleLib
一个有趣的 Minecraft 特效类库  
[![Java CI with Maven](https://github.com/602723113/ParticleLib/actions/workflows/maven.yml/badge.svg)](https://github.com/602723113/ParticleLib/actions/workflows/maven.yml)

# 你可以拿来做什么?
你可以用这个类库来制作你想要的 Minecraft 粒子特效

# 留言
目前类库还在**初步阶段**, 如果你发现了什么 bug 或者你有更好的算法，欢迎给该项目发 pr 或 issues

# 使用方法
请点击 [Wiki](https://github.com/602723113/ParticleLib/wiki) 获得更多

# Maven
Step 1. Add the JitPack repository to your build file
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://www.jitpack.io</url>
    </repository>
</repositories>
```
Step 2. Add the dependency
```xml
<dependency>
    <groupId>com.github.602723113</groupId>
    <artifactId>ParticleLib</artifactId>
    <version>1.1.0</version>
</dependency>
```
# Gradle
Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
    repositories {
        maven { url 'https://www.jitpack.io' }
    }
}
```
Step 2. Add the dependency
```gradle
dependencies {
    implementation 'com.github.602723113:ParticleLib:1.1.0'
}
```