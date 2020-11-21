pipeline {
    agent any

    tools {
        jdk "Default"
        maven "Default"
    }
     
    
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -version'
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
        stage('Deliver') { 
            steps {
                sh './jenkins/scripts/deliver.sh' 
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}