# The Oregon Trail
This is a relative remake of the 1990s version of The Oregon Trail by MECC
rewritten in Java with a terminal based UI. This was made for a Computer 
Science project at Bridgewater State University. I got bored and recreated
an actual game instead of using 1000 if statements.

### Dependencies
* Google's ***lanterna*** library - used for the terminal based UI
* Google's ***GSON*** library - used for save serialization / deserialization

### Platform Support
* Any platform that supports JDK 23 and Windowing

### Prerequisites
* OpenJDK 23
* * [Download OpenJDK](https://jdk.java.net/)
* * Verify installation: 
    ```shell
    java -version
    ```
* Apache Maven
* * [Download Maven](https://maven.apache.org/download.cgi)
* * Verify installation:
    ```shell
    mvn -version
    ```
    
### Building the project
Clone the repository:
```shell
git clone https://github.com/hlpdev/OregonTrail.git
cd OregonTrail
```

Compile and package:
```shell
mvn clean package
```

The output JAR will be in ``target/``
```shell
ls target/
```

### Running the application
```shell
java -jar TheOregonTrail.jar
```

### Dependency Management
Maven will automatically resolve dependencies, but if needed:
```shell
mvn dependency:resolve
```