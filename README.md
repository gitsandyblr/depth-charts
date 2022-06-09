##FanDuel Trading Solutions Coding Challenge – NFL Depth Charts

The depth-charts application is develop to provide a functionality of doing CRUD operations on player with respect to depth-charts being prepared.
The application main focus is on player and him being part of the depth charts, but there is some code for sport and team from the view point of scalability.

#Things required to run the application
install java 11
install gradle 6.8.2
postman for testing

#Running the application:
1. You can git clone the application and run the DepthChartsApplication.java 
2. Alternatively run the command ./gradlew clean build to generate a jar depth-charts-0.0.1-SNAPSHOT.jar and run java -jar depth-charts-0.0.1-SNAPSHOT.jar from root

The end-points are exposed as rest api's which can be tested via postman.
#post request: "/addPlayerToDepthChart"
#delete request: "/removePlayerToDepthChart"
#get request: "/getFullDepthChart"
#post request: "/getBackups"


#Sample requests
http://localhost:8080/fanduel-depth-charts/v1/addPlayerToDepthChart
#body: 
{
    "playerPosition":"LWR",
    "playerName": "Kyle Paker",
    "playerPositionDepth": 1
}

http://localhost:8080/fanduel-depth-charts/v1/removePlayerToDepthChart
#body
{
    "playerNumber":"13",
    "playerName": "Mike Evans"
}

http://localhost:8080/fanduel-depth-charts/v1/getBackups
#body
{
    "playerPosition": "LWR",
    "playerName": "Chris Evans"
}

http://localhost:8080/fanduel-depth-charts/v1/getFullDepthChart -> its a get request, body not required


#Database
The application is configured with H2 database and the url for the same is
#http://localhost:8080/fanduel-depth-charts/h2-console

Please enter the below values to connect
JDBC URL: jdbc:h2:mem:depthcharts
username: sa
password: provided in application.yml file, please refer


#Assumptions made for development purpose:
1. As the problem statement is mainly about depth charts, main functionality is around that.
2. Test cases are also focussed on that and further test cases can be added to different tiers of application like controller
3. Have kept player name to be unique in order for simplicity of operations
4. Although the problem statement says scalability from the perspective of sport and team, i encountered several doubts on this front.
   The application can be further bettered for scalability if there is more information of mapping of a player to sport and team. 
   As of now, for adding a player there is not specific data as in which team or sport we are adding him to, so defaulting values to 
   nfl sport id(1) and tampa bay team id(768)
5. team position for NFL is controlled with scripts as of now as we are not adding them through endpoints. However this can be looked into for enhancements   
   

Faced issues with:
1. H2 database was not taking it easy with adding constraint on the database, hence had to drop it, but in a normal scenario i would 
   put constraint on player_position(player_position_id, player_position_name, player_sports_id) and 
   player(player_id, player_name, player_position, player_position_depth) for better control over data and uniqueness of player

#Next
The application can be further develop for scalability with more functionality for sport, team, position functionalities and related test cases can be added
Using Restassured, further testing can be done for application from controller point of view, as of now only a basic enpoints are provided to do execute depth charts operations 
