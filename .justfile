
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

bundle-sources output-file="bundle-sources.txt":
    find . -type f \( -name "*.java" -o -name "*.kts" -o -name "*.properties" -o -name "*.css" \) \
        ! -path "*/target/*" \
        ! -path "*/build/*" \
        ! -path "*/.git/*" \
        ! -path "*/.idea/*" \
        ! -path "*/gradle/*" \
        ! -path "*/.gradle/*" \
        ! -path "*/.run/*" \
        | xargs -I {} sh -c 'echo "--- FILE: {} ---"; cat {}; echo ""' > {{ output-file }}

summarize-ia input-file="project-visu-sources.txt" output-file="summary-ia.txt":
    jq -n --arg prompt "Fais un résumé détaillé de ce fichier sources. Explique ce qui est déjà fait mais ne parle pas de ce qui reste à faire" --arg content "$(cat {{ input-file }})" \
    '{ \
      model: "default", \
      messages: [{role: "user", content: ($prompt + "\n\n" + $content)}] \
    }' | curl http://localhost:1234/v1/chat/completions \
      -H "Content-Type: application/json" \
      -d @- > {{ output-file }}

