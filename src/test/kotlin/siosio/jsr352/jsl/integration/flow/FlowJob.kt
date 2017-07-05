package siosio.jsr352.jsl.integration.flow

import siosio.jsr352.jsl.*

class FlowJob : JobBuilder {
    override val job: Job =
            job("flow-job") {
                flow("flow1", next = "next-step") {
                    step("flow-step1", next = "flow-step2") {
                        batchlet<FlowJobBatchlet>()
                    }

                    step(name = "flow-step2") {
                        chunk {
                            itemCount = 25
                            reader<FlowJobReader>()
                            writer<FlowJobWriter>()
                        }
                    }
                }

                step(name = "next-step") {
                    batchlet<FlowJobBatchlet2>()
                }
            }
}
