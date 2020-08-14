pipeline {	
  agent any	
  stages {	
    stage('Test') {	
      steps {	
        sh '''	

name=auth_master_test

# remove test image if exists	
if [[ "$(docker images -q $name 2> /dev/null)" != "" ]]; then	
  docker image rm $name	
fi	

# create test image	
docker image build --no-cache -t $name ./	
'''	
      }	
    }	

    stage('Deploy') {	
      steps {	
        sh '''	

name=auth_master	
external_port=8082	
internal_port=8080	

# remove older docker	
if docker ps --format \'{{.Names}}\' | grep $name &> /dev/null; then	
    docker stop $name	
    docker rm $name	
fi	

# remove older image	
if [[ "$(docker images -q $name 2> /dev/null)" != "" ]]; then	
  docker image rm $name	
fi	

# create new image	
docker image build -t $name ./	

# create new docker	
docker run --restart always -p $external_port:$internal_port -e GOOGLE_CLIENT_ID=${GOOGLE_CLIENT_ID} -e MONGODB_URI=${MONGODB_URI} --name $name -d $name	
'''	
      }	
    }	

  }	
  environment {	
    MONGODB_URI = credentials('MONGODB_URI')	
    GOOGLE_CLIENT_ID = credentials('GOOGLE_CLIENT_ID')	
  }	
}
