# Process Solution Template for Camunda Platform 8 using Keycloak, Java and Spring Boot

## Directory Server
To run it locally, you'll need a Directory Server and a Camunda 8 platform. 
If you don't have a Directory Server, you can follow the documentation enclosed in LocalDirectoryServer.docx and import the sailors.LDIF file.

## Camunda 8 platform
If you don't have a Camunda 8 platform, you can use the Camunda provided docker-compose file : https://raw.githubusercontent.com/camunda/camunda-platform/d7848cc66f4dee79ee1ea73efa7eb9684c2dc748/docker-compose.yaml

## A few configurations

* Then you'll need to federate the Directory users in your Keycloak (follow the WorkshopSupport.docx documentation)

* To have some "Admin" users allowed to access **http://localhost:8080/admin.html**, you'll need to configure them manually in Keycloak. To do so, connect to Keycloak as an Admin 
localhost:18080 (admin:admin)
Navigate to Roles and create an "Admin" role.
Navigate to Users and assign the "Admin" role to someone (for example "Demo").
**Be careful, if you use demo as a normal user, you should provide him an email in Keycloak**

* If you want to send mails or store documents in drive, you'll need to add a **client_secret_google_api.json** in your resources folder. This can be done from https://console.cloud.google.com/apis/credentials

