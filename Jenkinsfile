pipeline {
  agent none
  stages {
    stage('maven') {
      steps {
        sh 'mvn clean package'
      }
    }

    stage('run in docker') {
      steps {
        sh '''#!/usr/bin/env bash
docker-compose build
docker-compose down
docker-compose up -d'''
      }
    }

  }
}