
# GitHub REST API v3 Project

This repository contains a project that demonstrates the usage of the GitHub REST API v3. It provides a Java-based implementation to interact with the GitHub API and perform various operations.

# Prerequisites

Before running the project, make sure you have the following prerequisites installed:

Java Development Kit (JDK) 17 or higher
Maven

# Getting Started

To get started with this project, follow the steps below:

Clone the repository to your local machine:

    git clone https://github.com/PawelOnlyJava/GithubRestApiv3Project.git

Navigate to the project directory:   

    cd GithubRestApiv3Project

Build the project using Maven:

    mvn clean install

Run the application:

    mvn spring-boot:run

This will start the application and make it accessible at http://localhost:8080.

# Usage

This project provides a REST API endpoint to retrieve non-forked repositories for a given GitHub username. The endpoint is as follows:


    GET /repositories/{username} - Retrieves non-forked repositories for the specified GitHub username.

You can make an HTTP GET request to the endpoint mentioned above to interact with the API and receive a JSON response containing the non-forked repositories.
JSON caintain information about Repository Name, Owner Login and For each branch name and last commit sha

# Example response:

    [
        {
            "repositoryName": "GithubRestApiv3Project",
            "ownerLogin": "PawelOnlyJava",
            "branches": [
                {
                    "branchName": "master",
                    "lastCommitSha": "5278a63568783c9ce95313d783f9be8a597536f7"
                }
            ]
        }
    ]


