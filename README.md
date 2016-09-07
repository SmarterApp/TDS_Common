#Common
This project contains some of the commonly used classes throughout the system.

##Build Requirements
* Java 8
* Maven

## How to Build
Simply run `mvn clean install` at the base directory of the project.


##Features
### Common Exceptions
These are common Exceptions used throughout the microservices.  Specifically the Exceptions leveraged by Spring MVC ExceptionAcvice to return proper HTTP status code and messages.

### MySQL UUID VarBinary handler
VarBinary UUID mapping isn't handled out of the box by Spring.  There are some specific mapping classes that will help handling this type and UUID -> & VarBinary -> UUID convesrsion.