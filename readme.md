### Run the Project

First, download the repository as a ZIP file and build the project with Maven.

Then, you can directly run the JAR file or create a Docker container based on the JAR file.

---

### Build the Project with Maven

1. Add the JSON input to the "src/main/resources/input.json" file.
2. Ensure you are in the project's root directory. Build the project using Maven with the following command: <br>
   `mvn clean package`

---

### Run the JAR File

1. Add the JSON input to the "src/main/resources/input.json" file.
2. Ensure you are in the project's root directory. Run the JAR file using the following command: <br>
   `java -jar target/task_standalone-1.0-SNAPSHOT.jar`

---

### Run Docker Container

##### Build Docker Image

Ensure you are in the project's root directory. Create a new Docker image named "task-standalone" with the following command:<br>
`docker build -t task-standalone .`

##### Run Docker Image

Once the image is created, you can run the service in a Docker container called "task-standalone" by using the following command:<br>
`docker run --name task-standalone task-standalone`

---

### JSON Input Example

```
{
  "operation_number": 1,
  "input_array":[
    {"command":"append", "number": 22},
    {"command":"multiply", "number": 2},
    {"command":"power", "number": 2},
    {"command":"reduce", "number": 2937},
    {"command":"multiply", "number": 4},
    {"command":"divide", "number": 9}
  ]
}
```

---

### Tech Stack

* Java 17
* Maven
* Spring Boot
* Docker