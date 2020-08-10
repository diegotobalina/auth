pipeline {
  agent {
    dockerfile {
      filename 'Dockerfile'
    }

  }
  stages {
    stage('maven') {
      steps {
        sh 'mvn clean package'
      }
    }

  }
  environment {
    GOOGLE_CLIENT_ID = 'google'
    MONGODB_URI = 'mongodb://mongo:password@192.168.1.228:27017/?retryWrites=true&w=majority'
  }
}