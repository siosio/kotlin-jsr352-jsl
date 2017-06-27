package siosio.jsr352.jsl

import org.assertj.core.api.Assertions.*
import org.junit.*
import javax.batch.api.*
import javax.enterprise.context.*
import javax.inject.*


class BatchletStepTest {

    @Test
    fun `defineBatchletStep`() {
        val job = object : JobBuilder {
          override val job: Job
            get() =
            job("sample-job") {
              batchlet<TestBatchlet>("test-step") {
                property("key1", "value1")
                property("key2", "value2")
              }
            }
        }.job

        assertThat(job)
            .hasFieldOrPropertyWithValue("id", "sample-job")

        assertThat(job.steps)
            .hasSize(1)
            .element(0)
            .hasFieldOrPropertyWithValue("properties", listOf(
                Property("key1", "value1"),
                Property("key2", "value2"))
            )
            .extracting("name", "nextStep", "batchletClass")
            .containsExactly("test-step", null, TestBatchlet::class)
    }

    @Test
    fun specifiedNextStep() {
        val job = object : JobBuilder {
          override val job: Job
            get() =
            job("multi-step") {
              batchlet<TestBatchlet>(
                  name = "first",
                  nextStep = "second") {
                property("key", "value")
              }

              batchlet<TestBatchlet>("second")
            }
        }.job

        assertThat(job)
            .hasFieldOrPropertyWithValue("id", "multi-step")

        assertThat(job.steps)
            .hasSize(2)

        assertThat(job.steps)
            .element(0)
            .hasFieldOrPropertyWithValue("properties", listOf(
                Property("key", "value")
            ))
            .extracting("name", "nextStep", "batchletClass")
            .containsExactly("first", "second", TestBatchlet::class)

        assertThat(job.steps)
            .element(1)
            .hasFieldOrPropertyWithValue("properties", listOf<Property>())
            .extracting("name", "nextStep", "batchletClass")
            .containsExactly("second", null, TestBatchlet::class)
    }

    @Test
    fun withoutNamedAnnotation_shouldThrowException() {
        val job: JobBuilder = object : JobBuilder {
          override val job: Job
            get() =
            job("invalid-job") {
              batchlet<InvalidBatchldet>(name = "invalid") {}
            }
        }

        assertThatThrownBy {
            job.job
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("bean class not have Named annotation. class: siosio.jsr352.jsl.BatchletStepTest.InvalidBatchldet")
    }

    @Named("batchlet-step")
    @Dependent
    class TestBatchlet : AbstractBatchlet() {
        override fun process(): String = "ok"
    }

    class InvalidBatchldet : AbstractBatchlet() {
        override fun process(): String = "ok"
    }
}
