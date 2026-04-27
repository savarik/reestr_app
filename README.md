# reestr_app
>**For now, instructions and information for development will be posted here.** :snowflake:
## Starting the program
To start the program you need to run the file
[ReestrApplication](src/main/java/com/pd/reestr/ReestrApplication.java).
## Connecting to the database
To deploy a database, you need to create a container and a database
according to the settings in [application.properties](src/main/resources/application.properties), or change 
the settings to suit your needs. Detailed deployment instructions are available [here](md/Database.md).

If the database is empty, then when you run the program, it will be filled automatically with sample data 
from the file [data.sql](src/main/resources/data.sql). Otherwise, the data won't be populated. You can 
then delete the data currently in your database and restart the program.  
You can also change the settings in the file [application.properties](src/main/resources/application.properties)
```
# ????????? ?????????? data.sql
spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true
```
## REST api
[REST-controllers](src/main/java/com/pd/reestr/controller) have been implemented, allowing you to make various requests. 
For example, a GET request (in the browser):
```
http://localhost:8080/api/search?country=китай
```