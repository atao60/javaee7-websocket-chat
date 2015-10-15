# Java EE 7 WebSockets Chat Tutorial

## Rational

An example setting up a websocket-enabled chat system using Java EE 7, custom encoders/decoders, the new Java API for JSON Processing and [bootstrapping](https://en.wikipedia.org/wiki/Bootstrapping).

Two application servers are tested:
* [Wildfly](http://wildfly.org/),
* [Glassfish](https://glassfish.java.net/).

For each one, two modes are available:
* *embedded*: this mode allows testing the application without requiring any running standalone server.
* *standalone*: this mode creates a fatjar which is able to launch a standalone server running the application.

## Tools

With [Wildfly Swarm](http://wildfly.org/swarm/), it's straightforward to build either of the two modes for *Wildfly*. 

*Glassfish* doesn't provide any off-the-shelf means to create a runnable fatjar. *maven-embedded-glassfish-plugin* is designed just to launch the server with an existing war file, nothing else. 

[Payara Micro](http://www.payara.co.uk/introducing_payara_micro) can launch a standalone Glassfish server, deploying a specified war file. But again, it doesn't provide any off-the-shelf means to create a runnable fatjar.

Fortunately, with the help of [Capsule](http://www.capsule.io/), it's quite easy to wrap *Payara Micro* inside a fatjar.

## Build and running

A profile is provided for each scenario:
* `mvn clean wildfly-swarm:run -Pwildfly-embedded`
* `mvn package antrun:run -Pwildfly-standalone`
* `mvn clean package embedded-glassfish:run -Pglassfish-embedded`
* `mvn clean package antrun:run -Pglassfish-standalone`
   
Whatever the scenario, when the server is ready, go to:

       http://localhost:8084/hascode   
    
Notes:
* The port can be changed from the pom file.
* With *Maven 3.3* or below, for the profiles using *Wildfly* server, the profile `wildfly` must be added to the build command.  

## Todo

With the simple websocket application, *Wildfly-Swarm* can run it with a *Undertow* server, i.e. without the *Wildfly* server itself: the fatjar is half the size of the *Glassfish* fatjar. So:
* create an application which requires the *Wildfly* server itself, and check then the fatjar size;
* find out if there is a means to slim down the *Glassfish* fatjar.
    
## Credits

The sample is based on:
>      Creating a Chat Application using Java EE 7, Websockets and GlassFish 4
>      http://www.hascode.com/2013/08/creating-a-chat-application-using-java-ee-7-websockets-and-glassfish-4/

with the code source:
>      Java EE 7 - GlassFish WebSockets Chat Tutorial  
>      https://bitbucket.org/hascode/javaee7-websocket-chat/

    