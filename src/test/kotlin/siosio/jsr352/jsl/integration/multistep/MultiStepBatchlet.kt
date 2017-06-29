package siosio.jsr352.jsl.integration.multistep

import org.jboss.logging.*
import javax.batch.api.*
import javax.enterprise.context.*
import javax.inject.*

@Named
@Dependent
object MultiStepBatchlet : AbstractBatchlet() {
    val log = Logger.getLogger(MultiStepBatchlet::class.java)

    override fun process(): String {
        log.info("batchlet!!!")
        return "ok"
    }
}
