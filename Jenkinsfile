pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                url: 'https://github.com/zakimudzakirh/bank-demo.git'
            }
        }

        stage('SonarQube') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh '''
                    /opt/sonar-scanner/bin/sonar-scanner \
                    -Dsonar.projectKey=penjualan \
                    -Dsonar.sources=.
                    '''
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('DB Migration') {
            steps {
                sh '''
                docker run --rm \
                --network bindnamed_jenkins_network \
                -v $PWD/db/migration:/flyway/sql \
                flyway/flyway \
                -url=jdbc:postgresql://postgres:5432/bank \
                -user=sonar \
                -password=sonar123 \
                migrate
                '''
            }
        }

    }
}
