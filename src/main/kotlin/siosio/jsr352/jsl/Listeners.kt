package siosio.jsr352.jsl

class Listeners<T : Listener<*>> {

    val listeners: MutableList<T> = arrayListOf()

    fun add(listener: T) {
        listeners.add(listener)
    }

    fun buildListeners(): String {
        return if (listeners.isEmpty()) {
            ""
        } else {
            val xml = StringBuilder()
            xml.append("<listeners>")
            listeners.map {
                xml.append(it.buildListener())
            }
            xml.append("</listeners>")
            xml.toString()
        }
    }
}
