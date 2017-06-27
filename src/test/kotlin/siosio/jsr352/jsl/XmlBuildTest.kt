package siosio.jsr352.jsl

import org.assertj.core.api.Assertions.*
import org.junit.*
import javax.batch.api.*
import javax.batch.api.listener.*
import javax.batch.runtime.context.*
import javax.inject.*

class XmlBuildTest {

  @Test
  fun jobLevelListener() {
    val job = object : JobBuilder {
      override val job: Job
        get() =
        job("sample") {
          listener<TestJobListener>()
        }
    }.job

    //language=XML
    assertThat(job.build())
        .isXmlEqualTo("""
            <job id="sample" restartable='true' xmlns="http://xmlns.jcp.org/xml/ns/javaee" version="1.0">
              <listeners>
                <listener ref="joblistener-xml" />
              </listeners>
            </job>
        """)
  }

  @Test
  fun jobLevelListenerWithProperty() {
    val job = object : JobBuilder {
      override val job: Job
        get() =
        job("sample") {
          property("job-property", "value")
          listener<TestJobListener>() {
            property("key", "value")
            property("key1", "value2")
          }
        }
    }.job

    //language=XML
    assertThat(job.build())
        .isXmlEqualTo("""
            <job id="sample" restartable='true' xmlns="http://xmlns.jcp.org/xml/ns/javaee" version="1.0">
              <properties>
                <property name='job-property' value='value'/>
              </properties>
              <listeners>
                <listener ref="joblistener-xml">
                  <properties>
                    <property name="key" value="value" />
                    <property name="key1" value="value2" />
                  </properties>
                </listener>
              </listeners>
            </job>
        """)
  }

  @Test
  fun batchletStep() {
    val job = object : JobBuilder {
      override val job: Job
        get() =
        job("sample-job") {
          restartable = false
          batchlet<TestBatchlet>("my-step")
        }
    }.job

    //language=XML
    assertThat(job.build())
        .isXmlEqualTo("""
            <job id='sample-job' restartable='false' xmlns='http://xmlns.jcp.org/xml/ns/javaee' version='1.0'>
              <step allow-start-if-complete="false" id="my-step">
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

  @Named("joblistener-xml")
  class TestJobListener @Inject constructor(
      private val jobContext: JobContext
  ) : AbstractJobListener() {
  }
}
