package siosio.jsr352.jsl

abstract class Step(
        private val name: String,
        private val nextStep: String?,
        private val allowStartIfComplete: Boolean
) {

    private var end: End? = null

    fun end(init: End.() -> Unit) {
        end = End("").let {
            it.init()
            if (it.on.isNullOrEmpty()) {
                throw IllegalArgumentException("must be set 'on' value.")
            }
            it
        }
    }

    fun build(): String {
        val xml = StringBuilder()
        xml.append("<step id=\"${name}\"" +
                " ${nextStep?.let { "next='$it'" } ?: ""}" +
                " allow-start-if-complete='${allowStartIfComplete}'>")
        xml.append(buildBody())
        end?.let {
            xml.append("<end on='${it.on}' ${
            if (it.exitStatus.isNotEmpty()) {
                "exit-status='" + it.exitStatus + "'"
            } else {
                ""
            }} />")
        }
        xml.append("</step>")
        return xml.toString()
    }

    abstract fun buildBody(): String
}

data class End(
        var on: String,
        var exitStatus: String = ""
)
