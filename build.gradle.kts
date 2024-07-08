plugins {
    kotlin("jvm") version "1.9.24"
    idea
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
        gradleVersion = "8.8"
        distributionType = Wrapper.DistributionType.ALL
    }
}

tasks.test {
    useJUnitPlatform()
}
