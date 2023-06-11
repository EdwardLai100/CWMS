# PROJ005_CarWashManagementSystem

Hi there! This is a simple Java Spring Boot Application that allows free download, republishing, and modification any per your requirements, fully open source.

This is an ongoing personal project.

The functional use-case feature will cover:
1) Login
2) Wash Process Queue Management, Allow cars to queue in or out before the process started, the cost is immediately calculated upon queueing.
3) Wash Process Flow Management, which Allows the user to manage the car on-queue flow with a defined flow list. By default, the program will initiate with the below flow if the flow list is not found:
3.1) Queued > Washing > Drying > Interior > Detailing > Completed
4) Cost management, to generate a simple invoice and allows pop-up PDF download based on the cars in the queue list.
5) Audit History, Saved all the cars that are being queued and completed the service in 2 separate view lists.

The languages, framework & microservices, architectures, and libraries include:
-Java (JDK17), JavaScript, HTML, CSS, Groovy

-Java Spring Boot 3.1.0, Java Spring Security (for login), Spring MVC (for resource configuration)

-Apache PDFBox

Future release:
-Introducing membership system and implementing JDBC for membership and history storing feature

-Enhance flow management UI where provide a "Release" feature to move to store the car from the queue list into history and remove it from the queue list.

-Introduce role access based on Spring Security, allow Admin role to configure price list and flow list thru UI

-Introduce MVC on text/title naming configuration thru the .properties file.

To run the project:
You may run thru Command Prompt / PowerShell / Terminal, or any IDE with SpringBoot integrated.
For Terminal:
1) Username and Password are located at cwms\src\main\resources\application.properties, you may change the user settings
2) Locate to the project gradlew.bat under directory \cwms
3) Run ".\gradlew.bat bootRun" in your terminal
4) Browse with http://localhost:8080/login
5) Enjoy!

Hope you enjoy the application, please feel free to contact me by email: edwardlaipohyen@hotmail.com

Thank you for your time!
