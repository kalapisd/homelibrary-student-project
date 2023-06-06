# Home library student project 

Hello and welcome to the readme for the Home Library REST API!
This REST API is designed to easily manage your home library.

With this REST API, you can perform a variety of CRUD (create, read, update, delete) operations on the data stored in your database.
To get started, you will need to have a basic understanding of REST APIs and how to interact with them using HTTP requests. 
You will also need to have a tool like Postman or curl to make requests to the API.

You can read about the endpoints supported by the API in the [Swagger documentation](http://localhost:8080/swagger-ui/index.html)

## Installation guide
- To run this API in Docker containers, just execute

```
docker-compose.bat
```

or

```
docker-compose.sh
```

depending on your operating system. This will automatically create docker images and run the corresponding containers.

- If you only need to set up the database in a Docker container, run 
```
docker-db-build.bat
```
and then 
```
docker-db-run.bat
```
on Windows operating system, or
```
docker-db-build.sh
```
and then
```
docker-db-run.sh
```
on Linux.  

Set the following environment variables on your computer to run this application:  
DB_PORT=5442  
DB_URL=localhost  
POSTGRES_PASSWORD=postgres  
POSTGRES_USER=postgres  
GOOGLE_BOOKS_API_KEY= ** put your own Google Books API key here (see below) **

### Testing the application
You can use the Jacoco Maven plugin to generate reports for test coverage. 
The plugin is configured to evaluate test results during Maven verify phase.

### An extra feature
The API also supports getting book data from Google Books API.
To use this API feature, you will need to provide a valid Google Books API key in your requests.

This will allow the Google Books API to identify you and authenticate your requests. You can obtain an API key easily by following the instructions provided by Google: 
https://cloud.google.com/docs/authentication/api-keys

I hope you find this API useful and easy to use. If you have any questions or feedback, please don't hesitate to [contact me](mailto:kalapisd@gmail.com)!
