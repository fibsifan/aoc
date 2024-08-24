plugins {
	kotlin("jvm") version "2.0.0"
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
		gradleVersion = "8.10"
		distributionType = Wrapper.DistributionType.ALL
	}
}

tasks.test {
	useJUnitPlatform()
}
