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

dependencies {

}

