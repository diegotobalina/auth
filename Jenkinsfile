pipeline {
  agent any
  stages {
    stage('docker') {
      steps {
        sh '''docker image build -t auth ./
docker run --restart always -p 8080:8080 -e GOOGLE_CLIENT_ID="google_id" -e MONGODB_URI="mongodb://mongo:password@192.168.1.228:27017/?retryWrites=true&w=majority" --name auth -d auth
'''
      }
    }

  }
}