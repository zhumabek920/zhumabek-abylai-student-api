pipeline {
    agent any

    environment {
        STUDENT_NAME    = 'Zhumabek Abylai'
        STUDENT_ID      = '38304'
        IMAGE_NAME      = 'zhumabek-abylai-student-api'
        IMAGE_TAG       = 'v1.1-zhumabek-abylai'
        DOCKER_BUILDKIT = '1'
    }

    triggers {
        pollSCM('H/2 * * * *')
    }

    stages {
        stage('Checkout') {
            steps {
                echo "=== Pipeline of ${STUDENT_NAME} (ID ${STUDENT_ID}) ==="
                checkout scm
                sh 'git log -1 --oneline'
            }
        }

        stage('Build') {
            steps {
                echo "Building Spring Boot app of ${STUDENT_NAME}"
                sh 'chmod +x gradlew'
                sh './gradlew clean bootJar -x test --no-daemon'
            }
        }

        stage('Test') {
            steps {
                echo "Running tests of ${STUDENT_NAME}"
                sh './gradlew test --no-daemon'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'build/test-results/test/*.xml'
                }
            }
        }

        stage('Docker Build') {
            steps {
                echo "Building image ${IMAGE_NAME}:${IMAGE_TAG}"
                sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
            }
        }

        stage('Verify') {
            steps {
                sh "docker image inspect ${IMAGE_NAME}:${IMAGE_TAG} > /dev/null"
                sh "docker images ${IMAGE_NAME}:${IMAGE_TAG}"
                echo "Image ${IMAGE_NAME}:${IMAGE_TAG} exists. Verified for ${STUDENT_NAME}."
            }
        }
    }

    post {
        success { echo "SUCCESS: pipeline of ${STUDENT_NAME} finished" }
        failure { echo "FAILURE: pipeline of ${STUDENT_NAME} failed" }
    }
}
