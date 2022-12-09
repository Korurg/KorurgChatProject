#!/bin/bash

bash zip-release.sh
hub release create -a "./korurg-chat-unstable.zip"