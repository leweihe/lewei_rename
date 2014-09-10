
set /p working_folder=

set MY_JAVA_HOME="C:\Program Files\Java\jre7"

echo working_folder is %working_folder%

call %MY_JAVA_HOME%\bin\java.exe -jar lewei-rename-0.1-SNAPSHOT.jar -w %working_folder%

echo done...
pause
