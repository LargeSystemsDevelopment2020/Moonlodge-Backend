private void buildProject() {
    echo 'building the apllication...'
    // jenkins env files
    echo "Building project NR:${BUILD_DISPLAY_NAME}......."
}

private void unitTest() {
    echo "Running Unit Tests ......."
}

def integrationTest() {
    echo "Running Integration Tests ......."
}

def deployProject() {
    echo "deploying version ${params.VERSION}"
    sh 'chmod +x ./deliver.sh'
    sh './deliver.sh'
}


return this 
