This program is used for administration of list of employees. Using REST API endpoints employees can be added, updated and removed from database. 
It can be also used for simple statistic, where employees are count according their position and it is count average age for each position.

### What is used to run this program:
- any program, which is enable to run java script (intellij was used in my case - source https://www.jetbrains.com/idea/)
- there is still no frontend to use it so any test tool is necessary (postman in my case - source https://www.postman.com/)
- local database to store data persistently (SQLite - source https://sqlitebrowser.org/)

### When all three programs are ready it is necessary to create DB file.
- Run DB Browser for SQLite
- click on new DB and set the name of your DB (in my case hrsystem.db)
- create required table - via SQL command  (bookmark fourth from left)
      
                      CREATE TABLE "AllEmployees" (
                    	"employeeId"	INTEGER,
                        "employeeName"	TEXT,
                    	"employeeSurname"	TEXT,
                    	"employeeAge"	INTEGER,
                    	"employeePosition"	TEXT,
                    	PRIMARY KEY("employeeId")
                      )

- or by adding and removing field and changing name and type manually (it is quite intuitive GUI).


### In the program full path to DB file is necessary to change (class EmployeeRepository, line 14). 
- Now there is set "jdbc:sqlite:/C:/Users/User/Desktop/SQlite/DB_Browser_for_SQLite/hrsystem.db" which was used in my case (my DB file was stored in downloaded folder with SQLite on desktop).
- When DB file is set, program can be started. It required port 8080. 

### If it is running without errors postman can be started and used to test all methods
- GET and POST is used for http://localhost:8080/hrsystem
- PUT and DELETE required also add number of ID to modify or delete (for example http://localhost:8080/hrsystem/5 to modify or delete employee with ID 5)
- GET "http://localhost:8080/hrsystem/statistic" is used to run some small statistic which will write all the position with information how many employees is working on this position and also it will calculate their average age (GET methods are working also in any web browser)
      

      
