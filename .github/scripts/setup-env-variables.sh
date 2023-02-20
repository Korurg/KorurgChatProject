#!/bin/bash

export KORURG_CHAT_PROJECT_VERSION=$(./gradlew korurg-chat:properties | grep "version" | awk '{ gsub("version: ","",$0); print $0 }')

printenv

envsubst < "./korurg-chat/src/main/resources/application.yml" > "./korurg-chat/src/main/resources/application-temp.yml"
mv "./korurg-chat/src/main/resources/application-temp.yml" "./korurg-chat/src/main/resources/application.yml"