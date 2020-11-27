 pipeline {
    agent any
    stages {
        stage('backend'){
            steps {
                dir('boat-backend/'){
                    sh 'ls -la'
                    sh './mvnw clean package -DskipTests'
                }
            }
        }
    }
}