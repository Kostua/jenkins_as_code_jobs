pipelineJob('us2ua-pipeline') {
  definition {
    cps {
      script('''
         freeStyleJob('JobsGenerator') {
        scm {
            github('https://github.com/Kostua/us2ua-shipping-cost-calculator', 'master')
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
queue('us2ua-pipeline')
