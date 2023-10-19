# vdp-client-app
This repository hosts the Spring boot micro service example using two-way-ssl to connect for the [Visa Developer Platform](https://developer.visa.com/) (Sandbox Environment).

## Getting Started

The instructions will guide you make the first call to VDP from your local machine for development and testing purposes.

### Prerequisites

* Java JDK: ([Version 1.8.0 or 11.0.0](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html))
* Build Tools: Maven ([Version 3.0.5 or above](https://maven.apache.org/download.cgi))

### Setup & Installation
* Follow the instruction of [Two-Way SSL (Mutual Authentication)](https://developer.visa.com/pages/working-with-visa-apis/two-way-ssl) to generate client side certificated key/files.
* This example specifically follows 'Configuring a Two-Way SSL Keystore Using an Auto-generated CSR' to generate jks file.
* After generate your local jks file, you can replace the 'dummy_key_file.jks' under resources folder of this project. 
* Edit the file name 'classpath:dummy_key_file.jks' on VdpClientAuthConfig.java
* Edit /resources/application.properties to set the fields shown below. Refer the [Getting Started Guide](https://developer.visa.com/vdpguide#get-started-overview) to get the credentials.
```
auth.userid=<YOUR_VDP_PROJECT_USER_ID>
auth.password=<YOUR_VDP_PROJECT_PASSWORD>
auth.keystore.password=<YOUR_KEYSTORE_PASSWORD>
auth.privatekey.password=<YOUR_PRIVATE_KEY_PASSWORD>
```

* Run the below command to install and resolve all dependencies
```
mvn clean install
```
* Run the application
```
java -jar vdp-client-app-0.0.1-SNAPSHOT.jar 
```
* Test the micro service application from local: http://localhost:8080/vdp-connection/helloworld

