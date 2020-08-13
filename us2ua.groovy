pipelineJob('us2ua-pipeline') {
  definition {
    cps {
      script('''
pipeline {
    agent any

    tools {
    }

    stages {
        stage('Checkout') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/Kostua/us2ua-shipping-cost-calculator'

                // Run Maven on a Unix agent.
                // sh "mvn -Dmaven.test.failure.ignore=true test"

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
