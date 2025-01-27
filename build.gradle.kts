plugins {
	kotlin("jvm") version "2.1.0"
	idea
}

repositories {
	mavenCentral()
}
dependencies {
	testImplementation(kotlin("test"))
}

kotlin {
	jvmToolchain(21)
}

tasks {
	wrapper {
		gradleVersion = "8.12.1"
		distributionType = Wrapper.DistributionType.ALL
	}

    test {
        useJUnitPlatform()
    }
}
