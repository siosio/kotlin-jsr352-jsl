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
                    step("test-step") {
                        batchlet<TestBatchlet> {
                            property("key1", "value1")
                            property("key2", "value2")

                            end(on = "*")
                            fail(on = "fail", exitStatus = "failed")
                            stop(on = "stop", restart = "step3")
                        }
                    }
                }
        }.job

        assertThat(job)
                .hasFieldOrPropertyWithValue("id", "sample-job")

        assertThat(job.elements)
                .hasSize(1)
                .element(0)
                .hasFieldOrPropertyWithValue("properties", listOf(
                        Property("key1", "value1"),
                        Property("key2", "value2"))
                )
                .hasFieldOrPropertyWithValue("end", Transition("end", "*", null))
                .hasFieldOrPropertyWithValue("fail", Transition("fail", "fail", "failed"))
                .extracting("name", "nextStep", "batchletClass")
                .containsExactly("test-step", null, TestBatchlet::class)
    }

    @Test
    fun specifiedNextStep() {
        val job = object : JobBuilder {
            override val job: Job
                get() =
                job("multi-step") {
                    step("first", "second") {
                        batchlet<TestBatchlet>() {
                            property("key", "value")
                        }
                    }

                    step("second") {
                        batchlet<TestBatchlet>()
                    }
                }
        }.job

        assertThat(job)
                .hasFieldOrPropertyWithValue("id", "multi-step")

        assertThat(job.elements)
                .hasSize(2)

        assertThat(job.elements)
                .element(0)
                .hasFieldOrPropertyWithValue("properties", listOf(
                        Property("key", "value")
                ))
                .extracting("name", "nextStep", "batchletClass")
                .containsExactly("first", "second", TestBatchlet::class)

        assertThat(job.elements)
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
                    step(name = "invalid") {
                        batchlet<InvalidBatchldet>()
                    }
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
