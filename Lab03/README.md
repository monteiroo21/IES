# NMEC-114547

# LAB 3

## 3.1 Accessing databases in SpringBoot

The implementation of DAO layers that provide CRUD functionality on JPA entities can be a repetitive, time-consuming task that we want to avoid in most cases.
Because of this, we use Sprint Boot which makes it easy to create CRUD applications through a layer of standard JPA-based CRUD repositories


### The “UserController” class gets an instance of “userRepository” through its constructor; how is this new repository instantiated? 
The userRepository is instanciated into the UserController using the constructor, as you can see here:

```
UserRepository userRepository;

public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
}
```

### List the methods invoked in the “userRepository” object by the “UserController”. Where are these methods defined? 
The methods invoked in the UserRepository object are:

    save() — saves a user to the repository.
    findAll() — retrieves all users.
    findById() — retrieves a user by ID.
    delete() — deletes a specified user.

These methods are inherited from CrudRepository, which UserRepository extends.

### Where is the data being saved?

The data is being saved in a database, through the save() method. The User entity is mapped to a table due to the @Entity(name = "tbl_user") annotation.

### Where is the rule for the “not empty” email address defined?
In the User class, as seen here:

```
@NotBlank(message = "Email is mandatory")
private String email;
```

## 3.2 Multilayer applications: exposing data with REST interface

Using a Docker Container to run MySQL:

docker run --name mysql5 -e MYSQL_ROOT_PASSWORD=secret1 -e MYSQL_DATABASE=demo -e MYSQL_USER=demo -e MYSQL_PASSWORD=secret2 -p 33060:3306 -d mysql/mysql-server:5.7 

### App Demo:
```
curl -v http://localhost:8080/employees

[{"id":1,"name":"Bilbo Baggins","email":"bbaggings2@gmail.com"},{"id":2,"name":"Frodo Baggins","email":"fbaggings2@gmail.com"}]


curl -v http://localhost:8080/employees/2

{"id":2,"name":"Frodo Baggins","email":"fbaggings2@gmail.com"}

curl -X POST localhost:8080/addemployee -H 'Content-type:application/json' -d '{"name": "Samwise Gamgee", "email": "sgamgee@gmail.com"}'

{"id":3,"name":"Samwise Gamgee","email":"sgamgee@gmail.com"}

curl -X DELETE localhost:8080/employees/3
curl -v http://localhost:8080/employees

[{"id":1,"name":"Bilbo Baggins","email":"bbaggings2@gmail.com"},{"id":2,"name":"Frodo Baggins","email":"fbaggings2@gmail.com"}]
```
