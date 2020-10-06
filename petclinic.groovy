pipelineJob('job-dsl-plugin') {
  definition {
    cpsScm {
      scm {
        git {
          remote {
            url('https://github.com/Kostua/spring-petclinic')
          }
          branch('*/main')
        }
      }
      lightweight()
    }
  }
}
