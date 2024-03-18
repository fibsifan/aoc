plugins {
    kotlin("jvm") version "1.9.23"
}

repositories {
    mavenCentral()
}
dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(17)
}

tasks {
    wrapper {
        gradleVersion = "8.1.1"
        distributionType = Wrapper.DistributionType.ALL
    }
}

tasks.test {
    useJUnitPlatform()
}
