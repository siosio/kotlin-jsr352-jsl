package siosio.jsr352.jsl.integration.flow

import org.jboss.logging.*
import javax.batch.api.*
import javax.enterprise.context.Dependent
import javax.inject.Named

@Named
@Dependent
class FlowJobBatchlet2 : AbstractBatchlet() {
    val log : Logger = Logger.getLogger(FlowJobBatchlet2::class.java)
    override fun process(): String {
        log.info("FlowJobBatchlet2!!!!!!")
        return "ok"
    }
}
