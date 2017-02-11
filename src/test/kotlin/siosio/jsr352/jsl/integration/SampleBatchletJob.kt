package siosio.jsr352.jsl.integration

import siosio.jsr352.jsl.*

class SampleBatchletJob: JobBuilder {
  override fun create(): Job {
    return job("sample") {
      batchlet<SampleBatchlet>("my-step") {
        property("name", "hoge")
      }
    }
  }
}

