 pipeline {
    agent any
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