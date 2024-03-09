# CorePoint Logic Java Service

### Build the Project with Maven

1. Add the JSON input to the "src/main/resources/input.json" file.
2. Ensure you are in the project's root directory. Then, build the project using Maven with the following command: `mvn clean package`
3. Then, the calculations are made and the call to the REST endpoint is sent.

---

### Run the JAR File

1. Add the JSON input to the "src/main/resources/input.json" file.
2. Ensure you are in the project's root directory. Then, run the JAR file using the following command: `java -jar target/task_standalone-1.0-SNAPSHOT.jar`
3. Then, the calculations are made and the call to the REST endpoint is sent.

---

### Run Docker Container

##### Build Docker Image

Ensure you are in the project's root directory. Then create a new Docker image named `task-standalone` with the following command:<br>
`docker build -t task-standalone .`

##### Run Docker Image

Once the image is created, you can run the service in a Docker container called `task-standalone` by using the following command:<br>
`docker run --name task-standalone task-standalone`

---

### Tech Stack

* Java 17
* Maven
* Spring Boot
* Spring Context
* Spring Webflux
* Spring Test
* Lombok
* Docker