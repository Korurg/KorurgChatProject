#!/bin/bash

archive_name="archive"

if [ -n "$1" ]
then
  archive_name="$1.zip"
fi

echo "#!/bin/bash" > run-linux.sh
echo "java -jar korurg-chat.jar -Xss1m -Xmx128m" >> run-linux.sh

echo "start java -jar korurg-chat.jar -Xss1m -Xmx128m" > run-windows.bat

zip -7 "./${archive_name}" ./run-windows.bat
zip -7 "./${archive_name}" ./run-linux.sh
zip -7 -r "./${archive_name}" ./templates
zip -7 -r "./${archive_name}" ./localizations
zip -j -7 "./${archive_name}" ./korurg-chat/build/libs/korurg-chat.jar