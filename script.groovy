def buildProject() {
    echo 'building the apllication...'
    // jenkins env files
    echo "Building project NR:${BUILD_DISPLAY_NAME}......."
    //echo "building version ${NEW_VERSION}"
}

def unitTest() {
    echo "Running Unit Tests ......."
}

def integrationTest() {
    echo "Running Integration Tests ......."
}

def deployProject() {
    echo "deploying version ${params.VERSION}"
}
return this 
