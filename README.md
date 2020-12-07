### GROUP E

* * * 
**Velkommen til Group E's LSD projekt.**

Vi har lavet **Moon lodge projektet**, som er et booking system af hotelværelser.  
Projektet er lavet med **Java i frontend og backend**, som snakker sammen ved hjælp af **RMI**.  

### Projektet  
Hele projektet er deployet på en droplet og kan tilgås ved hjælp af en [**React frontend**](http://206.81.29.87/) eller et [**Postman projekt**](https://www.getpostman.com/collections/bb1d633ed47153050bab)   

Du kan lave en booking med en eller flere hotelværelser. Du kan finde dine bookings via dit passport number og du kan slette dine bookings.  

### Kontrakten
[**Frontend**](https://github.com/LargeSystemsDevelopment2020/MoonLodge-FrontEnd) og [**backend**](https://github.com/LargeSystemsDevelopment2020/Moonlodge-Backend) kan sende java klasser over RMI ved at bruge vores [**Kontrakt**](https://github.com/LargeSystemsDevelopment2020/MoonLodge_Contract).  
Kontrakten er blevet bygget til en .jar fil og lagt på [**Archiva**](http://rasmuslynge.com:8081/#artifact/dk.cphbusiness.lsd.groupe.moonloodge/MoonLodgeContract) på en droplet. (kan muligvis ikke åbnes i chrome grundet SSL_PROTOCOL, men virker bla. i friefox).  
kontaktens .jar fil bliver hentet ned i front- og backend ved hjælp af pom filen (se i kontraktens repo for et eksempel)


### Pipeline
Backenden bruger CD ved hjælp af Jenkins. Jenkins får besked fra github, når der er ændringer i main branch og builder, tester og deployer derefter projektet ved hjælp af [jenkinsfilen](https://github.com/LargeSystemsDevelopment2020/Moonlodge-Backend/blob/main/Jenkinsfile), som ligger i backend repoet.  

Kontrakten bliver også lagt på archiva ved hjælp af Jenkins, men dog kun ved hjælp af github webhooks.  

### Monitorering  
Vores droplet(projekter) bliver monitoreret med [**Grafana på vores droplet**]( http://206.81.29.87:3000).  
*bruger:* admin.  
*password:* guest.  
kig under dashboarded **Node Exporter Full**  
  
  
### Testing  
Da vi har haft store udfordringer med RMI og byg af jar filer, indeholder vores projekter ikke mange brugbare tests endnu.  


### SLA Proposal
[SLA kan blive fundet her](https://github.com/LargeSystemsDevelopment2020/MoonLodge_Contract/blob/master/SLA.md)


* * * 

## Kør projektet lokalt  
*Du skal have MySQL på din lokale maskine*  
- Find link til sql schema og sql insert values(test data) nedenfor og kør dem i en lokal mySQL server.  
- git clone moonlodge backend og ret mysql bruger og password. Kør projektet med f.eks. Intellij.  
- git clone moonlodge frontend og kør det. 
- Brug vores [postman projekt](https://www.getpostman.com/collections/bb1d633ed47153050bab) til at teste vores endpoints.


* * *

# Ressourcer  
Nedenfor er modeller, diagrammer mm. omhandlende projektet. 

* * *

- [Logic Data Model](https://github.com/LargeSystemsDevelopment2020/MoonLodge/blob/master/diagrams/LogicDataModel.md)

* * *

- [Use Case Model](https://github.com/LargeSystemsDevelopment2020/MoonLodge/blob/master/diagrams/UseCase.md)

* * *

- [Sequence Diagram](https://github.com/LargeSystemsDevelopment2020/MoonLodge/blob/master/diagrams/SequenceDiagram.md) 

* * *

- [Assignment 3](https://github.com/LargeSystemsDevelopment2020/MoonLodge_Assigment3)

* * *

- [Jenkins Server](http://206.81.29.87:8090/)

* * *

- [Archiva](http://rasmuslynge.com:8088/)

* * *

- [Moonlodge Schema](https://github.com/LargeSystemsDevelopment2020/Moonlodge-Backend/blob/main/documents/moonlodge_schema.sql)

* * *

- [Moonlodge Insert Values](https://github.com/LargeSystemsDevelopment2020/Moonlodge-Backend/blob/main/documents/insert_values.sql)


* * *

- [Moonlodge Insert EER Diagram](https://github.com/LargeSystemsDevelopment2020/Moonlodge-Backend/tree/rmi/documents)

* * *

To run **unit tests** by using the command `mvn clean test -P dev`

To run **integration test** by using the command `mvn clean verify -P integration-test`

**SonarQube**, a static test suite is also used in this project but only accessible through a token. 

[SonarQube Setup](https://docs.sonarqube.org/latest/setup/get-started-2-minutes/) or just run docker-image 

`docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest`

 * got to http://localhost:9000 admin/admin
 
 * click "+" on the right top corner and create a "Project key". Key name has to match groupid:artifactid of the project

* * *

To see the **Java Code Coverage Library JaCoCo** go to the following path `/target/site/jacoco/index.html`

* * *

To see the **Pit mutation test** reports go to `/target/pit-reports/index.html`

* * *

## Grafana

To access Grafana go to http://206.81.29.87:3000 and login with the username: admin, and password: guest.

1.	Go to http://206.81.29.87:3000 where Grafana is deployed
2.	Login with username: admin & password: guest
3.	On the frontpage look on the starred dashboard, and click “Node Exporter Full”
4.	When you’re on the dashboard, pick datasource: “Prometheus”, job: “node_exporter”, host: ”Localhost:9100”
5.	The look at the right corner and pick which time interval you want to look at, we recommend to use 5 min to see most amount of dataflow
If you don’t have any information, contact the group and we will open the services for you.


 
