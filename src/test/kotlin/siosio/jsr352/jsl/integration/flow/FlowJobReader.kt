package siosio.jsr352.jsl.integration.flow

import java.io.Serializable
import javax.batch.api.chunk.*
import javax.enterprise.context.*
import javax.inject.*

@Named
@Dependent
class FlowJobReader : AbstractItemReader() {

    val input = (1..100).iterator()

    override fun readItem(): Any? {
        return if (input.hasNext()) {
            input.nextInt()
        } else {
            null
        }

    }
}
