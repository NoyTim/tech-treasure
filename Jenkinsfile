pipeline {
  agent any
  tools {
    maven 'maven_3.5.0'
  }
  stages {
    stage('Build') {
      steps {
        checkout ...
        sh 'mvn clean install'
      }
    }
    stage('Build docker image and start container') {
      steps {
        script {
          sh 'docker build -t NoyTim/tech-treasure docker/.'
        }
      }
    }
  }
}