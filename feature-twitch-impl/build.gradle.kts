plugins {
    id("java")
    id("org.liquibase.gradle") version "2.1.1"
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

dependencies {
    implementation(project(":db"))
    implementation(project(":settings"))
    implementation(project(":core-network-twitch-api"))
    implementation(project(":feature-twitch-api"))
    implementation(project(":core-chat-api"))

    liquibaseRuntime("org.liquibase:liquibase-core:4.17.2")
    runtimeOnly("org.liquibase:liquibase-core:4.17.2")
}