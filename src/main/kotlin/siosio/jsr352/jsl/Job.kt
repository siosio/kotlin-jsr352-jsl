package siosio.jsr352.jsl

import javax.batch.api.*
import javax.batch.api.listener.*

class Job(val id: String) {

  var steps: MutableList<Step> = mutableListOf()
  var listeners: MutableList<JobLevelListener<*>> = mutableListOf()

  inline fun <reified T : JobListener> listener() {
    listener<T> {}
  }

  inline fun <reified T : JobListener> listener(init: JobLevelListener<*>.() -> Unit) {
    val jobLevelListener = JobLevelListener(T::class)
    jobLevelListener.init()
    listeners.add(jobLevelListener)
  }

  inline fun <reified T : Batchlet> batchlet(name: String) {
    batchlet<T>(name) {}
  }

  inline fun <reified T : Batchlet> batchlet(name: String, init: BatchletStep<*>.() -> Unit) {
    val step = BatchletStep(
        name = name,
        batchletClass = T::class)
    step.init()
    steps.add(step)
  }

  internal fun build(): String {
    val xml = StringBuilder()
    xml.append("<job id=\"${id}\" xmlns=\"http://xmlns.jcp.org/xml/ns/javaee\" version=\"1.0\">")
    xml.append(buildListener())
    xml.append(buildStep())
    xml.append("</job>")
    return xml.toString()
  }

  private fun buildListener(): String {
    if (listeners.isEmpty()) {
      return ""
    }
    val xml = StringBuilder()
    xml.append("<listeners>")
    listeners.map {
      it.build()
    }.forEach {
      xml.append(it)
    }
    xml.append("</listeners>")
    return xml.toString()
  }

  private fun buildStep(): String {
    val xml = StringBuilder()
    steps.map {
      it.build()
    }.forEach {
      xml.append(it)
    }
    return xml.toString()
  }
}

