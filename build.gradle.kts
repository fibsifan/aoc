plugins {
	kotlin("jvm") version "2.0.21"
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
		gradleVersion = "8.10.2"
		distributionType = Wrapper.DistributionType.ALL
	}

    test {
        useJUnitPlatform()
    }
}
