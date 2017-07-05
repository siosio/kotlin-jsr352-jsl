package siosio.jsr352.jsl.integration.flow

import org.jboss.logging.*
import javax.batch.api.chunk.*
import javax.enterprise.context.*
import javax.inject.*

@Named
@Dependent
class FlowJobWriter : AbstractItemWriter() {
    val log: Logger = Logger.getLogger(FlowJobWriter::class.java)
    override fun writeItems(items: MutableList<Any>?) {
        log.info(items)
    }
}
