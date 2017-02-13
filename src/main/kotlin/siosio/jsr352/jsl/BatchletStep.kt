package siosio.jsr352.jsl

import javax.batch.api.*
import kotlin.reflect.*

data class BatchletStep<T : Batchlet>(
    override val name: String,
    override val nextStep: String? = null,
    val batchletClass: KClass<T>
) : Step, Properties, Verifier {

  init {
    verifyNamedAnnotation(batchletClass)
  }

  override val properties: MutableList<Property> = mutableListOf()

  override fun build() :String {
    val xml = StringBuilder()
    xml.append("<step id=\"${name}\">")
    val beanName = beanName(batchletClass)
    if (properties.isEmpty()) {
      xml.append("<batchlet ref=\"$beanName\" />")
    } else {
      xml.append("<batchlet ref=\"$beanName\">")
      xml.append(super.build())
      xml.append("</batchlet>")
    }
    xml.append("</step>")
    return xml.toString()
  }
}
