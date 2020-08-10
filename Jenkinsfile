pipeline {
  agent any
  stages {
    stage('tests') {
      steps {
        sh '''
# remove test image if exists
if [[ "$(docker images -q auth_tests 2> /dev/null)" != "" ]]; then
  docker image rm auth_tests
fi

# create test image
docker image build --no-cache -t auth_tests ./
'''
      }
    }

    stage('deploy') {
      steps {
        sh '''
# remove older docker
if ! docker ps --format \'{{.Names}}\' | egrep \'^auth$\' &> /dev/null; then
    docker stop auth
    docker rm auth
fi

# remove older image
if [[ "$(docker images -q auth 2> /dev/null)" != "" ]]; then
  docker image rm auth
fi

# create new image
docker image build -t auth ./

# create new docker
docker run --restart always -p 8080:8080 -e GOOGLE_CLIENT_ID=${GOOGLE_CLIENT_ID} -e MONGODB_URI=${MONGODB_URI} --name auth -d auth
'''
      }
    }

  }
  environment {
    MONGODB_URI = 'mongodb://mongo:password@192.168.1.228:27017/?retryWrites=true&w=majority'
    GOOGLE_CLIENT_ID = 'placeholder'
  }
}