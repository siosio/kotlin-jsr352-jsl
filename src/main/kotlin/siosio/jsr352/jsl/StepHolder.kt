package siosio.jsr352.jsl

class StepHolder(steps: MutableList<Step> = mutableListOf()) : MutableList<Step> by steps {

    fun buildStep(): String {
        val xml = StringBuilder()
        map {
            it.build()
        }.forEach {
            xml.append(it)
        }
        return xml.toString()
    }
}
