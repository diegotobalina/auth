pipeline {
  agent any
  stages {
    stage('docker') {
      steps {
        sh '''#!/usr/bin/env bash
docker-compose build
docker-compose down
docker-compose up -d
docker-compose logs -f --tail=100 auth | ccze -o nolookups'''
      }
    }

  }
  environment {
    GOOGLE_CLIENT_ID = 'google'
    MONGODB_URI = 'mongodb://mongo:password@192.168.1.228:27017/?retryWrites=true&w=majority'
  }
}