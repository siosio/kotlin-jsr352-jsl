package siosio.jsr352.jsl

import javax.batch.api.*

abstract class Step(
        private val name: String,
        private val nextStep: String?,
        private val allowStartIfComplete: Boolean
) : Element {

    private var end: Transition? = null
    private var fail: Transition? = null
    private var stop: Stop? = null

    fun end(on: String, exitStatus: String? = null) {
        end = Transition("end", on, exitStatus)
    }

    fun fail(on: String, exitStatus: String? = null) {
        fail = Transition("fail", on, exitStatus)
    }

    fun stop(on: String, restart: String, exitStatus: String? = null) {
        stop = Stop(on, restart, exitStatus)
    }

    override fun build(): String {
        val xml = StringBuilder()
        xml.append("<step id=\"${name}\"" +
                " ${nextStep?.let { "next='$it'" } ?: ""}" +
                " allow-start-if-complete='${allowStartIfComplete}'>")
        xml.append(buildBody())
        xml.append(buildTransition(end))
        xml.append(buildTransition(fail))
        xml.append(buildStop())
        xml.append("</step>")
        return xml.toString()
    }

    private fun buildStop(): String {
        return stop?.let {
            "<stop on='${it.on}' restart='${it.restart}' ${
            if (it.exitStatus != null) {
                "exit-status='" + it.exitStatus + "'"
            } else {
                ""
            }} />"
        } ?: ""
    }

    private fun buildTransition(transition: Transition?): String {
        return transition?.let {
            "<${it.name} on='${it.on}' ${
            if (it.exitStatus != null) {
                "exit-status='" + it.exitStatus + "'"
            } else {
                ""
            }} />"
        } ?: ""
    }

    abstract fun buildBody(): String
}

class StepBuilder(val name: String, val nextStep: String? = null) {

    var step: Step? = null

    // ****************************** batchlet
    inline fun <reified T : Batchlet> batchlet() {
        batchlet<T> {}
    }

    inline fun <reified T : Batchlet> batchlet(init: BatchletStep<*>.() -> Unit) {
        step = BatchletStep(
                name = name,
                nextStep = nextStep,
                batchletClass = T::class).apply(init)
    }

    // ****************************** chunk
    inline fun chunk(init: ChunkStep.() -> Unit) {
        step = ChunkStep(
                name = name,
                nextStep = nextStep
        ).apply(init)
    }
}

data class Transition(
        val name: String,
        val on: String,
        val exitStatus: String?
)

data class Stop(
        val on: String,
        val restart: String,
        val exitStatus: String?
)
