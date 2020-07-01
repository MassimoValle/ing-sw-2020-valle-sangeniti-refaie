# Santorini GC54

![alt text](https://github.com/MassimoValle/ing-sw-2020-valle-sangeniti-refaie/blob/MVC_client/src/main/Resources/imgs/santorini-logo.png)

Santorini project of group GC54 (Massimo Valle, Simone Sangeniti, Magdy Refaie)


## Getting Started

The goal of the project is to implement Statorini's game using the Model View Controller (MVC) architectural pattern and Object Orientied programming. The network is managed via a socket created between clients and server to send specific type of messages. There are two version of client: a Command Line Interface (CLI) version showed in terminal and a Graphic User Interface (GUI) version showed in a windows, each one started by a jar.

### Documentation

The follow documentation contains documents and charts used for the design of the game.

#### UML


* [Initial UML](https://www.jetbrains.com/idea/)

* [Final UML](https://www.jetbrains.com/idea/)

#### JavaDoc
The major part of Server code is documented by JavaDoc, expecially Model and Controller packages.

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

First of all, you have to start the server using the command below and then you colud start one of the client's interfaces.

### Client

The client is run by choosing the interface to play with, the possible choices are from the CLI or GUI. The following sections describe how to run the client in one way or another.

#### CLI
For a better command line gaming experience you need to launch the client with a terminal that supports UTF-8 encoding and ANSI escapes. To launch the client in CLI mode, type the following command:

```
java -jar CLI.jar
```

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

* **Massimo Valle** - *Developer* - [PurpleBooth](https://github.com/MassimoValle)
* **Simone Sangeniti** - *Developer* - [PurpleBooth](https://github.com/Sn4k3ss)
* **Magdy Refaie** - *Developer* - [PurpleBooth](https://github.com/magfly)
