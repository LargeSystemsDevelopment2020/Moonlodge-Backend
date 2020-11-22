pipeline {
    agent {
        docker {
            image "maven:3.6.3-adoptopenjdk-14"
        }
    } 
    stages {
        stage('Build') {
            steps {
                sh echo "mvn clean install"
                sh "mvn clean install"
            }
        }
        stage('Unit Test') {
            steps {
                sh echo "mvn clean test -P dev"
                sh 'mvn clean test -P dev'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Integration Test') {
            steps {
                sh echo "mvn clean verify -P integration-test"
                sh 'mvn clean verify -P integration-test'
            }
        }
        stage('Build Jar File') {
            steps {
                sh 'mvn compile'
            }
            post {
                always {
                    '/target/jar-folder/moonlodge.jar'
                }
            }
        }
    }
}