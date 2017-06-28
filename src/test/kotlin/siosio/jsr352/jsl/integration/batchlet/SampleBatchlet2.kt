package siosio.jsr352.jsl.integration.batchlet

import org.jboss.logging.*
import javax.batch.api.*
import javax.batch.runtime.context.*
import javax.enterprise.context.*
import javax.inject.*

@Named
@Dependent
class SampleBatchlet2 @Inject constructor(
    private val stepContext: StepContext
) : AbstractBatchlet() {

    val log: Logger = Logger.getLogger(SampleBatchlet2::class.java)

    override fun process(): String {
        log.infov("step name:{0}", stepContext.stepName)
        return "ok"
    }
}
