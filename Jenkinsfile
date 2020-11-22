pipeline {
    agent {
        docker {
            image "maven:3.6.3-adoptopenjdk-14"
        }
    } 
    stages {
        stage('Build') {
            steps {
                echo "Building project ......."
                sh "mvn clean install"
            }
        }
        stage('Unit Test') {
            when {
                expression {
                    // Jenkins env file
                    BRANCH_NAME == 'dev'
                }
            }
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