plugins {
    java
    application // permet de définir une classe principale pour lancer l'application
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    mainClass.set("fr.asenka.visu.Main")
}

