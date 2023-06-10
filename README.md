# PROJ005_CarWashManagementSystem

Hi there! This is a simple Java Spring Application that allows free download, republishing, and modification any per your requirements, fully open source.

This is an ongoing project.

The use-case functional feature will cover:
1) Login
2) Wash Process Queue Management, Allow cars to queue in or out before the process started.
3) Wash Process Flow Management, Allows the user to manage the car on-queue flow with the below state-change:
3.1) Queued > Washing > Drying > Interior > Detailing > Completed
4) Cost management, which will be based on what type of wash service and the segment of cars, to generate a simple invoice and allows pdf download.
5) Audit History, Saved all the cars that is being queued and completed the service in 2 separated view list.

The languages, framework & microservices, and architecture includes:
1) Java (JDK17), JavaScript, HTML, CSS, Groovy
2) Java Spring 3.1.0, Java Spring Security

Future proposal:
Includes JDBC for membership and history storing feature

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
