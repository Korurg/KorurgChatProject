plugins {
    id("java-library")
    id("org.springframework.boot") version "2.7.5"
    id("org.liquibase.gradle") version "2.1.1"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

dependencies {
    api ("org.xerial:sqlite-jdbc:3.40.1.0")
    api ("org.springframework.boot:spring-boot-starter-jdbc:2.7.9")
    api ("com.github.gwenn:sqlite-dialect:0.1.2")

    liquibaseRuntime( "org.liquibase:liquibase-core:4.18.0")
    runtimeOnly ("org.liquibase:liquibase-core:4.18.0")
}