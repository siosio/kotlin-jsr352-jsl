package siosio.jsr352.jsl

import org.assertj.core.api.Assertions.*
import org.junit.*
import javax.batch.api.listener.*
import javax.inject.*

class JobLevelListenerTest {

  @Test
  fun defineJobLevelListener() {
    val job = object : JobBuilder {
      override fun create() =
          job("sample") {
            listener<JobListenerTest1>()
          }
    }.create()

    assertThat(job.listeners)
        .hasSize(1)
        .element(0)
        .hasFieldOrPropertyWithValue("listener", JobListenerTest1::class)
  }

  @Test
  fun defineJobLevelListenerAndProperty() {
    val job = object : JobBuilder {
      override fun create() =
          job("sample") {
            listener<JobListenerTest1> {
              property("key", "value")
              property("key2", "value2")
            }
          }
    }.create()

    assertThat(job.listeners)
        .hasSize(1)
        .element(0)
        .hasFieldOrPropertyWithValue("listener", JobListenerTest1::class)
        .hasFieldOrPropertyWithValue("properties", listOf(Property("key", "value"), Property("key2", "value2")))
  }

  @Test
  fun withoutNamedAnnotation_shouldThrowException() {
    val jobBuilder = object : JobBuilder {
      override fun create() =
          job("sample") {
            listener<InvalidJobListener>()
          }
    }

    assertThatThrownBy {
      jobBuilder.create()
    }.isInstanceOf(IllegalArgumentException::class.java)
        .hasMessage("bean class not have Named annotation. class: ${InvalidJobListener::class.qualifiedName}")
  }

  @Named("job-listener-test")
  class JobListenerTest1 : AbstractJobListener() {}

  class InvalidJobListener : AbstractJobListener() {}
}
