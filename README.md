# Java EE 7 WebSockets Chat Tutorial

## Rational

An example setting up a websocket-enabled chat system using Java EE 7, custom encoders/decoders, the new Java API for JSON Processing and [bootstrapping](https://en.wikipedia.org/wiki/Bootstrapping).

Two application servers are tested:
* [Wildfly](http://wildfly.org/),
* [Glassfish](https://glassfish.java.net/).

With each server, two modes are available:
* *embedded*: this mode allows testing the application without requiring any standalone server already installed and running.
* *standalone*: this mode creates a fatjar which is able to launch a standalone server with the application running.

The post [Embarquement immédiat et sans souci pour JavaEE 7](http://atao60.github.io/pop-tech/2015/10/javaee7-runnable-fatjar.html#pour-aller-plus-loin) (in french) gives some more details.

## Tools

With [Wildfly Swarm](http://wildfly.org/swarm/), it's straightforward to build either of the two modes for *Wildfly*. 

But *Glassfish* doesn't provide any off-the-shelf means to create a runnable fatjar. *maven-embedded-glassfish-plugin* is designed just to launch the server with an existing war file, nothing else. 

[Payara Micro](http://www.payara.co.uk/introducing_payara_micro) can launch a standalone Glassfish server, deploying a specified war file. But again, it doesn't provide any off-the-shelf means to create a runnable fatjar.

Fortunately, with the help of [Capsule](http://www.capsule.io/), it's quite easy to wrap *Payara Micro* inside a fatjar.

For the time being *maven-assembly-plugin* is used to do the job. But starting with its version 2.6, this plugin has eratic behavior: the generated fatjar is not always runnable. See [capsule and maven-assembly-plugin 4.5+ #93](https://github.com/puniverse/capsule/issues/93). It's why the version 2.5.5 is used here.

About *maven-embedded-glassfish-plugin*: when used on the command line, it can't be launch from the parent project but only from the module with source code, see below.

About *wildfly-swarm-plugin*: under *M2Eclipse*, don't use the profile `wildfly-embedded` as, with wildfly-swarm:run, the server keeps running after its shutting down has been asked. Then you would have to stop it from a console command line.

This project requires Maven 3.3.1+. When used with *Maven 3.3* or below, on the command line:  
* with either of the profiles `glassfish-embedded` or `glassfish-standalone`, specify also the profile `glassfish`,    
* with either of the profiles `wildfly-embedded` or `wildfly-standalone`, specify also  the profile `wildfly`.  

## Build and running

A profile is provided for each scenario:
``` bash
mvn clean package antrun:run -Pwildfly-standalone
```

``` bash
mvn clean wildfly-swarm:run -Pwildfly-embedded
```

``` bash
mvn clean install antrun:run -Pglassfish-standalone
```

``` bash
cd app
mvn clean package embedded-glassfish:run -Pglassfish-embedded
```
   
Whatever the scenario, when the server is ready, go to:

       http://localhost:8084/hascode   
    
Note:
* The port can be changed from the parent pom file.

## Todo

Use of *capsule-maven-plugin* with the profile `glassfish-standalone`.

With this simple websocket application, *Wildfly-Swarm* can run it with a *Undertow* server, i.e. without the *Wildfly* server itself: the fatjar is half the size of the *Glassfish* fatjar. So:
* create an application which requires the *Wildfly* server itself, and check then the fatjar size;
* find out if there is a means to slim down the *Payara* fatjar.
    
## Credits

The sample is based on:
>      Creating a Chat Application using Java EE 7, Websockets and GlassFish 4
>      http://www.hascode.com/2013/08/creating-a-chat-application-using-java-ee-7-websockets-and-glassfish-4/

with the code source:
>      Java EE 7 - GlassFish WebSockets Chat Tutorial  
>      https://bitbucket.org/hascode/javaee7-websocket-chat/

    