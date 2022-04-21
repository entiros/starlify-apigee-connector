# Starlify connector for Apigee Gateway
Exports the API details to Starlify as systems and services.

## Dependencies
   1. Java-8 +
   2. Maven
   
### spring-boot-starter-web
For exposure of connector etc. on http.

## Configuration
Put the text below in your property file to configure your URL for Apigee API Gateway and Starlify:

```
apigee:
	server:
		url: http://localhost:8001
starlify:
		url: https://api.starlify.com
```
 
## Start
Start by cloning the project by using the link below:  
https://github.com/entiros/starlify-apigee-connector.git

Go to cloned location and run the command below to start the process:
```
mvn clean spring-boot:run
```

## Import Apigee API Gateway details to Starlify
Use the endpoint below to start importing API details to Starlify as systems and services:

```
Method : POST
URL : http://localhost:8080/submitRequest
Body : 
	{
		"starlifyKey":"starlify-api-key",
		"apiKey":"apigee-api-key",
		"networkId":"starlify-network-id-to-create-services-systems-and-flows"
	}
```
