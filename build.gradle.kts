import io.izzel.taboolib.gradle.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    id("io.izzel.taboolib") version "2.0.9"
    id("org.jetbrains.kotlin.jvm") version "1.9.22"
}

taboolib {
    env {
        // 安装模块
        install(KETHER, DATABASE, METRICS, UI)
        install(NMS, NMS_UTIL)
        install(UNIVERSAL)
        install(BUKKIT_ALL)
        install(EXPANSION_REDIS)
        relocate("com.github.benmanes.caffeine", "top.blackcat.mc.libs.caffeine")
    }
    version { taboolib = "6.1.0" }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("ink.ptms.core:v12004:12004:mapped")
    compileOnly("ink.ptms.core:v12004:12004:universal")
    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))
    taboo("com.github.ben-manes.caffeine:caffeine:2.9.3")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = listOf("-Xjvm-default=all", "-Xextended-compiler-checks")
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    sourceSets.all {
        languageSettings {
            languageVersion = "2.0"
        }
    }
}