package siosio.jsr352.jsl.integration

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
  private lateinit var name:String

  override fun process(): String {
    println("job propertyes: ${jobContext.properties}")
    println("****************************************************************************************************")
    println("${jobContext.jobName} : ${stepContext.stepName}")
    println("name: ${name}")
    return "ok"
  }
}
