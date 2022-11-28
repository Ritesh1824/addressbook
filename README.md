# AddressBook
Java Address Book Management System is project developed in Spring Boot and Java 17 to Add, Delete, Display and Update 
contact information. 

User can perform action and manage their contact through Command Line Console.

## Pre-requisites
Java, Maven and IDE (Eclipse, Intellij) should be installed and configured in system to build and run the application in local.
JDK 17 and Spring Boot 2.7.0 version is used to build this application.

## Usage
To run program in local got to project path and run mvn spring-boot:run command or import project to IDE
and run application from there. User can perform below action from AddressBook app.

1. Add Contact
2. Display All Contact
3. Remove Contact
4. Update Contact
5. Exit 

## User Program Guide
1. Application will start from UserActionController -> run() method then 
it will go through action method.
2. Here the logic to take user input perform various action has been written.
   User can Select various option to ADD, Display,
   Remove , Update contact and exit from the application.
3. From the UserActionController class the flows goes through UserActionService ->
   useraActionServiceImpl here all the business logic is written to perform validation 
   and connect to ContactInfoRepository and perform CRUD operation .
4. UserActionServiceHelper additional class is created to reduce dependency on main service class.
5. InputValidation utility class is created to store all validation logic in one place.
6. All contact info will be stored in AddressBook.txt file in resources folder.

