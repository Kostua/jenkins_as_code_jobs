multibranchPipelineJob('peteclinic') {
    branchSources {
        git{
            id('petclinicID') // IMPORTANT: use a constant and unique identifier
            remote('https://github.com/Kostua/spring-petclinic.git')
        }
    }
    lightweight()
    orphanedItemStrategy {
        discardOldItems {
            numToKeep(2)
        }
    }
}
queue('petclinic-pipeline')
