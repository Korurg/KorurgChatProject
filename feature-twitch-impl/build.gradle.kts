plugins {
    id("java")
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

dependencies {
    implementation(project(":db"))
    implementation(project(":settings"))
    implementation(project(":core-network-twitch-api"))
    implementation(project(":feature-twitch-api"))
    implementation(project(":core-chat-api"))
}