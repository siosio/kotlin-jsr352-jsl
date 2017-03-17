package siosio.jsr352.jsl.integration.chunk

import org.jboss.logging.*
import javax.batch.api.chunk.*
import javax.enterprise.context.*
import javax.inject.*

@Dependent
@Named
class SampleWriter : AbstractItemWriter() {

    private val log: Logger = Logger.getLogger(SampleWriter::class.java)

    override fun writeItems(items: MutableList<Any>?) {
        log.infov("items: $items")
    }
}
