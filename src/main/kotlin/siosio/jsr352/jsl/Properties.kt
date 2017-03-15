package siosio.jsr352.jsl

interface Properties {

    val properties: MutableList<Property>

    fun property(name: String, value: String) {
        properties.add(Property(name, value))
    }

    fun buildProperties(): String {
        if (properties.isEmpty()) {
            return ""
        }
        val xml = StringBuilder()
        xml.append("<properties>")
        properties.map {
            it.build()
        }.forEach {
            xml.append(it)
        }
        xml.append("</properties>")
        return xml.toString()
    }
}
