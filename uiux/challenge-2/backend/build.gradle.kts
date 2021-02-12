import groovy.lang.GroovyObject
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "2.2.2.RELEASE"
  id("io.spring.dependency-management") version "1.0.7.RELEASE"
  kotlin("jvm") version "1.3.50"
  kotlin("plugin.spring") version "1.3.50"
  id("jacoco")
  `maven-publish`
  application
}

group = "amaze.us"
java.sourceCompatibility = JavaVersion.VERSION_11
application.mainClassName = "amaze.us.ColonyKeplerApplicationKt"

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation(kotlin("stdlib-jdk8"))
  implementation("org.springframework.boot:spring-boot-starter") {
    exclude(module = "spring-aop")
  }
  implementation("org.springframework.boot:spring-boot-starter-web") {
    exclude(module = "spring-boot-starter-tomcat")
  }
  implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-undertow")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("io.springfox:springfox-swagger2:2.6.1")
  implementation("io.springfox:springfox-swagger-ui:2.6.1")
  implementation("io.jsonwebtoken:jjwt:0.9.1")
  implementation("javax.xml.bind:jaxb-api:2.3.1")
  implementation("ch.qos.logback:logback-core:1.2.3")
  implementation("ch.qos.logback:logback-classic:1.2.3")
  implementation("ch.qos.logback:logback-access:1.2.3")
  implementation("net.logstash.logback:logstash-logback-encoder:6.2")
  testImplementation("org.springframework.boot:spring-boot-starter-test") {
    exclude(module = "junit")
    exclude(module="junit-vintage-engine")
    exclude(module = "mockito-core")
  }
  testImplementation("org.springframework.security:spring-security-test")
  testImplementation("org.testcontainers:mongodb:1.15.0")
  testImplementation("com.ninja-squad:springmockk:2.0.2")
  testImplementation("com.github.tomakehurst:wiremock:2.20.0")
  testImplementation("io.mockk:mockk:1.9.3")
  testImplementation("org.junit.jupiter:junit-jupiter:5.4.2")
  testImplementation("org.junit.jupiter:junit-jupiter-api")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.test {
  useJUnitPlatform()
  testLogging {
    showStandardStreams = true
    exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    events("skipped", "failed")
  }
}

tasks.jacocoTestReport {
  reports {
    xml.isEnabled = true
    html.isEnabled = true
  }
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "11"
  }
}

tasks.withType<JacocoCoverageVerification> {
  afterEvaluate {
    classDirectories.setFrom(files(classDirectories.files.map {
      fileTree(it).apply {
        exclude("**/ColonyKeplerApplication**")
      }
    }))
  }
}

tasks.withType<JacocoReport> {
  afterEvaluate {
    classDirectories.setFrom(files(classDirectories.files.map {
      fileTree(it).apply {
        exclude("**/ColonyKeplerApplication**")
      }
    }))
  }
}

tasks.create<Exec>("dockerBuild") {
  commandLine = listOf("docker", "build", "-t", project.name, ".")
}

tasks.create<Exec>("dockerPush") {
  dependsOn("dockerBuild")
  commandLine = listOf("docker", "push", project.name)
}
