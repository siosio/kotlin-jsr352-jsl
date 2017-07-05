package siosio.jsr352.jsl

import javax.batch.api.listener.*

class Job(val id: String) : Properties {

    /** job level properties */
    override val properties: MutableList<Property> = mutableListOf()

    /** job level listeners */
    var listeners: Listeners<JobLevelListener> = Listeners()

    /** restartable atribute(default true) */
    var restartable: Boolean = true

    /** sub element list */
    val elements: ElementHolder = ElementHolder()

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

    fun step(name: String, next: String? = null, init: StepBuilder.() -> Unit) {
        val step = StepBuilder(name, next)
                .apply(init)
                .step ?: throw IllegalStateException("must be set batchlet or chunk.")
        elements.add(step)
    }

    // ****************************** flow
    inline fun flow(name: String, next: String? = null, init: FlowBuilder.() -> Unit) {
        val flow = FlowBuilder(name, next).apply(init).flow
        elements.add(flow)
    }

    fun build(): String {
        val xml = StringBuilder()
        xml.append("<job id='${id}' restartable='$restartable' xmlns='http://xmlns.jcp.org/xml/ns/javaee' version='1.0'>")
        xml.append(buildProperties())
        xml.append(listeners.buildListeners())
        xml.append(elements.build())
        xml.append("</job>")
        return xml.toString()
    }
}

