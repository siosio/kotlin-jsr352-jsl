package siosio.jsr352.jsl.integration.multistep

import siosio.jsr352.jsl.*

class MultiStepJob : JobBuilder {
    override val job: Job
        get() = job("multiple-step") {

            step(name = "1", next = "2") {
                batchlet<MultiStepBatchlet>()
            }

            step(name = "2") {
                batchlet<MultiStepBatchlet>()
            }
        }
}


