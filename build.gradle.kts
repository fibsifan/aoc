plugins {
	kotlin("jvm") version "2.1.10"
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
		gradleVersion = "8.13"
		distributionType = Wrapper.DistributionType.ALL
	}

    test {
        useJUnitPlatform()
    }
}
