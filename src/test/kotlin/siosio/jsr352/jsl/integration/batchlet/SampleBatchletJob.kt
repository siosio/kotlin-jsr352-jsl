package siosio.jsr352.jsl.integration.batchlet

import siosio.jsr352.jsl.*

class SampleBatchletJob : JobBuilder {
    override val job: Job
        get() = job("sample") {
            property("job-property", "job-value")

            listener<SampleJobListener> {
                property("property", "値")
            }

            batchlet<SampleBatchlet>(name = "my-step", nextStep = "next-step") {
                property("name", "hoge")

                end {
                    on = "ok"
                }
            }

            batchlet<SampleBatchlet2>("next-step")
        }
}

