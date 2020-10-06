pipelineJob('us2ua-pipeline') {
  definition {
    cpsScm {
      scm {
        git {
          remote {
            url('https://github.com/Kostua/us2ua-shipping-cost-calculator')
          }
          branch('*/master')
        }
      }
      lightweight()
    }
  }
}
queue('us2ua-pipeline')
