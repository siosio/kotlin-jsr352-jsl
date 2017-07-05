package siosio.jsr352.jsl

class Flow(val name: String, val next: String? = null) : Element {

    private val elemens: ElementHolder = ElementHolder()

    internal fun addStep(step: Step) {
        elemens.add(step)
    }

    override fun build(): String {
        val sb = StringBuilder()
        sb.append("<flow id='${name}' ${next?.let { "next='${it}'" } ?: ""}>")
        sb.append(elemens.build())
        sb.append("</flow>")

        return sb.toString()
    }

}
