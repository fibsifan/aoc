plugins {
	kotlin("jvm") version "2.2.20"
	idea
}

repositories {
	mavenCentral()
}

dependencies {
	testImplementation(platform("org.junit:junit-bom:5.13.4"))
	testImplementation(kotlin("test"))
	testImplementation("org.junit.jupiter:junit-jupiter-params")
}

kotlin {
	jvmToolchain(21)
}

tasks {
	wrapper {
		gradleVersion = "9.0.0"
		distributionType = Wrapper.DistributionType.ALL
	}

    test {
        useJUnitPlatform()
    }
}
