 pipeline {
    agent any
    parameters {
       string(name: 'WORKSPACE', defaultValue: 'development', description:'environment)
    }

    environment {
        EXAMPLE = "0"
    }

    stages {
        stage('backend'){
            steps {
                dir('boat-backend/'){
                    sh './mvnw clean package -DskipTests'
                }
            }
        }
    }
}