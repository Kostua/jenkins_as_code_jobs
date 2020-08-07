#!/usr/bin/env groovy

pipelineJob('example-pipeline') {
  definition {
    cps {
      script('''
        pipeline {
          agent any
            stages {
              stage("Checkout") {
		            steps {
		                	git url: 'https://github.com/Kostua/us2ua-shipping-cost-calculator'
		            }
              stage ('Build') {
                steps {
                  echo 'Build phase'
                }
              }
              stage ('Unit tests') {
                steps {
                  echo 'Unit testing phase'
                }
              }
              stage ('Deploy') {
                steps {
                  echo 'Deploy phase'
                }
              }
            }
        }
      '''.stripIndent())
      sandbox()

    }
  }
}