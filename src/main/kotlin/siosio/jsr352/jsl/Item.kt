package siosio.jsr352.jsl

import kotlin.reflect.*

data class Item<T : Any>(
    private val itemName: String,
    private val itemClass: KClass<T>
) : Properties {
    override val properties: MutableList<Property> = mutableListOf()

    fun buildItem(): String {
        val xml = StringBuilder()
        xml.append("<$itemName ref='${beanName(itemClass)}'>")
        xml.append(buildProperties())
        xml.append("</$itemName>")
        return xml.toString()
    }
}
