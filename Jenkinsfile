pipeline {
    agent {
        docker { dockerfile true}
    }
    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                sh 'mvn -B'
            }
        }
        stage('Unit Test') {
            steps {
                echo 'Unit Testing..'
                sh 'mvn clean test -P dev'
            }
            post {
                always {
                    junit '/target/surefire-reports/*.xml'
                }
            }
        }
    }
}
