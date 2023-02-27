plugins {
    id("java-library")
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

dependencies {
    api(project(":core-exceptions"))
}