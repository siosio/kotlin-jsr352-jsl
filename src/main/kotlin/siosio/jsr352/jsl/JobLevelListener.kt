package siosio.jsr352.jsl

import javax.batch.api.listener.*
import kotlin.reflect.*

class JobLevelListener<T : JobListener>(private val listener: KClass<T>) : Properties, Verifier {

    override val properties: MutableList<Property> = mutableListOf()

    init {
        verifyNamedAnnotation(listener)
    }

    override fun build(): String {
        val xml = StringBuilder()
        if (properties.isEmpty()) {
            xml.append("<listener ref='${beanName(listener)}' />")
        } else {
            xml.append("<listener ref='${beanName(listener)}'>")
            xml.append(super.build())
            xml.append("</listener>")
        }
        return xml.toString()
    }
}
