# Jilapi
Jilapi is a Java-based native OS command line output parser.

##Features
* Line-wise parser.By default, Jilapi assumes that every output line is a meaningful entity by itself.
  (Jilapi can also be made to work under situation when a single entity spans across multiple lines)
* Every line is further broken down into individual fields.
* Takes the following types of inputs:
     * InputStream
     * A single String with a new line delimiter to mark individual lines.
* The result of the command output parsing is given out in JSON format.
* It is a standalone jar
* User of the library only needs to feed the command related data into the jilapi.properties file
* Please check the sample jilapi.properties file for reference.

## Build
* `git clone git@github.com:BinitaBharati/jilapi.git`
* `cd jilapi`
* `mvn clean;mvn package` - generates the jilapi uber jar.
* The generated jilapi jar can be included in other projects.

## Test
* `mvn test`

## Quick Start
Lets see few sample commands. <br />
1. Executing `uname -a` on a Linux system generates the following output: 
![Alt text](docs/cmnd1.png "uname -a")

Now, lets understand what attributes of jilapi property file matters in this case.
* **`<CMND_KEY>.entity.delimiter`**: Here a single line contains a complete meanigful entity.Hence, entity delimiter is a new line, which is also the default entity delimiter.Hence, this attribute doesnt apply.
* * **`<CMND_KEY>.result.entity.field.delimiter`**:The entity field delimiter is SPACE here, which is also the default.So, this attribute doesnt apply.
* **`<CMND_KEY>.result.sections`**: The output is just a single line.Hence, multiple sections doesnt apply.
* **`<CMND_KEY>.result.header`**: The output is just a single line.Hence, headers doesnt apply.
* **`<CMND_KEY>.result.footer`**: The output is just a single line.Hence, footers doesnt apply.
* **`<CMND_KEY>.result.ignore`**: The output is just a single line.Hence, ignore doesnt apply.
* **`<CMND_KEY>.result.entity.field.positional.map`**: Here each field of the entity is positional.Eg at 1st position we find Kernel version, 2nd position we find Node name etc.The field Build time spans multiple columns, viz column 4 to 11.
* **`<CMND_KEY>.result.entity.field.prefix.map`**: Doesnt apply as `<CMND_KEY>.result.entity.field.positional.map` is already applied.
Corresponding property file entry is given below:
```
cmnd1.result.entity.field.positional.map=1:kernelName,2:nodeName,3:kernelVersion,4-11:buildTime,12:processorType,13:hwPlatform,14:processorArch,15:osName
```

2. Executing `route -n` on a Linux system generates the following output: <br />
![Alt text](docs/cmnd2.png "route -n")

Now, lets understand what attributes of jilapi property file matters in this case.
* **`<CMND_KEY>.entity.delimiter`**: Here each single line contains a complete meanigful entity, which is a route entry.Hence, entity delimiter is a new line, which is also the default entity delimiter.Hence, this attribute doesn't apply.
* * **`<CMND_KEY>.result.entity.field.delimiter`**:The entity field delimiter is SPACE here, which is also the default.So, this attribute doesnt apply.
* **`<CMND_KEY>.result.sections`**: There are no sections, as in there is only a single large section.Multiple sections dont apply.
* **`<CMND_KEY>.result.header`**: The lines containing the fields `Destination`,`Gateway`,`Genmask` etc precede the actual route entries.So, the columns `Destination`,`Gateway`,`Genmask` etc is the header.
* **`<CMND_KEY>.result.footer`**: Footers doesnt apply.
* **`<CMND_KEY>.result.ignore`**: Ignore doesnt apply.
* **`<CMND_KEY>.result.entity.field.positional.map`**: Here each field of the entity is positional.Eg at 1st position we find `Destination Network`, 2nd position we find `Gateway` etc.
* **`<CMND_KEY>.result.entity.field.prefix.map`**: Doesnt apply as `<CMND_KEY>.result.entity.field.positional.map` is already applied.

          
## Jilapi property file

### File description
* **`<CMND_KEY>.entity.delimiter`**: The delimiter marking a complete entity can be demarked.Entity is a single unit of useful data.
     A command output can have multiple such entities.The default entity delimiter is new line, but it may not be true in all cases.
* **`<CMND_KEY>.result.entity.field.delimiter`**: The delimiter used to delimit across individual fields of a entity.This should be a      unique character, and should not be already present as part of the original output.The default field delimiter when not specified      is SPACE.
     A command output can have multiple such entities.The default entity delimiter is new line, but it may not be true in all cases.
* **`<CMND_KEY>.result.sections`**: Useful when the command output has multiple sections.May not be applicable for all commands.
    Please check the 'cmnd3' properties for a demo of the <CMND_KEY>.result.sections property.
* **`<CMND_KEY>.result.header`**:  The output line preceding the start of the actual data.May not be applicable for all commands.
* **`<CMND_KEY>.result.footer`**: The output line following the end of the actual data.May not be applicable for all commands.
* **`<CMND_KEY>.result.ignore`**: The output line that needs to be ignored.May not be applicable for all commands.
* **`<CMND_KEY>.result.entity.field.positional.map`**: A map representing the position of the fields of an entity in the output.This 
      is mutually exclusive with result.entity.field.prefix.map.s. The map should contain the field positions in ascending order. 
      Eg -> 1:fieldA,4:fieldB,10:fieldC is valid. But, 1:fieldA,10:fieldC,4:fieldB is invalid.
      A single field can spawn across multiple positions (columns) in the output line.See cmnd1's buildTime for a sample of the same.
      If exact field positioning not available, but instead search texts per field available in the output, then please use 
      result.entity.prefix.map.
* **`<CMND_KEY>.result.entity.field.prefix.map`**: A list enlisting the text content signifying the start of each field of an entity
     in the output.This is mutually exclusive with result.entity.field.positional.map.Please refer to cmnd5 for a sample.

### Sample file

```result.line.field.delimiter=default``` <br />

\#Kernel IP routing table <br />
\#Destination     Gateway         Genmask         Flags Metric Ref    Use Iface <br/>
\#0.0.0.0         10.0.2.2        0.0.0.0         UG    0      0        0 eth0 <br/>
\#10.0.2.0        0.0.0.0         255.255.255.0   U     0      0        0 eth0 <br/>
\#192.168.10.0    0.0.0.0         255.255.255.0   U     0      0        0 eth1 <br/>
```
cmnd1.result.sections.header=Destination,Gateway,Genmask,Flags,Metric,Ref,Use,Iface
cmnd1.result.sections.line.field.map=1:destinationNw,2:gateway,3:netMask,5:metric,8:port
```

\#Linux vmwutpapp01-dev 2.6.18-238.5.1.el5 #1 SMP Mon Feb 21 05:52:39 EST 2011 x86_64 x86_64 x86_64 GNU/Linux <br/>

```
cmnd2.result.sections.line.field.map=1:kernelName,2:nodeName,3:kernelVersion,4-11:buildTime,12:processorType,13:hwPlatform,14:processorArch,15:osName
``` 

\#===========================================================================<br/>
\#Interface List<br/>
\# 15...e8 b1 fc 66 45 3a ......Bluetooth Device (Personal Area Network)<br/>
\# 13...e8 b1 fc 66 45 36 ......Intel(R) Dual Band Wireless-AC 7260<br/>
\# 11...28 d2 44 cc 73 0c ......Intel(R) Ethernet Connection I218-LM<br/>
\# 30...0a 00 27 00 00 00 ......VirtualBox Host-Only Ethernet Adapter 6<br/>
\# 32...0a 00 27 00 00 00 ......VirtualBox Host-Only Ethernet Adapter 7<br/>
\# 35...0a 00 27 00 00 00 ......VirtualBox Host-Only Ethernet Adapter 8<br/>
\#  1...........................Software Loopback Interface 1<br/>
\# 12...00 00 00 00 00 00 00 e0 Microsoft ISATAP Adapter<br/>
\# 16...00 00 00 00 00 00 00 e0 Microsoft ISATAP Adapter 2<br/>
\# 17...00 00 00 00 00 00 00 e0 Microsoft ISATAP Adapter 3<br/>
\# 33...00 00 00 00 00 00 00 e0 Microsoft ISATAP Adapter 10<br/>
\# 34...00 00 00 00 00 00 00 e0 Microsoft ISATAP Adapter 11<br/>
\#===========================================================================<br />
<br />
\#IPv4 Route Table<br />
\#===========================================================================<br />
\#Active Routes:<br/>
\#Network Destination        Netmask          Gateway       Interface  Metric<br/>
\#          0.0.0.0          0.0.0.0      10.74.128.1    10.74.130.122     20<br/>
\#      10.74.128.0    255.255.252.0         On-link     10.74.130.122    276<br/>
\#    10.74.130.122  255.255.255.255         On-link     10.74.130.122    276<br/>
\#    10.74.131.255  255.255.255.255         On-link     10.74.130.122    276<br/>
\#        127.0.0.0        255.0.0.0         On-link         127.0.0.1    306<br/>
\#        127.0.0.1  255.255.255.255         On-link         127.0.0.1    306<br/>
\#  127.255.255.255  255.255.255.255         On-link         127.0.0.1    306<br/>
\#     132.168.56.0    255.255.255.0         On-link      132.168.56.1    266<br/>
\#    192.168.119.0    255.255.255.0         On-link     192.168.119.1    266<br/>
\#    192.168.119.1  255.255.255.255         On-link     192.168.119.1    266<br/> \#===========================================================================<br />
\#Persistent Routes:<br />
\#  None<br />
 <br />
\#IPv6 Route Table<br />
\#===========================================================================<br />
\#Active Routes:<br />
\# If Metric NetworkDestination      Gateway<br/>
\#  1    306 ::1/128                  On-link<br/>
\# 23   1010 2002::/16                On-link<br/>
\# 11    276 fe80::/64                On-link<br/>
\# 21    266 fe80::/64                On-link<br/>
\# 22    266 fe80::/64                On-link<br/>
\# 21    266 fe80::2d21:f7b:b2f6:374d/128 <br/>
                                      On-link<br/>
\# 24    266 fe80::4ca6:6ff5:97f3:e609/128<br/>
                                      On-link<br/>
\# 11    276 fe80::58b5:85f7:a3a3:c493/128<br/>
                                      On-link<br/>
\#  1    306 ff00::/8                 On-link<br/>
\# 11    276 ff00::/8                 On-link<br/>
\# 21    266 ff00::/8                 On-link<br/>
\# 22    266 ff00::/8                 On-link<br/>
\# 24    266 ff00::/8                 On-link<br/>
\#===========================================================================<br />
\#Persistent Routes:<br />
\#  None<br />

```
cmnd3.result.sections=ipv4Route;ipv6Route 
cmnd3.result.sections.header=NetworkDestination,Netmask,Gateway,Interface,Metric;If,Metric,Network Destination,Gateway
cmnd3.result.sections.footer=\=+;\=+
cmnd3.result.sections.line.field.map=1:destinationNw,2:netMask,3:gateway,4:port,5:metric;1:field1,2:metric,3:destination,4:gateway
```

\#root:X:0:0:root:/root:/bin/bash<br/>
\#bin:X:1:1:bin:/bin:/sbin/nologin<br/>
\#daemon:X:2:2:daemon:/sbin:/sbin/nologin<br/>
\#adm:X:3:4:adm:/var/adm:/sbin/nologin<br/>
\#lp:X:4:7:lp:/var/spool/lpd:/sbin/nologin<br/>
\#sync:X:5:0:sync:/sbin:/bin/sync<br/>
\#shutdown:X:6:0:shutdown:/sbin:/sbin/shutdown<br/>
\#dovenull:X:496:492:Dovecot's unauthorized user:/usr/libexec/dovecot:/sbin/nologin<br/>
\#tcpdump:X:72:72::/:/sbin/nologin<br/>
\#nscd:X:28:28:NSCD Daemon:/:/sbin/nologin<br/>
``` 
cmnd4.result.sections.line.field.map=1:userName,2:passwd,3:userId,4:grpId,5:userFullName,6:homeDirectory,7:shellAccount
cmnd4.result.line.field.delimiter=:
```


## License

Copyright © 2016 Binita Bharati <br />
Distributed under the Eclipse Public license. 
