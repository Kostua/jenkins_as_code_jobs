pipelineJob('us2ua-pipeline') {
  definition {
    cps {
      script('''
      pipeline {
    agent {
      docker { image 'maven:3.6-openjdk-15'}
    } 
    environment {
        HOME = '.'
    }

    triggers {
      pollSCM('H/5 * * * *') 
    }
    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/Kostua/us2ua-shipping-cost-calculator'

                // Run Maven on a Unix agent.
                sh "mvn -Dmaven.test.failure.ignore=true test"

                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
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
