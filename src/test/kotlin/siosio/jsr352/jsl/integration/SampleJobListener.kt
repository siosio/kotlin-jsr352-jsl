package siosio.jsr352.jsl.integration

import javax.batch.api.*
import javax.batch.api.listener.*
import javax.batch.runtime.context.*
import javax.enterprise.context.*
import javax.inject.*

@Named
@Dependent
class SampleJobListener @Inject constructor(
    private val jobContext: JobContext
) : AbstractJobListener() {

    @BatchProperty
    @Inject
    private lateinit var property: String

    override fun beforeJob() {
        println("${jobContext.jobName} start!!!!!!!!!!!")
        println("********** ${property} **********")
    }

    override fun afterJob() {
        println("${jobContext.jobName} end!!!!!!!!!!!")
    }
}
