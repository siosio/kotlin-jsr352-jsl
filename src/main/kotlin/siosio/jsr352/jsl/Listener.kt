package siosio.jsr352.jsl

import kotlin.reflect.*

interface Listener<T> : Properties, Verifier {

    val listener:KClass<*>

    fun buildListener():String {
        val xml = StringBuilder()
        if (properties.isEmpty()) {
            xml.append("<listener ref='${beanName(listener)}' />")
        } else {
            xml.append("<listener ref='${beanName(listener)}'>")
            xml.append(super.buildProperties())
            xml.append("</listener>")
        }
        return xml.toString()
    }
}
