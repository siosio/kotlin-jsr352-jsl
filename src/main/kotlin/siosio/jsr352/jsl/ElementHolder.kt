package siosio.jsr352.jsl

class ElementHolder(elements: MutableList<Element> = mutableListOf()) : MutableList<Element> by elements {

    fun build(): String {
        val sb = StringBuilder()
        forEach {
            sb.append(it.build())
        }
        return sb.toString()
    }

}
