freeStyleJob('petclinic') {
  definition {
    cps {
      script('''
           freeStyleJob('JobsGenerator') {
        scm {
            github('https://github.com/Kostua/spring-petclinic', 'main')
        }
        steps {
            dsl {
              external('Jenkinsfile')
            }
        }
      }
'''.stripIndent())
      sandbox()
    }
  }
}
queue('petclinic')
