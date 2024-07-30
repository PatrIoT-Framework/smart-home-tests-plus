# Virtual Smart Home Project

This project requires Docker to be running on your machine. Follow the steps below to get the Virtual Smart Home Docker images up and running.

## Prerequisites

- Docker installed and running on your machine.
- Git installed on your machine.

## Steps

1. Clone the virtual-smart-home-plus repositories from GitHub:
    ```
    git clone <repository_url>
    ```
2. Clone the virtual-smart-home-gateway-plus repository from GitHub:
    ```
    git clone <repository_url>
    ```
3. Navigate to the project directory of both repositories:
    ```
    cd <project_directory>
    ```
4. Run Maven to clean and build the both projects:
    ```
    mvn clean install
    ```
5. Create the Docker image for both projects by running the following command in each project directory:
    ```
    docker build -t <image_name> .
    ```
6. Run the `init.py` script to initialize the project:
    ```
    python init.py
    ```

7. Run the tests to ensure everything is working correctly:
    ```
    mvn test
    ```