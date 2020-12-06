def gv

pipeline {
    agent any

    stages {
        stage("init") {
            steps {
                echo "initialize groovy script"
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
    post {
        // will be executed no matter what
        always {
            gv.afterEachBuild()
        }
        // only relevant if buil fails
        failure {
            gv.whenBuildFailed()
        }
        // only relevant if build succeded
        success {
            gv.whenBuildSucceded()
        }
    }
}