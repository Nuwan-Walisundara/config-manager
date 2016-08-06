# Central configuration manager for Software systems

## 1. Introduction 
  
Typical software development process goes from 
- development phase, 
- QA phase , 
- User acceptance test phase, 
- production phase.
Depending on each phase the basic system metadata very . Central configuration manager is for managing all of these configurations .

## 2. System Requirements

- Java SE Development Kit 1.8 
- Apache Maven 3.0.x 
- MySQL (5.6.5+)

To build the product from the source distribution both JDK and Apache Maven are required. 

No need to install Maven if you install by downloading and extracting the binary distribution (as recommended for most users) instead of building from the source code.
 

## 3. installation
### 3.1 Build the Service

-- Clon the repository - git clone https://github.com/Nuwan-Walisundara/config-manager.git
-- run mvn clean install. This will create the fat jar config-service-1.0.0-SNAPSHOT.jar in the target directory.


### 3.1 Database Setup
 

MySQL Server’s time zone setting should be set to UTC time zone as ‘+00:00'

The database script relevant for this service can be found at /dbscripts folder .By default the db name is set as ‘config_service’ but this can be altererd . 

### 3.3 Configuration Setup

Folder path:	/deploy/config.yml
 

#### 3.3.1 Database Configuration 

user: database username
password: database password
url: url for database driver, by default jdbc:mysql://<dburl>/config_service
 

#### 3.3.2 Server Configuration


- applicationConnectors: 
A set of connectors which will handle application requests. 

- adminConnectors: 
A set of connectors which will handle admin requests. 

- port: 
TCP/IP port on which to listen for incoming connections.
Built-in default is an HTTP connector listening on port 8181, override if needed

- bindHost: 
The hostname to bind to.
 

#### 2.3.3 Log configuration  
  

- Level:

Logback logging level. ‘INFO’ Designates informational messages that highlight the progress of the application at coarse-grained level. 

- Loggers:

io.dropwizard for INFO

com.wso2telco for DEBUG

- Appenders:

Locations to where the logging messages should be displayed or written

-- type:
Console / File

-- threshold: 
The lowest level of events to print to the console/ write to the file.

-- timeZone: 
The time zone to which event timestamps will be converted.
 
-- target: 
The name of the standard stream to which events will be written.
Can be stdout or stderr. 

-- currentLogFilename: 
The filename where current events are logged.

-- archive: 
Whether or not to archive old events in separate files.
  
-- archivedLogFilenamePattern: 
Required if archive is true.The filename pattern for archived files.%d is replaced with the date in yyyy-MM-dd format, and the fact that it ends with.gz indicates the file will be gzipped as it’s archived.Likewise, filename patterns which end in .zip will be filled as they are archived.

-- archivedFileCount: 
The number of archived files to keep.
Must be between 1 and 50. 

-- logFormat: 
Logback pattern with which events will be formatted.  


## 4. Run the Service

In order to get the service up and running, execute the following command.

```
java -jar target/config-service-1.0.0-SNAPSHOT.jar server deploy/config.yml
```




### 5. Mater data set up

   Initially it need to set- up
	-- company
	-- context - within a company there may be several product/domains eg: logistics solution,procument solution ,travel solution
	-- attributes 
	-- enviorments - enviorment going to use eg : QA,UAT,DEV,PRODUCTION
following curl command can be used to create above data.

```
curl -X POST   -H "Content-Type: application/x-www-form-urlencoded" -d @payload.json http://<ip>:<port>/configservice/<companty- id>/<context -id>
```


 


## 6. Features 

### 6.1 APIs with cURL Testing: 


- GET:
 
http://&lt;host&gt;:&lt;port&gt;/configservice/{ownerID}/{contextID}/{envID}

```
curl -i -H "Accept: application/json" "http://<host>:<port>/configservice/{ownerID}/{contextID}/{envID}"
```

API to retrieve a valid configurations for given owner,context,enviorment
  
 
  

### 6.2 Swagger Annotations:  

[Swagger](http://swagger.io/getting-started/) is a standard, language-agnostic interface to REST APIs which allows both humans and computers to discover and understand the capabilities of the service without access to source code, documentation, or through network traffic inspection.

  
In order to retrieve Swagger definitions of this microservice, go to http://&lt;host&gt;:&lt;port&gt;/swagger .
 
