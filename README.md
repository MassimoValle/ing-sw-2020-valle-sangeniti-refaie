# Santorini GC54

![alt text](https://github.com/MassimoValle/ing-sw-2020-valle-sangeniti-refaie/blob/master/src/main/Resources/imgs/santorini-logo.png)

Santorini project of group GC54 (Massimo Valle, Simone Sangeniti, Magdy Refaie)


## Getting Started

The project's goal is to implement Santorini's game using the Model View Controller (MVC) architectural pattern and Object Oriented programming. The network is managed via a socket created between clients and server to send specific type of messages. There are two version of the client: a Command Line Interface (CLI) version showed in terminal and a Graphic User Interface (GUI) version showed in a windows, each one started by a jar.

### Documentation

The following documentation contains documents and charts used for the design of the game.

#### UML


* [Initial UML](https://github.com/MassimoValle/ing-sw-2020-valle-sangeniti-refaie/blob/master/deliveries/UML/Initial/PNG/UML_Santorini_initial.png)

* [Final Client UML](https://github.com/MassimoValle/ing-sw-2020-valle-sangeniti-refaie/blob/master/deliveries/UML/Final/Client/UML_Client_Summary.png)   [[Advanced]](https://github.com/MassimoValle/ing-sw-2020-valle-sangeniti-refaie/blob/master/deliveries/UML/Final/Client/UML_Client_Detail.png)
* [Final Server UML](https://github.com/MassimoValle/ing-sw-2020-valle-sangeniti-refaie/tree/master/deliveries/UML/Final/Server)  


#### JavaDoc
The major part of Server code is documented by JavaDoc, expecially Model and Controller packages.

* [ZIP](https://github.com/MassimoValle/ing-sw-2020-valle-sangeniti-refaie/tree/master/deliveries/final/JavaDoc/javadpc.zip)

#### Coverage report

* [ZIP](https://github.com/MassimoValle/ing-sw-2020-valle-sangeniti-refaie/tree/master/deliveries/final/report/report.zip)

#### Libraries and Plugin
Library/Plugin | Description |
--- | --- |
maven | management tool for Java-based software and build automation |
junit | Java framework for unit testing |
JavaFx | Java graphics library |


#### Jars
The following jars have been used for the delivery of the project, therefore they allow the game to be launched according to the features described in the introduction. The features created according to the project specification are listed in the next section while the details for how to launch the system will be defined in the section called Execution of the jars.

## Requirements

### Basic requirements

* Complete rules
* CLI
* GUI
* Socket

### Advanced requirements

* Multiple games
* 5 advanced gods added:
     * Chronus
     * Hera
     * Hestia
     * Triton
     * Zeus


## JAR Execution

First of all, you have to start the server using the command below and then you could start one of the client's interfaces.


* [Cli JAR](https://github.com/MassimoValle/ing-sw-2020-valle-sangeniti-refaie/blob/master/deliveries/final/CLI-jar)
* [Gui JAR](https://github.com/MassimoValle/ing-sw-2020-valle-sangeniti-refaie/blob/master/deliveries/final/GUI-jar)
* [Server JAR](https://github.com/MassimoValle/ing-sw-2020-valle-sangeniti-refaie/blob/master/deliveries/final/SERVER-jar)

### Client

The client is run by choosing the interface to play with, you can choose between CLI or GUI. The following sections describe how to run the client in one way or another.

#### CLI
For a better command line gaming experience you need to launch the client with a terminal that supports UTF-8 encoding and ANSI escapes. To launch the client in CLI mode, type the following command:

```
java -jar CLI.jar
```

Cli tested on the new Windows Terminal on Windows, the default Terminal on macOS and ZSH on linux;


#### GUI
You have to type the following command which launches the GUI:

```
java -jar GUI.jar
```

### Server

You have to type the following command which launches the server:

```
java -jar server.jar
```


## Built With

* [IntelliJ IDEA](https://www.jetbrains.com/idea/) - The IDE used
* [Maven](https://maven.apache.org/) - Dependency Management
* [JUnit](https://junit.org/junit5/) - Unit Testing Framework


## Authors

* **Massimo Valle** - *student* - [Github](https://github.com/MassimoValle)
* **Simone Sangeniti** - *student* - [Github](https://github.com/Sn4k3ss)
* **Magdy Refaie** - *student* - [Github](https://github.com/magfly)
