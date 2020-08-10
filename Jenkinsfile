pipeline {
  agent {
    docker {
      image 'maven:3.6.3-openjdk-14-slim'
    }

  }
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