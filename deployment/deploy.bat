@REM Server info
set project-name="guild-master"
set target-dir="C:\Program Files\Apache Software Foundation\Tomcat 10.1\webapps"

@REM Working directory info
set src-dir="../bin"
set lib-dir="../lib"
set web-dir="../web/jsp"
set assets="../web/assets"
set config="../config"

@REM Create the server directory structure
rmdir /q/s "temp"
mkdir "temp"
mkdir "temp/WEB-INF/classes"
mkdir "temp/WEB-INF/lib"
mkdir "temp/WEB-INF/jsp"
mkdir "temp/assets"
mkdir "temp/uploads"

@REM Copy the contents to each server directory
echo D | xcopy /q/s/y %src-dir% "temp/WEB-INF/classes/"
echo D | xcopy /q/s/y %lib-dir% "temp/WEB-INF/lib/"
echo D | xcopy /q/s/y %web-dir% "temp/WEB-INF/jsp/"
echo D | xcopy /q/s/y %assets% "temp/assets/"
echo D | xcopy /q/s/y %config% "temp/WEB-INF/"

@REM Create WAR file and copy it to the server directory
jar -cvf %project-name%.war -C temp/ .

@REM Remove existing WAR in server
del %target-dir%\%project-name%.war
echo D | xcopy /q/y %project-name%.war %target-dir%

@REM Delete all temporary files
del %project-name%.war
rmdir /q/s "temp"

pause
