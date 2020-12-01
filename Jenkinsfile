// make the script variable available globally
def gv
// to see all env variables in jenkins = http://206.81.29.87:8080/env-vars.html/
pipeline {
    agent any

    //parameters {
        // string(name: 'BUILD_REASON', defaultValue: '', description: 'commit message....')
        // choice(name: 'VERSION', choices: ['1.1.0', '1.2.0', '1.3.0'], description: '')
        // booleanParam(name: 'executeUnitTests', defaultValue: true, description: '')
        // booleanParam(name: 'executeIntegrationTests', defaultValue: false, description: '')
    //}

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
            steps {
                script {
                    gv.buildProject()
                }
                sh "mvn clean install"
            }
        }
        stage('unit test') {
//             when {
//                 expression {
//                     params.executeUnitTests
//                 }
//             }
            steps {
                script {
                    gv.unitTest()
                }
                sh 'mvn clean test -P dev'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('integration test') {
//             when {
//                 expression {
//                     params.executeIntegrationTests
//                 }
//             }
            steps {
                script {
                    gv.integrationTest()
                }
                sh 'mvn clean verify -P integration-test'
            }
        }
        stage('deploy') {
            steps {
                script {
                    gv.deployProject()
                }
                sh 'chmod +x ./deliver.sh'
                sh './deliver.sh'
            }
        }
    }
}