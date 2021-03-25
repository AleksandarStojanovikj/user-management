# user-management

## Overview

* Basic user management functionalities (adding, deleting, editing)
* Registration done through activation code sent via email
* Forget password functionality 
* Removal of non-verified accounts in the past 24 hours


## Prerequisites

*  JDK 8
*  Maven
*  PostgreSQL
   


## Installation

#### Clone the repository

```bash
$ git clone https://github.com/AleksandarStojanovikj/user-management.git
```



#### Install dependencies

```bash
$ cd user-management
$ mvn install
```

#### Update properties

Set the correct datasource properties in `application.properties`

```yaml
spring.datasource.url=jdbc:postgresql://localhost:5432/emt
spring.datasource.username=YOUR_DATASOURCE_USERNAME
spring.datasource.password=YOUR_DATASOURCE_PASSWORD
```

#### Start the application

Start FakeSMTP and set the listening port to `2525`.

```bash
$ java -jar fakeSMTP-2.0.jar
```

Then run the application. 

```bash
$ mvn spring-boot:run
````
