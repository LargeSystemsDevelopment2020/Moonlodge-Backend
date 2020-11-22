// to see all env variables in jenkins = http://206.81.29.87:8080/env-vars.html/
pipeline {
    agent {
        docker {
            image "maven:3.6.3-adoptopenjdk-14"
        }
    }
    parameters {
        choice(name: 'Version', choices: ['1.1.0', '1.2.0', '1.3.0'], description: '')
        booleanParam(name: 'executeTests', defaultValue: true, description: '')
    }
    // Access build tools for project. Only three tools available: Gradle, Maven and jdk.
    // Is accessable through "global tool configuration" in jenkins. Is used only if locally.
    // tools {

    // }
    // creating our own env variables
    environment {
        NEW_VERSION = '1.0.3'
        // server-credential is the id you gave when creating a jenkins credential
        //SERVER_CREDENTIALS = credentials('server-credentials')
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
                echo "Building project NR:${BUILD_DISPLAY_NAME}......."
                echo "building version ${NEW_VERSION}"
                sh "mvn clean install"
            }
        }
        stage('Unit Test') {
            when {
                expression {
                    params.executeTests == true
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
                echo "Running Integration Tests ......."
                sh 'mvn clean verify -P integration-test'
            }
        }
        stage('Deploy') {
            steps {
                sh 'mvn compile'
                // withCredentials([
                //     usernamePassword(credentials: 'server-credentials', usernameVariable: USER, passwordVariable: PWD )
                // ]) {
                //     sh "some script ${USER} ${PWD}"
                // }
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