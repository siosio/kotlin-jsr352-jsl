package siosio.jsr352.jsl.integration

import siosio.jsr352.jsl.*

class SampleBatchletJob: JobBuilder {
  override fun create(): Job {
    return job("sample") {

      property("job-property", "job-value")

      listener<SampleJobListener> {
        property("property", "値")
      }

      batchlet<SampleBatchlet>("my-step") {
        property("name", "hoge")
      }
    }
  }
}

