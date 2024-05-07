Initiative: Demographic Details
   
Develop a web application to capture and store demographic details for individuals.

Requirements:
1. The landing page displays two links:
 - Add a new record - **implemented**
 - List all records - **implemented**
2. Add new record with fields:
- Name - Required - Max 25 characters - **implemented**
- PPS Number* primary key - required - Prevent duplicates - **implemented** 
- Date of Birth - required - dd/mm/yyyy - Cannot be future date - Must be over 16 years old - **implemented**
- Mobile Phone Number - Not required - Must begin with 08 prefix - **implemented**
3. Display all records added and order by the creation date of the record - **implemented - records created most recent get displayed first**
4. If there are no records display message “No records have been created” - **implemented**

Additional features implemented:
  1. Update - we can update existing record using the update icon located at the right side of each record. If we update PPSN, a new record will be created.
  2. Delete - A record can be deleted with a confirm page prompted before deletion
  3. JPA Data Unit testing to test data layer CRUD functionality

**Known Bugs:**
Update functionality implementation: After I have updated the datepicker to display the required date format dd/MM/yyyy, the datepicker doesn't populate existing 
date of birth value when update form is displayed. THis firce us to always update date of birth field when updating a record.

Technologies and tools used:
 - Spring Boot
 - Spring MVC
 - Spring Data / In memory database
 - Thymeleaf
 - JQuery
 - JPA Unit Testing
 - bootstrap
 - Java JDK Version: 17
 - Junit, Mockito
 - Maven
 - IDE: Eclipse (Version 2023-12)

**How to run application:**
1. Please find Jar is located in root folder with file name - demographics-0.0.1-SNAPSHOT.jar
2. Running the command 'java -jar .\demographics-0.0.1-SNAPSHOT.jar' will run application in default 8080 port.
3. Once application server started, navigate to **http://localhost:8080/home** to land on the welcome page with links available to navigate the rest of the pages.

**Demo**  
Home page:  
![image](https://github.com/wossen/demographics/assets/5076199/0cf50fdf-fb20-4c35-af90-f5f7b6904ae2)

Show All  
![image](https://github.com/wossen/demographics/assets/5076199/4f5fc40e-0534-4c53-ab93-3cc85f6c9f11)

Show All No records  
![image](https://github.com/wossen/demographics/assets/5076199/eda0d428-ea2d-4c39-a02e-5fd68c1d4d2a)

Add New record  
![image](https://github.com/wossen/demographics/assets/5076199/97fe4507-2433-4053-b09a-036a6dc82c30)

Add New Record - Validation:  
![image](https://github.com/wossen/demographics/assets/5076199/f6cdb707-4656-49a6-b50f-0141de6a6d17)

Update  
![image](https://github.com/wossen/demographics/assets/5076199/eaecdfd5-18bb-4acb-8fc8-93a01cbf66ae)

Delete  
![image](https://github.com/wossen/demographics/assets/5076199/da7b9ef3-e767-4b0c-8da5-442dba24fcff)

Delete Confirmation  
![image](https://github.com/wossen/demographics/assets/5076199/8899fba4-10d3-4036-b604-d4ff13f9ffab)















