plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("org.springframework.boot") version "4.1.0"
    id("io.spring.dependency-management") version "1.1.7"
}

application {
    mainClass.set("fr.asenka.visu.App")
}

javafx {
    version = "${project.property("version-javafx")}"
    modules("javafx.controls", "javafx.graphics", "javafx.fxml")
}

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}

dependencies {

    implementation("org.springframework.boot:spring-boot-starter:${project.property("version-spring-boot")}")

    implementation("org.openjfx:javafx-controls:${project.property("version-javafx")}")
    implementation("org.openjfx:javafx-graphics:${project.property("version-javafx")}")
    implementation("org.openjfx:javafx-fxml:${project.property("version-javafx")}")

    testImplementation("org.assertj:assertj-core:${project.property("version-assertj")}")

    testImplementation("org.junit.jupiter:junit-jupiter:${project.property("version-junit")}")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:${project.property("version-junit")}")

    compileOnly("org.projectlombok:lombok:${project.property("version-lombok")}")
    annotationProcessor("org.projectlombok:lombok:${project.property("version-lombok")}")
}

