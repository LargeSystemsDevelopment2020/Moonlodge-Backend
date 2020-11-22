// make the script variable available globally
def gv
// to see all env variables in jenkins = http://206.81.29.87:8080/env-vars.html/

pipeline {
    agent {
        docker {
            image "maven:3.6.3-adoptopenjdk-14"
        }
    }
    parameters {
        string(name: 'BUILD_REASON', defaultValue: '', description: 'commit message....')
        choice(name: 'VERSION', choices: ['1.1.0', '1.2.0', '1.3.0'], description: '')
        booleanParam(name: 'executeUnitTests', defaultValue: true, description: '')
        booleanParam(name: 'executeIntegrationTests', defaultValue: false, description: '')
    }
    // Access build tools for project. Only three tools available: Gradle, Maven and jdk.
    // Is accessable through "global tool configuration" in jenkins. Is used only if locally.
    // tools {

    // }
    // creating our own env variables
    environment {
        NEW_VERSION = '1.0.3'
        //SERVER_CREDENTIALS = credentials('tomcat')
        // server-credential is the id you gave when creating a jenkins credential
        //SERVER_CREDENTIALS = credentials('server-credentials')
    }
    stages {
        stage("init") {
            steps {
                echo "initilize groovy script"
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage('build') {
            // when {
            //     expression {
            //         // Jenkins env file
            //         BRANCH_NAME == 'dev' || CODE_CHANGES == true
            //     }
            // }
            steps {
                script {
                    gv.buildProject()
                }
                sh "mvn clean install"
            }
        }
        // stage('unit test') {
        //     when {
        //         expression {
        //             params.executeUnitTests                 
        //         }
        //     }
        //     steps {
        //         script {
        //             gv.unitTest()
        //         }
        //         sh 'mvn clean test -P dev'
        //     }
        //     post {
        //         always {
        //             junit 'target/surefire-reports/*.xml'
        //         }
        //     }
        // }
        // stage('integration test') {
        //     when {
        //         expression {
        //             params.executeIntegrationTests
        //         }
        //     }
        //     steps {
        //         script {
        //             gv.integrationTest()
        //         }
        //         sh 'mvn clean verify -P integration-test'
        //     }
        // }
        stage('deploy') {
            steps {
                script {
                    gv.deployProject()
                }
                sh 'mvn compile'
                withCredentials([
                    usernamePassword(credentialsId: 'tomcat', usernameVariable: USER, passwordVariable: PWD )
                ]) {
                     sh 'echo some script $USER $PWD'
                }
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