plugins {
	kotlin("jvm") version "2.4.20"
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
	jvmToolchain(25)
}

tasks {
	wrapper {
		gradleVersion = "9.8.0"
		distributionType = Wrapper.DistributionType.ALL
	}

    test {
        useJUnitPlatform()
    }
}
