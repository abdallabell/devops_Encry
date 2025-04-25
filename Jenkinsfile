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
   
   
        stage('Download Dependency-Check') {
            steps {
                sh '''
                    wget -q https://github.com/jeremylong/DependencyCheck/releases/download/v8.4.0/dependency-check-8.4.0-release.zip
                    unzip -o dependency-check-8.4.0-release.zip -d dependency-check
                '''
            }
        }

        stage('Run Security Scan') {
            steps {
                sh '''
                    dependency-check/bin/dependency-check.sh \\
                        --project "MyProject" \\
                        --scan . \\
                        --format "HTML" \\
                        --out .
                '''
            }
        }

        stage('Publish Report') {
            steps {
                publishHTML(target: [
                    reportDir: '.', 
                    reportFiles: 'dependency-check-report.html', 
                    reportName: 'OWASP Dependency-Check Report'
                ])
            }
        }



        stage('Security Check') {
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

