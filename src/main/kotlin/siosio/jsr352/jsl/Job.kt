package siosio.jsr352.jsl

import javax.batch.api.*
import javax.batch.api.listener.*
import kotlin.reflect.*

class Job(val id: String) : Properties {

    /** job level properties */
    override val properties: MutableList<Property> = mutableListOf()

    /** step list */
    var steps: MutableList<Step> = mutableListOf()

    /** job level listeners */
    var listeners: Listeners<JobLevelListener> = Listeners()

    /** restartable atribute(default true) */
    var restartable: Boolean = true

    /** add job leve listener */
    inline fun <reified T : JobListener> listener() {
        listener<T> {}
    }

    /** add job level listener with body */
    inline fun <reified T : JobListener> listener(init: JobLevelListener.() -> Unit) {
        val jobLevelListener = JobLevelListener(T::class)
        jobLevelListener.init()
        listeners.add(jobLevelListener)
    }

    // ****************************** batchlet
    inline fun <reified T : Batchlet> batchlet(name: String, nextStep: String? = null) {
        batchlet<T>(name, nextStep) {}
    }

    inline fun <reified T : Batchlet> batchlet(name: String, nextStep: String? = null, init: BatchletStep<*>.() -> Unit) {
        val step = BatchletStep(
            name = name,
            nextStep = nextStep,
            batchletClass = T::class)
        step.init()
        steps.add(step)
    }

    // ****************************** chunk
    fun chunk(name: String, next: String? = null, init: ChunkStep.() -> Unit) {
        val step = ChunkStep(name)
        step.init()
        steps.add(step)
    }

    fun build(): String {
        val xml = StringBuilder()
        xml.append("<job id='${id}' restartable='$restartable' xmlns='http://xmlns.jcp.org/xml/ns/javaee' version='1.0'>")
        xml.append(buildProperties())
        xml.append(listeners.buildListeners())
        xml.append(buildStep())
        xml.append("</job>")
        return xml.toString()
    }

    private fun buildStep(): String {
        val xml = StringBuilder()
        steps.map {
            it.build()
        }.forEach {
            xml.append(it)
        }
        return xml.toString()
    }
}

