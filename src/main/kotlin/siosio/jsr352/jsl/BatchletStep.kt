package siosio.jsr352.jsl

import javax.batch.api.*
import javax.inject.*
import kotlin.reflect.*

data class BatchletStep<T : Batchlet>(
    override val name: String,
    override val nextStep: String? = null,
    val batchletClass: KClass<T>
) : Step {

  init {
    if (!batchletClass.annotations.any { it is Named }) {
      throw IllegalArgumentException("batchlet class not have Named annotation. class: ${batchletClass.qualifiedName}")
    }
  }

  private val properties: MutableList<Property> = mutableListOf()

  fun property(name:String, value:String) {
    properties.add(Property(name, value))
  }

  override fun build() :String {
    val xml = StringBuilder()
    xml.append("<step id=\"${name}\">")
    val beanName = beanName(batchletClass)
    if (properties.isEmpty()) {
      xml.append("<batchlet ref=\"$beanName\" />")
    } else {
      xml.append("<batchlet ref=\"$beanName\">")
      xml.append("<properties>")
      properties.map {
        it.build()
      }.forEach {
        xml.append(it)
      }
      xml.append("</properties>")
      xml.append("</batchlet>")
    }
    xml.append("</step>")
    return xml.toString()
  }
}
