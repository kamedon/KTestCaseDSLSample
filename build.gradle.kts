import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    kotlin("jvm") version "1.5.31"
    application
}

group = "com.kamedon"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        val credentials = loadProperties("$rootDir/github.properties")
        url = uri("https://maven.pkg.github.com/Kamedon/KTestCaseDSL")
        credentials {
            username = credentials.getProperty("user")
            password = credentials.getProperty("password")
        }
    }
}

dependencies {
    implementation("com.kamedon:ktestcasedsl:0.3.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}

fun loadProperties(fileName: String): Properties {
    val props = Properties()
    props.load(file(fileName).inputStream())
    return props
}