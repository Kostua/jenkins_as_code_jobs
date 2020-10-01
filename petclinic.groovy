pipelineJob('petclinic-pipeline') {
  definition {
    cps {
      script('''
pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        disableConcurrentBuilds()
    }
  
    triggers {
      pollSCM('H/5 * * * *') 
    }
    stages {
        stage('Test') {
             agent {
             docker { image 'maven:3.6-openjdk-15'
                      args '-v $HOME/.m2:/root/.m2'
                      }
            }
            steps {
                // Get some code from a GitHub repository
                git branch: "main", url: 'https://github.com/Kostua/spring-petclinic'

                // Run Maven on a Unix agent.
                sh "mvn -Dmaven.test.failure.ignore=true -Dcheckstyle.skip package"
                stash includes: '**/target/*.jar', name: 'app'

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
