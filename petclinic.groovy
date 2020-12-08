multibranchPipelineJob('petclinic-pipeline') {
  definition {
    cpsScm {
      scm {
        git {
          remote {
            url('https://github.com/Kostua/spring-petclinic')
          }
          branch('**')
        }
      }
      lightweight()
    }
  }
}
queue('petclinic-pipeline')
