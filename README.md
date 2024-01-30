## Getting Started

Follow these steps to get the application up and running on your local machine.

### Prerequisites

Before starting, ensure that you have the following installed on your machine:

- Java Development Kit (JDK) version 8 or later
- Maven build tool
- Any web browser of your choice

### Clone the Repository

1. Open a terminal/command prompt.
2. Run the following command to clone the repository:

```shell
git clone https://github.com/DerekAyala/books-task.git
```

### Build the Application

1. Navigate to the project directory using the terminal/command prompt:

```shell
cd books-task
```

2. Build the application using Maven:

```shell
mvn clean install
```

### Run the Application

1. Start the application using the following command:

```shell
mvn spring-boot:run
```

### Test the Endpoints

The application exposes the following endpoints:

- `POST /books`: Create a new book.
- `PUT /books/{id}`: Update an existing book with the given ID.
- `DELETE /books/{id}`: Delete a book with the given ID.

You can test these endpoints using any web API testing tool or a web browser.

1. Open a web browser.
2. Enter the endpoint URL in the address bar (e.g., `http://localhost:8080/books`).
3. Perform the desired HTTP request (e.g., POST, PUT, DELETE) with the necessary request body and headers.
4. Inspect the response from the application to verify the operation.

### Cleanup

To stop the application, press `Ctrl + C` in the terminal/command prompt.


---

Feel free to modify the steps according to your specific project structure and requirements.

Make sure to replace `your-username` and `your-repository` in the clone command with your GitHub username and repository name.

With these steps in the README file, others will be able to clone your repository, build and run the application, test the endpoints, and contribute to the project if desired.

Let me know if you need any further assistance!

![image](https://github.com/DerekAyala/books-task/assets/89038565/47acb4d5-9b4c-4ad4-b33e-58d98b414277)
![image](https://github.com/DerekAyala/books-task/assets/89038565/ffb4ddb5-131a-4184-bc1b-b6197c0403cc)
