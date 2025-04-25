pipeline {
    agent any

    tools {
        jdk 'jdk17'
    }

    stages {
        stage('Build') {
            steps {
                sh 'javac Encryptor.java'
            }
        }
     stages {
        stage('OWASP Dependency-Check') {
            steps {
                dependencyCheck additionalArguments: '--scan . --format HTML --format XML', odcInstallation: 'dependency-check'
            }
        }
    }

        stage('Security Check') 
            steps {
                sh '''
                    if grep -q "Base64" Encryptor.java; then
                        echo "Error: The encryption method used (Base64) is insecure and vulnerable to attacks. Please use a stronger encryption algorithm."
                        exit 1
                    fi
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t insecure_encryptor_app .'
            }
        }

        stage('Run Docker Container') {
            steps {
                sh 'docker run --rm insecure_encryptor_app'
            }
        }
    }
}
