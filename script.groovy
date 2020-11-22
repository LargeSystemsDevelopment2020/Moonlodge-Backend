def buildProject() {
    echo 'building the apllication...'
     // jenkins env
    echo "Building project NR:${BUILD_DISPLAY_NAME}......."
    // env created in this file
    echo "${BUILD_REASON}"
    echo "building version ${NEW_VERSION}"
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
