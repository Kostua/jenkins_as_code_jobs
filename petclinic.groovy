pipelineJob('petclinic-pipeline') {
  definition {
    cps {
      script('''
pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '20'))
        disableConcurrentBuilds()
    }
  
    triggers {
      pollSCM('H/5 * * * *') 
    }
    stages {
        stage('Test') {
            agent any
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/spring-projects/spring-petclinic.git'

                // Run Maven on a Unix agent.
                sh "mvn -Dmaven.test.failure.ignore=true test"
                // stash includes: '**/target/*.jar', name: 'app'

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
