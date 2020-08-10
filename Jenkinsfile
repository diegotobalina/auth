pipeline {
  agent any
  stages {
    stage('tests') {
      steps {
        sh '''docker image build -t auth ./
'''
      }
    }

    stage('deploy') {
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