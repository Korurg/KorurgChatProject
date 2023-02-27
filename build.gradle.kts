plugins {
    id("java")
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("org.liquibase.gradle") version "2.1.1"
}

subprojects {
    apply {
        plugin("java")
        plugin("org.liquibase.gradle")
    }

    repositories {
        mavenCentral()
    }

    tasks.test {
        useJUnitPlatform()
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter:2.7.9")
        implementation("org.springframework.boot:spring-boot-starter-web:2.7.9")
        implementation("org.springframework.boot:spring-boot-starter-thymeleaf:2.7.9")
        implementation("org.springframework.boot:spring-boot-starter-websocket:2.7.9")
        implementation("org.springframework.cloud:spring-cloud-starter-openfeign:3.1.5")
        implementation("org.springframework.boot:spring-boot-starter-validation:2.7.9")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.9")

        implementation("io.projectreactor:reactor-core:3.5.0")
        implementation("org.apache.commons:commons-lang3:3.12.0")
        implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.13.0")
        implementation("org.jetbrains:annotations:24.0.0")
        implementation("org.springdoc:springdoc-openapi-ui:1.6.14")
        implementation("commons-io:commons-io:2.11.0")


        compileOnly("org.mapstruct:mapstruct:1.5.3.Final")
        annotationProcessor("org.mapstruct:mapstruct-processor:1.5.3.Final")

        implementation("io.sentry:sentry-spring-boot-starter:6.14.0")
        implementation("io.sentry:sentry-logback:6.14.0")

        compileOnly("org.projectlombok:lombok:1.18.24")
        annotationProcessor("org.projectlombok:lombok:1.18.24")
        annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

        testImplementation("org.springframework.boot:spring-boot-starter-test:2.7.9")
        testImplementation("org.junit.vintage:junit-vintage-engine:5.9.1")
        testImplementation("org.assertj:assertj-core:3.22.0")

        liquibaseRuntime("org.liquibase:liquibase-core:4.18.0")
        runtimeOnly("org.liquibase:liquibase-core:4.18.0")
    }
}