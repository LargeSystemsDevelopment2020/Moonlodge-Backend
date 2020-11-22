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

def para() {
    [
    string(name: 'BUILD_REASON', defaultValue: '', description: 'commit message....'),
    choice(name: 'VERSION', choices: ['1.1.0', '1.2.0', '1.3.0'], description: ''),
    booleanParam(name: 'executeUnitTests', defaultValue: true, description: ''),
    booleanParam(name: 'executeIntegrationTests', defaultValue: false, description: '')
    ]

}

return this 
