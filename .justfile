# Commandes de base pour le projet VISU

# Compiler le projet
build:
    ./gradlew build

# Lancer l'application
run:
    ./gradlew run

run-st:
    ./gradlew run --stacktrace

# Nettoyer les fichiers de compilation
clean:
    ./gradlew clean

# Lancer les tests (si tu en as)
test:
    ./gradlew test

# Générer le Gradle Wrapper (si absent)
setup:
    gradle wrapper

