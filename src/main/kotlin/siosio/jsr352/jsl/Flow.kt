package siosio.jsr352.jsl

class Flow(val name: String, val next: String? = null) {

    private val steps: StepHolder = StepHolder()

    internal fun addStep(step: Step) {
        steps.add(step)
    }

    fun build(): String {
        val sb = StringBuilder()
        sb.append("<flow id='${name}' ${next?.let { "next='${it}'" } ?: ""}>")
        sb.append(steps.buildStep())
        sb.append("</flow>")

        return sb.toString()
    }

}
