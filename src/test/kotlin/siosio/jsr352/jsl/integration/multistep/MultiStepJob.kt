package siosio.jsr352.jsl.integration.multistep

import siosio.jsr352.jsl.*

class MultiStepJob : JobBuilder {
    override val job: Job
        get() = job("multiple-step") {

            batchlet<MultiStepBatchlet>(
                    name = "1",
                    nextStep = "2"
            ) {
            }

            batchlet<MultiStepBatchlet>(
                    name = "2"
            )

        }
}


