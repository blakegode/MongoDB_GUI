# MongoDB Movie GUI

## Project Description
This Java Swing application provides a graphical user interface (GUI) to interact with a MongoDB database containing movie information. Users can search for movies based on actor names and view the plot of selected movies.

## Features
- Search for movies by actor name.
- View the plot of a selected movie.
- Displays search results in a sortable table.
- User-friendly graphical interface.

## How to Run

### Prerequisites
- Java Development Kit (JDK) (e.g., version 8 or later).
- A running MongoDB instance.
- MongoDB Java Driver JAR.

### Running the Application
Compile the application:
`javac -cp mongo-java-driver.jar MongoDB_GUI.java`
(Replace `mongo-java-driver.jar` with the actual path to your MongoDB Java Driver JAR if it's located elsewhere or named differently.)

**Note:** Ensure the MongoDB Java Driver JAR is in your classpath during compilation and execution.

### Configuration
The application connects to MongoDB using a connection URI defined in the `MongoDB_GUI.java` file (the `MONGO_URI` variable).
If your MongoDB instance is different, or uses different credentials, you **must** update this URI in the `MongoDB_GUI.java` file before compiling and running the application.

## Database Setup
The application expects a MongoDB database with the following configuration:
- **Database Name:** `mongodb-mtext`
- **Collection Name:** `movies`

The `movies` collection should contain documents with at least the following fields:
- `title` (String): The title of the movie.
- `cast` (Array of Strings): A list of actors in the movie. The application searches this field.
- `plot` (String): A summary of the movie's plot.

## Dependencies
This project relies on the MongoDB Java Driver.
- **MongoDB Java Driver:** You will need to download the JAR file for the driver (e.g., `mongo-java-driver-X.Y.Z.jar`) and include it in your classpath when compiling and running the application. You can typically download it from the [official MongoDB website](https://mongodb.github.io/mongo-java-driver/).

## Contributing
Contributions to this project are welcome!
If you have improvements or bug fixes:
1. Fork the repository.
2. Create a new branch for your changes.
3. Make your changes and commit them with clear messages.
4. Submit a pull request for review.
