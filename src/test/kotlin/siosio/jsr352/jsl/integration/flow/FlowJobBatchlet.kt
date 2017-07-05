package siosio.jsr352.jsl.integration.flow

import org.jboss.logging.*
import javax.batch.api.*
import javax.enterprise.context.*
import javax.inject.*

@Named
@Dependent
class FlowJobBatchlet : AbstractBatchlet() {
    val log : Logger = Logger.getLogger(FlowJobBatchlet::class.java)
    override fun process(): String {
        log.info("FlowJobBatchlet!!!!")
        return "ok"
    }
}
