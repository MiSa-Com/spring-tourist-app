# MiSa-Tech: TouristApp

This is a simple app that allows you to search for places of interest in a city. It uses the Google Places API to search for places and the Google Maps API to display the results on a map.

## Getting Started

### Prerequisites
Make sure you have the following installed:
* [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
* [Maven](https://maven.apache.org/download.cgi)
* [SQL Server](https://www.microsoft.com/en-us/sql-server/sql-server-downloads)
* Recommended: [IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=windows)
* Recommended: [Postman](https://www.postman.com/downloads/)
* Recommended: [DBeaver](https://dbeaver.io/download/)

### Installation
1. Clone the repo
```sh
git clone https://github.com/MiSa-Tech/spring-tourist-app.git
```
2. Open the project in IntelliJ IDEA
3. Open the Maven tool window (View -> Tool Windows -> Maven)
4. Click the refresh button to load the project's dependencies
5. Open the Database tool window (View -> Tool Windows -> Database)
6. Click the plus button and select Data Source -> SQL Server
7. If you don't have the SQL Server driver installed, click the download link and follow the instructions
8. Enter the following details:
    * Host: localhost
    * Port: 1433
    * Database: TouristApp
    * User: sa
    * Password: Password123
9. Click Test Connection to make sure the connection is working
10. Click Apply and OK
11. Open the Maven tool window again and run the following commands:
    * `mvn clean install`
    * `mvn spring-boot:run`
    * `mvn spring-boot:run -Dspring-boot.run.profiles=dev,prod,test,local,dev-remote`
    * `mvn spring-boot:run -Dspring-boot.run.profiles=dev,prod,test,local,dev-remote -Dspring-boot.run.arguments=--server.port=8081`
12. Open Postman and import the collection from the `postman` folder
13. Run the `Create User` request
14. Run the `Login` request
15. Copy the token from the response
16. Click the `Authorization` tab
17. Select `Bearer Token` from the Type dropdown
18. Paste the token into the Token field
19. Run the `Get Places` request

## API Documentation
The API documentation is available at http://localhost:8080/swagger-ui.html

## Usage
The app has main features:
* Search for places of interest in a city
* Save places to a list
* View saved places on a map
* Delete saved places
* View the weather forecast for a city
* View the weather forecast for a saved place
* View the weather forecast for a city on a map
* View the weather forecast for a saved place on a map
* View the weather forecast for a city on a map for a specific date
* View the weather forecast for a saved place on a map for a specific date
* View the hotels in a city
* View the restaurants in a city
* Recommend a hotel in a city
* Recommend a restaurant in a city
* Recommend an itinerary for a city
* Recommend an itinerary for a saved place

## Contact
* [MiSa-Tech](https://github.com/MiSa-Tech)
* [Minh NGO](mailto:ngocminhk62@gmail.com)
* [Ngoc Sang NGO](mailto:ngocsangair01@gmail.com)

## Acknowledgements
* [GitHub Pages](https://pages.github.com)