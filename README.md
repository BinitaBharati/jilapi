# Jilapi
Jilapi is a Java-based native OS command output parser.

##Features
* Line-wise parser.By default, Jilapi assumes that every output line is a meaningful entity by itself.
* Every line is further broken down into individual fields.
* The result of the command execution is given out in JSON format.
* Standalone jar
* User of the library only needs to feed the command related data into the jilapi.properties file
* PLease check the sample jilapi.properties file for reference.

## Build
* `git clone git@github.com:BinitaBharati/jilapi.git`
* `cd jilapi`
* `mvn clean;mvn package` - generates the jilapi uber jar
* The generated jilapi jar can be included in other projects.

##Test
* `java -cp <jilapi uber jar> com.github.binitabharati.jilapi.Jilapi cmnd1`
* The result json will be printed to the log file.

