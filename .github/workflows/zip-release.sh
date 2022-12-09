#!/bin/bash

mkdir ./korurg-chat-temp
cp ./templates ./korurg-chat-temp
cp ./korurg-chat/build/libs/korurg-chat.jar ./korurg-chat-temp
zip ./korurg-chat-unstable.zip ./korurg-chat-temp