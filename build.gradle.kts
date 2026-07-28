plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("org.springframework.boot") version "4.1.0"
    id("io.spring.dependency-management") version "1.1.7"
}

application {
    mainClass.set("fr.asenka.visu.Main")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

javafx {
    version = "21.0.2"
    modules("javafx.controls", "javafx.graphics", "javafx.fxml")
}

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}

dependencies {

    implementation("org.springframework.boot:spring-boot-starter")

    implementation("org.openjfx:javafx-controls")
    implementation("org.openjfx:javafx-graphics")
    implementation("org.openjfx:javafx-fxml")

    testImplementation("org.assertj:assertj-core")

    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}

