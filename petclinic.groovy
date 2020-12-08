multibranchPipelineJob('peteclinic') {
    branchSources {
        git{
            id('123456789') // IMPORTANT: use a constant and unique identifier
            remote('https://github.com/Kostua/spring-petclinic.git')
        }
    }
    orphanedItemStrategy {
        discardOldItems {
            numToKeep(20)
        }
    }
}
queue('petclinic-pipeline')
