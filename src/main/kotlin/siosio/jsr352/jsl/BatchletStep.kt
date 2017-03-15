package siosio.jsr352.jsl

import javax.batch.api.*
import kotlin.reflect.*

class BatchletStep<T : Batchlet>(
    name: String,
    nextStep: String? = null,
    allowStartIfComplete: Boolean = false,
    private val batchletClass: KClass<T>
) : Step(name, nextStep, allowStartIfComplete), Properties, Verifier {

    init {
        verifyNamedAnnotation(batchletClass)
    }

    override val properties: MutableList<Property> = mutableListOf()

    override fun buildBody(): String {
        val xml = StringBuilder()
        val beanName = beanName(batchletClass)
        if (properties.isEmpty()) {
            xml.append("<batchlet ref='$beanName' />")
        } else {
            xml.append("<batchlet ref='$beanName'>")
            xml.append(buildProperties())
            xml.append("</batchlet>")
        }
        return xml.toString()
    }
}
