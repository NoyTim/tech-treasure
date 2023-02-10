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
    stage('Push to docker hub') {
      steps {
        withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
          sh 'docker login -u noytim -p ${dockerhubpwd}'
        }
        sh 'docker push noytim/to-do-app'
      }
    }
  }
}