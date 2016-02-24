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

## Test
* `mvn test`

## Jilapi property file

### File description
* **`<CMND_KEY>.result.sections`**  : Useful when the command output has multiple sections.May not be applicable for all commands.
    Please check the 'cmnd3' properties for a demo of the <CMND_KEY>.result.sections property.
* **`<CMND_KEY>.result.sections.header`** :  The output line preceding the start of the actual data. May not be applicable for all commands.
* **`<CMND_KEY>.result.sections.footer`** : The output line following the end of the actual data.May not be applicable for all commands.
* **`<CMND_KEY>.result.sections.ignore`** : The output line that needs to be ignored.May not be applicable for all commands.
* **`<CMND_KEY>.result.sections.line.field.map`** : A map representing the position of the fields (per output line) to the field.
    The map should contain the field positions in ascending order. Eg : 1:fieldA,4:fieldB,10:fieldC is valid. But, 1:fieldA,10:fieldC,4:fieldB is invalid. A single field can spawn across multiple positions (columns) in the output line.See cmnd2's buildTime for a sample of the same.
* **`<CMND_KEY>.result.line.field.delimiter`** : The delimiter used to delimit across individual fields.This should be a unique character, and should no be already present as part of the original output.The field delimiter when not specified is SPACE.

### Sample file

```result.line.field.delimiter=default``` <br />

\#Kernel IP routing table <br />
\#Destination     Gateway         Genmask         Flags Metric Ref    Use Iface <br/>
\#0.0.0.0         10.0.2.2        0.0.0.0         UG    0      0        0 eth0 <br/>
\#10.0.2.0        0.0.0.0         255.255.255.0   U     0      0        0 eth0 <br/>
\#192.168.10.0    0.0.0.0         255.255.255.0   U     0      0        0 eth1 <br/>
```cmnd1.result.sections.header=Destination,Gateway,Genmask,Flags,Metric,Ref,Use,Iface
cmnd1.result.sections.line.field.map=1:destinationNw,2:gateway,3:netMask,5:metric,8:port
```


## License

Copyright © 2016 Binita Bharati <br />
Distributed under the Eclipse Public license. 
