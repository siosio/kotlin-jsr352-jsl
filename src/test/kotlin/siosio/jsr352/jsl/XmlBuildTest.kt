package siosio.jsr352.jsl

import org.assertj.core.api.Assertions.*
import org.junit.*
import javax.batch.api.*
import javax.batch.runtime.context.*
import javax.inject.*

class XmlBuildTest {

  @Test
  fun batchletStep() {
    val job = object : JobBuilder {
      override fun create() =
          job("sample-job") {
            batchlet<TestBatchlet>("my-step")
          }
    }.create()

    assertThat(job.build())
        .isXmlEqualTo("""
            <job id="sample-job" xmlns="http://xmlns.jcp.org/xml/ns/javaee" version="1.0">
              <step id="my-step">
                <batchlet ref="batchlet-xml" />
              </step>
            </job>
        """)
  }

  @Named("batchlet-xml")
  class TestBatchlet @Inject constructor(
      private val stepContext: StepContext
  ) : AbstractBatchlet() {
    override fun process(): String {
      println("name: ${stepContext.stepName}")
      return "success"
    }
  }
}
