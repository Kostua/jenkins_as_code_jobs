pipelineJob('petclinic-pipeline') {
  definition {
    cps {
      script('''
pipeline {
    agent {
      docker {
            image 'maven:3-alpine'
            args '-v $HOME/.m2:/root/.m2'
        }
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '20'))
        disableConcurrentBuilds()
    }
  
    triggers {
      pollSCM('H/5 * * * *') 
    }
    stages {
        stage('Test') {
             agent {
             docker { image 'maven:3.6-openjdk-15'}
            }
            steps {
                // Get some code from a GitHub repository
                git branch: "main", url: 'https://github.com/spring-projects/spring-petclinic'

                // Run Maven on a Unix agent.
                sh "mvn -Dmaven.test.failure.ignore=true test"
                // stash includes: '**/target/*.jar', name: 'app'

            }
        }

  }  
    post {
        always {
            echo 'One way or another, I have finished'
            deleteDir() /* clean up our workspace */
      }
    }
}

'''.stripIndent())
      sandbox()
    }
  }
}
queue('petclinic-pipeline')
