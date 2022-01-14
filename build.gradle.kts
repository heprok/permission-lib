import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version Versions.SPRING_BOOT_VERSION
    id("io.spring.dependency-management") version Versions.SPRING_DEPENDENCY_MANAGEMENT

    kotlin("jvm") version Versions.KOTLIN_VERSION
    kotlin("kapt") version Versions.KOTLIN_VERSION
    kotlin("plugin.spring") version Versions.KOTLIN_VERSION

    `java-library`
    `maven-publish`
}

group = "com.briolink"
version = "0.1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // FasterXML
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    kapt("jakarta.annotation:jakarta.annotation-api")

    // Kotlin
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))

    // kotlin-logging
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11

    withJavadocJar()
    withSourcesJar()
}

// publishing {
//    publications {
//        create<MavenPublication>("maven") {
//            from(components["java"])
//        }
//    }
//
//    repositories {
//        maven {
//            name = "Gitlab"
//            url = uri("https://gitlab.com/api/v4/projects/29889174/packages/maven")
//
//            authentication {
//                create<HttpHeaderAuthentication>("header")
//            }
//
//            credentials(HttpHeaderCredentials::class) {
//                name = "Deploy-Token"
//                value = System.getenv("GITLAB_DEPLOY_TOKEN")
//            }
//        }
//        mavenLocal()
//    }
// }

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}