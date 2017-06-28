package siosio.jsr352.jsl.integration.batchlet

import org.jboss.logging.*
import javax.batch.api.*
import javax.batch.runtime.context.*
import javax.enterprise.context.*
import javax.inject.*

@Dependent
@Named
open class SampleBatchlet @Inject constructor(
    private val jobContext: JobContext,
    private val stepContext: StepContext
) : AbstractBatchlet() {

    @Inject
    @BatchProperty
    private lateinit var name: String

    private val log:Logger = Logger.getLogger(SampleBatchlet::class.java)

    override fun process(): String {
        log.infov("job propertyes: ${jobContext.properties}")
        log.infov("****************************************************************************************************")
        log.infov("${jobContext.jobName} : ${stepContext.stepName}")
        log.infov("name: ${name}")
        return "ok"
    }
}
