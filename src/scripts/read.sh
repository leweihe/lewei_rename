echo $JAVA_HOME

if [ $# -lt 1 ]; then
	echo "entered too much param"
fi

if [ $@ != "" ]; then
	app_working_folder=$@
fi

echo "working folder is "$app_working_folder

java -jar lewei-rename-0.1-SNAPSHOT.jar -r $app_working_folder


