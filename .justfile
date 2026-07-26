
build *args:
    ./gradlew build {{ args }}

build-no-cache *args:
    ./gradlew --no-configuration-cache build {{ args }}

run *args:
    ./gradlew run {{ args }}

clean:
    ./gradlew clean

test:
    ./gradlew test

setup:
    gradle wrapper

