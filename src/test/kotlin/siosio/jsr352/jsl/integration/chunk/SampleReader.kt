package siosio.jsr352.jsl.integration.chunk

import javax.batch.api.chunk.*
import javax.enterprise.context.*
import javax.inject.*

@Dependent
@Named
class SampleReader: AbstractItemReader() {

    val seq = (1..1000).iterator()

    override fun readItem(): Any? {
        return if (seq.hasNext()) {
            return seq.next()
        } else {
            null
        }
    }
}
