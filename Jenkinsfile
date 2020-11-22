// http://206.81.29.87:8080/env-vars.html/
pipeline {
    agent {
        docker {
            image "maven:3.6.3-adoptopenjdk-14"
        }
    } 
    stages {
        stage('Build') {
            // when {
            //     expression {
            //         // Jenkins env file
            //         BRANCH_NAME == 'dev' || CODE_CHANGES == true
            //     }
            // }
            steps {
                echo "Building project ......."
                echo BUILD_DISPLAY_NAME
                sh "mvn clean install"
            }
        }
        stage('Unit Test') {
            // when {
            //     expression {
            //         // Jenkins env file
            //         BRANCH_NAME == 'dev' || BRANCH_NAME == 'main'
            //     }
            // }
            steps {
                echo "Running Unit Tests ......."
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
                echo "Running Integration Tests ......."
                sh 'mvn clean verify -P integration-test'
            }
        }
        stage('Build Jar File') {
            steps {
                sh 'mvn compile'
            }
        }
    }
    // build Status or Build Status Changes
    // executes some logic AFTER all stages are executed
    // post {
    //     // will be executed no matter what
    //     always {

    //     }
    //     // only relevant if buil fails
    //     failure {

    //     }
    //     // only relevant if build succeded
    //     success {

    //     }

    // }
}