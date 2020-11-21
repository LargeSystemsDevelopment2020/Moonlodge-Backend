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
        stage('Deliver') { 
            steps {
                sh './jenkins/scripts/deliver.sh' 
            }
        }
    }
}