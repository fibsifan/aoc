plugins {
	kotlin("jvm") version "2.4.0"
}

repositories {
	mavenCentral()
}

dependencies {
	testImplementation(platform(libs.junit.bom))
	testImplementation(kotlin("test"))
	testImplementation(libs.junit.params)
}

kotlin {
	jvmToolchain(21)
}

tasks {
	wrapper {
		gradleVersion = "9.6.1"
		distributionType = Wrapper.DistributionType.ALL
	}

    test {
        useJUnitPlatform()
    }
}
