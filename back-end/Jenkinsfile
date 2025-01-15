pipeline {
    agent any

    tools {
        maven "Maven"
    }

    environment {
        DOCKER_HUB_REPO = 'asmaaba/musicmanager'
    }

    stages {
        stage('Clone Repo') {
            steps {
                cleanWs()
                git branch: 'main', url: 'https://github.com/asmaabarj/MusicManager'
            }
        }

        stage('Build Artifact') {
            steps {
                bat 'echo %CD%'
                bat 'dir'
                bat '''
                    mvn clean package -DskipTests
                '''
            }
        }

        stage('Prepare Docker Build') {
            steps {
                script {
                    writeFile file: 'Dockerfile', text: '''FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "app.jar"]'''
                }
            }
        }

        stage('Docker Build') {
            steps {
                bat "docker build -t ${DOCKER_HUB_REPO}:latest ."
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-token', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                    bat "docker login -u %DOCKER_USERNAME% -p %DOCKER_PASSWORD%"
                    bat "docker push ${DOCKER_HUB_REPO}:latest"
                }
            }
        }
    }

    post {
        always {
            bat 'docker logout'
        }
    }
}