package siosio.jsr352.jsl

import javax.batch.api.*

class Job(val id: String) {

  var steps: MutableList<Step> = mutableListOf()

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
    steps.map {
      it.build()
    }.forEach {
      xml.append(it)
    }
    xml.append("</job>")
    return xml.toString()
  }
}

