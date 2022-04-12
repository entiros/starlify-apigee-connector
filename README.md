# Starlify connector for Apigee gateway
Exports the api details to starlify as Service, Sysytem and Flow.

## Dependencies
   1. Java-8 +
   
### spring-boot-starter-web
For exposure of connector etc. on http.

## Configuration
Make sure proper Apigee api gateway and starlify url's configured properly in properties file like this

```
		apigee:
		  server:
			url: http://localhost:8001
		starlify:
		  url: https://api.starlify.com
```
 
## Start
First clone the project using below link
     https://github.com/entiros/starlify-apigee-connector.git

Go to cleaned location and run below command to start the process
	mvn clean spring-boot:run

## import Apigee api details to Starlify
Use below endpoint to start importing api details to starlify as services, systems and flows 

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