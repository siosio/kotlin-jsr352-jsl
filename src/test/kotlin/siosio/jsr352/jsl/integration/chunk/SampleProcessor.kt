package siosio.jsr352.jsl.integration.chunk

import javax.batch.api.chunk.*
import javax.enterprise.context.*
import javax.inject.*

@Named
@Dependent
class SampleProcessor : ItemProcessor {
    override fun processItem(item: Any): Any? {
        return if ((item as Int) % 10 == 0) {
            item
        } else {
            null
        }
    }
}
