package siosio.jsr352.jsl

abstract class Step(
    private val name: String,
    private val nextStep: String?,
    private val allowStartIfComplete: Boolean
) {

    fun build(): String {
        val xml = StringBuilder()
        xml.append("<step id=\"${name}\"" +
            " ${nextStep?.let { "next='$it'" } ?: ""}" +
            " allow-start-if-complete='${allowStartIfComplete}'>")
        xml.append(buildBody())
        xml.append("</step>")
        return xml.toString()
    }

    abstract fun buildBody(): String
}
