pipeline {
  agent {
    docker {
      image 'maven:3.6.3-openjdk-14-slim'
    }

  }
  stages {
    stage('tests') {
      steps {
        sh 'mvn clean package'
      }
    }

    stage('build docker image') {
      steps {
        sh '''docker image rm auth
docker image build -t auth ./
'''
      }
    }

    stage('remove old docker') {
      steps {
        sh '''docker stop auth
docker rm auth'''
      }
    }

    stage('deploy new docker') {
      steps {
        sh '''docker run --restart always -p 8080:8080 -e GOOGLE_CLIENT_ID=${GOOGLE_CLIENT_ID} -e MONGODB_URI=${MONGODB_URI} --name auth -d auth
'''
      }
    }

  }
  environment {
    MONGODB_URI = 'mongodb://mongo:password@192.168.1.228:27017/?retryWrites=true&w=majority'
    GOOGLE_CLIENT_ID = 'placeholder'
  }
}