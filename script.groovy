private void buildProject() {
    echo 'building the apllication...'
    // jenkins env files
    echo "Building project NR:${BUILD_DISPLAY_NAME}......."
}

private void unitTest() {
    echo "Running Unit Tests ......."
    sh 'mvn clean test -P dev'
}

def integrationTest() {
    echo "Running Integration Tests ......."
    sh "mvn clean verify -P integration-test"
}

def deployProject() {
    echo "deploying version ${params.VERSION}"
    sh 'chmod +x ./deliver.sh'
    sh './deliver.sh'
}


return this 
