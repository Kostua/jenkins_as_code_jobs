#!/usr/bin/env groovy
 pipelineJob('JobsGenerator') {

        def repo = 'https://github.com/Kostua/us2ua-shipping-cost-calculator'

        description("Pipeline for $repo")

        definition{
          triggers {
                  cron('H/5 * * * *')
              }
          cpsScm {
            scm {
              git {
                remote { url(repo) }
                branches('master')
                scriptPath('Jenkinsfile')
                extensions { }
              }
            }
          }
        }
      }