plugins {
    id("java")
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group "korurg"
version "2023.1-BETA"

dependencies {
    implementation(project(":feature-twitch-impl"))
    implementation(project(":feature-twitch-api"))
    implementation(project(":core-chat-impl"))
    implementation(project(":core-chat-api"))
    implementation(project(":core-utils"))
    implementation(project(":db"))
    implementation(project(":settings"))
    implementation(project(":core-exceptions"))
    implementation(project(":core-localization-api"))
    implementation(project(":core-localization-impl"))
    implementation(project(":core-network-twitch-api"))
    implementation(project(":core-network-twitch-impl"))

    implementation("me.friwi:jcefmaven:107.1.9")
}

tasks.withType<Jar>{
    archiveBaseName.set("korurg-chat")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}