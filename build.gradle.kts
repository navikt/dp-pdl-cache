val ktorVersion = "3.1.2"

project.setProperty("mainClassName", "io.ktor.server.cio.EngineMain")

plugins {
    // Apply the org.jetbrains.kotlin.jvm plugin to add support for Kotlin.
    kotlin("jvm") version "2.1.20"

    // Apply the application plugin to add support for building a CLI application in Java.
    application

    // Apply the Ktor plugin to create the application distribution
    id("io.ktor.plugin") version "3.1.2"
}

sourceSets {
    this.getByName("main") {
        this.kotlin.srcDir("src/main/kotlin")
        this.resources.srcDir("src/main/resources")
    }
    this.getByName("test") {
        this.kotlin.srcDir("src/test/kotlin")
        this.resources.srcDir("src/test/resources")
    }
}

application {
    // Define the main class for the application.
    mainClass.set(project.property("mainClassName").toString())
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-cio:$ktorVersion")
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages:$ktorVersion")
    implementation("io.ktor:ktor-server-metrics-micrometer:$ktorVersion")
    implementation("io.micrometer:micrometer-registry-prometheus:1.14.5")

    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-jackson:$ktorVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.3")

    implementation("no.nav.security:token-validation-ktor-v3:5.0.24")

    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")

    implementation("io.github.microutils:kotlin-logging:3.0.5")
    implementation("ch.qos.logback:logback-classic:1.5.18")
    implementation("net.logstash.logback:logstash-logback-encoder:8.1")

    implementation("io.valkey:valkey-glide:1.3.2")

    testImplementation(kotlin("test"))
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("no.nav.security:mock-oauth2-server:2.1.10")
}

tasks {
    withType<ProcessResources> {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    named<Test>("test") {
        // Use JUnit Platform for unit tests.
        useJUnitPlatform()
    }

    register("runServerTest", JavaExec::class) {
        systemProperties["RUNNING_LOCALLY"] = "true"
        systemProperties["TOKEN_X_WELL_KNOWN_URL"] = "tokenx.dev.nav.no"
        systemProperties["TOKEN_X_CLIENT_ID"] = "test:dp:pdlcache"

        mainClass.set(project.property("mainClassName").toString())
        classpath = sourceSets["main"].runtimeClasspath
    }
}
