package siosio.jsr352.jsl

import javax.batch.api.chunk.*
import kotlin.reflect.*

class ChunkStep(
    val name: String,
    val nextStep: String? = null,
    val allowStartIfComplete: Boolean = false
) : Step(name, nextStep, allowStartIfComplete), Verifier {

    var itemCount: Int = 10

    var reader: KClass<out ItemReader>? = null

    inline fun <reified T : ItemReader> reader() {
        val kClass = T::class
        verifyNamedAnnotation(kClass)
        this.reader = kClass
    }

    override fun buildBody(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
