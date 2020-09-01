pipelineJob('us2ua-pipeline') {
  definition {
    cps {
      script('''
      def secrets = [
  [path: 'secret/jenkins/dockerhub', engineVersion: 2, secretValues: [
    [envVar: 'USERNAME', vaultKey: 'username'],
    [envVar: 'PASSWORD', vaultKey: 'password']]],
]
def configuration = [vaultUrl: 'http://vault:8200',  vaultCredentialId: 'vault', engineVersion: 2]
pipeline {
    agent {
      docker { image 'maven:3.6-openjdk-15'}
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '20'))
        disableConcurrentBuilds()
    }
    
    environment {
        HOME = '.'
    }

    triggers {
      pollSCM('H/5 * * * *') 
    }
    stages {
        stage('Vault') {
        steps {
          withVault([configuration: configuration, vaultSecrets: secrets]) {
            sh "echo ${env.USERNAME}"
            sh "echo ${env.PASSWORD}"
          }
        }  
      }
        stage('Tests') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/Kostua/us2ua-shipping-cost-calculator'

                // Run Maven on a Unix agent.
                sh "mvn -Dmaven.test.failure.ignore=true clean package"

            }

        }
        
        stage('Docker build') {
            steps {
                // Run Maven on a Unix agent.
                sh "docker build -t kostua/calculator ."

            }

        }

        stage('CleanWorkspace') {
            steps {
                cleanWs()
            }
        }
    
        
    }
}



'''.stripIndent())
      sandbox()
    }
  }
}
queue('us2ua-pipeline')
