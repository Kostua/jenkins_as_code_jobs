jobs:
  - script: >
      pipelineJob('us2ua') {
          definition {
              triggers {
                  cron('H/5 * * * *')
              }
              cpsScm {
                  scm {
                    git {
                        remote { url 'https://github.com/Kostua/us2ua-shipping-cost-calculator' }
                        branch '*/master'
                        extensions {}
                    }
                  }
                  scriptPath 'Jenkinsfile'
              }
          }
      }