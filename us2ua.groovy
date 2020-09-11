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
    agent any
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
      
        stage('Package') {
            agent {
             docker { image 'maven:3.6-openjdk-15'}
            }
            
            environment {
             // Override HOME to WORKSPACE
             HOME = "${WORKSPACE}"
             // or override default cache directory (~/.npm)
              NPM_CONFIG_CACHE = "${WORKSPACE}/.npm"
            }
      
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/Kostua/us2ua-shipping-cost-calculator'

                // Run Maven on a Unix agent.
                sh "mvn -Dmaven.test.failure.ignore=true clean package"
                  sh "docker build -t kostua/calculator ."

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
