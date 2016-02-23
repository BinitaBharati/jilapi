# Jilapi
Jilapi is a Java-based native OS command output parser.

##Features
* Line-wise parser.By default, Jilapi assumes that every output line is a meaningful entity by itself.
* Every line is further broken down into individual fields.
* Takes the following types of inputs:
     * InputStream
     * A single String with a new line delimiter to mark individual lines.
* The result of the command execution is given out in JSON format.
* It is a standalone jar
* User of the library only needs to feed the command related data into the jilapi.properties file
* Please check the sample jilapi.properties file for reference.

## Build
* `git clone git@github.com:BinitaBharati/jilapi.git`
* `cd jilapi`
* `mvn clean;mvn package` - generates the jilapi uber jar
* The generated jilapi jar can be included in other projects.

##Test
* `mvn test`
