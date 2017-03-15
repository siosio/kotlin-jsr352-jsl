package siosio.jsr352.jsl

import javax.batch.api.listener.*
import kotlin.reflect.*

class JobLevelListener(override val listener: KClass<out JobListener>) : Listener<JobListener> {

    override val properties: MutableList<Property> = mutableListOf()

    init {
        verifyNamedAnnotation(listener)
    }
}
