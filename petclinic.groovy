pipelineJob('petclinic-pipeline') {
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
queue('petclinic-pipeline')
