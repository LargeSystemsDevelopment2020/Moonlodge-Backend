pipeline {
    agent {
        docker {
            image "maven:3.6.3-adoptopenjdk-14"
        }
    }
    
    stages {
        stage('Build') {
            steps {
                sh "mvn clean install"
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Unit Test') {
            steps {
                sh 'mvn clean test -P dev'
            }
        }
        stage('Integration Test') {
            steps {
                sh 'mvn clean verify -P integration-test'
            }
        }
        stage('Build Jar File') {
            steps {
                sh 'mvn compile'
            }
        }
    }
}