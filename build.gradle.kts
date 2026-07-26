plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

application {
    mainClass.set("fr.asenka.visu.App")
}

javafx {
    version = "21"
    modules("javafx.controls", "javafx.graphics", "javafx.fxml")
}

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation("org.openjfx:javafx-controls:21")
    implementation("org.openjfx:javafx-graphics:21")
    implementation("org.openjfx:javafx-fxml:21")

    compileOnly("org.projectlombok:lombok:${project.property("version-lombok")}")

    testImplementation("org.junit.jupiter:junit-jupiter:${project.property("version-junit")}")
    testImplementation("org.assertj:assertj-core:${project.property("version-assertj")}")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher:${project.property("version-junit")}")

    annotationProcessor("org.projectlombok:lombok:${project.property("version-lombok")}")
}

