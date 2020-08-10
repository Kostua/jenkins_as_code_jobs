pipelineJob('us2ua-pipeline') {
  definition {
    cps {
      script('''
pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M3"
        nodejs
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

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
    }
}
'''.stripIndent())
      sandbox()
    }
  }
}
