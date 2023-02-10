pipeline {
  agent any
  tools {
    maven 'Maven-3.9.0'
  }
  stages {
    stage('Build') {
      steps {
        checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/NoyTim/tech-treasure']])
        sh 'mvn clean install -DskipTests'
      }
    }
    stage('Test') {
      steps {
        sh 'mvn test'
      }
    }
    stage('Build docker image') {
      steps {
        script {
          sh 'docker build -t noytim/to-do-app .'
        }
      }
    }
  }
}