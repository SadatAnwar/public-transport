# Solution to the Verspatung challenge

## Solution Summary:
The solution is designed as a RESTful service that is built using Scala and [Playframework 2.7](https://www.playframework.com/)

The [routes](conf/routes) file is play's way of defining endpoints and is also a very nice point to start understanding
the application. It gives an overview of the endpoints the application exposes and controller method that is executed when
the endpoint is hit. 

### Run instruction
The application is built using [sbt](https://www.scala-sbt.org/) and all dependencies are configured in the [build.sbt](build.sbt)
file. 

To Run the application you must execute 
```bash
sbt run -J-Dhttp.port=8081
```

Executing the above command will run a development server on port `8081`. Once the server is ready, calling any of the 
endpoints for the first time will trigger compilation of the code and once done will return the result.


## Challenges: 
- Find a vehicle for a given time and X & Y coordinates

  - `localhost:8081/schedule?x=1&y=1&time=10:00:00` will return 
  ```json
  {
      "line": {
          "id": 0,
          "name": "M4"
      },
      "stop": {
          "id": 0,
          "x": 1,
          "y": 1
      },
      "time": "10:00:00",
      "status": {
          "onTime": false,
          "amount": 1
      }
  }
  ```
  indicating the route line M4 is scheduled to arrive at 10:00:00 but along with its status i.e not on time and delay of 1
  

- Return the vehicle arriving next at a given stop

- Indicate if a given line is currently delayed

  - calling `http://localhost:8081/status/<line-name>` will return the status of the line.
  
    eg `http://localhost:8091/status/M4` will return  
        ```json
        {
            "onTime": false,
            "amount": 1
        }
        ```
     also querying for a line that is not present in the lines.csv will lead to a `NotFound(404)` error. 


