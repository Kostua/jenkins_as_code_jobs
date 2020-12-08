multibranchPipelineJob('peteclinic') {
    branchSources {
        github {
            id('123456789') // IMPORTANT: use a constant and unique identifier
            repository('spring-petclinic')
            repoOwner('Kostua')
        }
    }
    orphanedItemStrategy {
        discardOldItems {
            numToKeep(20)
        }
    }
}
queue('petclinic-pipeline')
