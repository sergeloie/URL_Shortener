plugins {
	application
	checkstyle
	jacoco
	alias(libs.plugins.freefairLombokPLugin)
	alias(libs.plugins.benManesVersionsPlugin)
//	alias(libs.plugins.johnrengelmanShadowPlugin)
	alias(libs.plugins.patrikerdesUseLatestVersionsPlugin)
	alias(libs.plugins.littlerobotsVersionCatalogUpdatePlugin)
	alias(libs.plugins.springFrameworkPlugin)
	alias(libs.plugins.springDependencyManagementPlugin)
	alias(libs.plugins.sonarCubePlugin)
//	alias(libs.plugins.sentryPlugin)
}

group = "ru.anseranser"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	runtimeOnly(libs.h2)
	runtimeOnly(libs.postgresql)

	compileOnly(libs.lombok)

	annotationProcessor(libs.lombok)
	annotationProcessor(libs.mapstruct.annotation.processor)

	testImplementation(libs.bundles.junitBundle)
	testImplementation(platform(libs.junitBom))
	testImplementation(libs.bundles.springTest)

	implementation(libs.bundles.springUrlShortener)
	implementation(libs.jakarta.persistence.api)
	implementation(libs.mapstruct)
	implementation(libs.jackson.databind.nullable)
	implementation(libs.datafaker)
	implementation(libs.instancio)
	implementation(libs.postgresql)

//	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
//	implementation("org.springframework.boot:spring-boot-starter-web")
//	compileOnly("org.projectlombok:lombok")
//	annotationProcessor("org.projectlombok:lombok")
//	testImplementation("org.springframework.boot:spring-boot-starter-test")
//	testImplementation("org.springframework.boot:spring-boot-testcontainers")
//	testImplementation("org.testcontainers:junit-jupiter")
//	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
