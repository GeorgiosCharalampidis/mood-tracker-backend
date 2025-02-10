# MoodTracker

MoodTracker is a Spring Boot application designed to track and analyze user thoughts and moods. It uses the DeepSeek R1 model running locally via Ollama to provide insights and summaries of user thoughts.

## Getting Started

### Prerequisites

- Java 17
- Maven
- DeepSeek R1 model running locally via Ollama

### Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/moodtracker.git
   cd moodtracker
   ```

2. Build the project using Maven:
   ```sh
   mvn clean install
   ```

3. Run the application:
   ```sh
   mvn spring-boot:run
   ```

### Configuration

Ensure that the DeepSeek R1 model is running locally and accessible at `http://localhost:11434/api/deepseek`.

### API Endpoints

#### User Endpoints

- **Create User**
  ```http
  POST /api/users
  ```
  Request Body:
  ```json
  {
      "username": "john_doe",
      "email": "john@example.com",
      "password": "password123"
  }
  ```

- **Get User by Username**
  ```http
  GET /api/users/{username}
  ```

- **Get All Users**
  ```http
  GET /api/users
  ```

- **Update User**
  ```http
  PUT /api/users/{username}
  ```
  Request Body:
  ```json
  {
      "email": "john_new@example.com",
      "password": "newpassword123"
  }
  ```

- **Delete User**
  ```http
  DELETE /api/users/{username}
  ```

#### Thought Endpoints

- **Create Thought for User**
  ```http
  POST /api/thoughts/{userName}
  ```
  Request Body:
  ```json
  {
      "text": "Today was a good day!",
      "moodRating": 4,
      "date": "2023-10-15",
      "user": {
          "id": 1
      }
  }
  ```

- **Get Thoughts by User**
  ```http
  GET /api/thoughts/{userName}
  ```

- **Get Thoughts by User and Date Range**
  ```http
  GET /api/thoughts/user/{userId}/date-range?startDate=2023-10-01&endDate=2023-10-15
  ```

- **Delete Thought**
  ```http
  DELETE /api/thoughts/{thoughtId}
  ```

- **Delete All Thoughts**
  ```http
  DELETE /api/thoughts
  ```

- **Get User Thoughts Summary**
  ```http
  GET /api/thoughts/{username}/summary
  ```

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.
