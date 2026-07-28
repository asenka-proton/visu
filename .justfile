
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

bundle-sources output-file="project-visu-sources.txt":
    find . -type f \( -name "*.java" -o -name "*.kts" -o -name "*.properties" \) \
        ! -path "*/target/*" \
        ! -path "*/build/*" \
        ! -path "*/.git/*" \
        ! -path "*/.idea/*" \
        ! -path "*/gradle/*" \
        ! -path "*/.gradle/*" \
        ! -path "*/.run/*" \
        | xargs -I {} sh -c 'echo "--- FILE: {} ---"; cat {}; echo ""' > {{ output-file }}

