# Springboot API

This API exposes five services: 

•  one that displays the details of all users  
•  one that displays the details of a registered user by id   
•  one that displays the details of a registered user by username  
•  one that allows to register a user   
•  one that delete a user   



A user is defined by:   
•  a user name   
•  a birthdate   
•  a country of residence   

A user has optional attributes:   
•  a phone number     
•  a gender  


Only adult French residents are allowed to create an account.

An embedded H2 database is used.

I use Spring AOP for logging and execution time.

I handle custom Exceptions by using a custom exception Object which is ApiException.java to inform the client when a request is sent.


## Development

Download the sources , go to [https://github.com/colude/CRUD-test-offer](https://github.com/colude/CRUD-test-offer)


Git clone the project

```
git clone https://github.com/colude/CRUD-test-offer.git
```

To run the app, just right click on  

```
com.springboot.crud.plasse.CrudApplication
```

And select 

```
Run As Spring Boot App
```


## Testing

We can find Junit Test and Integration Test in src/test/java


## Postman

Postman collection is located in src/main/resources/postman


```
Crud_Test_Offer.postman_collection.json
```


## Documentation


To see the swagger, go to : [http://localhost:8080/swagger-ui/index.html#/employee-controller](http://localhost:8080/swagger-ui/index.html#/employee-controller)