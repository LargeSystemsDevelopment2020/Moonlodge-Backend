pipeline {
    agent any
    
    stages {
            stage ("java-installation") {
        tools {
            jdk "Default_JDK"
        }
        steps {
            sh 'java -version'
        }
    }
           stage("maven-installation") {
        tools {
            maven "Default"
        }
        steps {
            sh 'mvn -version'
        }
    }
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