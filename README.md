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
 

## 3. Install 
  

### 3.1 Database Setup
 

MySQL Server’s time zone setting should be set to UTC time zone as ‘+00:00'

The database script relevant for this service can be found at /dbscripts folder .By default the db name is set as ‘config_service’ but this can be altererd . 



### 2. Mater data set up

2.2 Initially it need to set- up
- company
- context - within a company there may be several product/domains eg: logistics solution,procument solution ,travel solution
- attributes 
- enviorments - enviorment going to use eg : QA,UAT,DEV,PRODUCTION

Following curl command can be used to create above data.

```
curl -X POST   -H "Content-Type: application/x-www-form-urlencoded" -d @payload.json http://<ip>:<port>/configservice/<companty- id>/<context -id>
```
