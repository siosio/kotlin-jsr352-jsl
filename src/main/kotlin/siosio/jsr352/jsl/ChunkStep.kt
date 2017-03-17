package siosio.jsr352.jsl

import javax.batch.api.chunk.*

class ChunkStep(
    val name: String,
    nextStep: String? = null,
    allowStartIfComplete: Boolean = false
) : Step(name, nextStep, allowStartIfComplete), Verifier {

    var itemCount: Int = 10
    var timeLimit: Int = 0
    var skipLimit: Int? = null
    var retryLimit: Int? = null

    var reader: Item<out ItemReader>? = null
    var processor: Item<out ItemProcessor>? = null
    var writer: Item<out ItemWriter>? = null

    inline fun <reified T : ItemReader> reader() {
        reader<T> {}
    }

    inline fun <reified T : ItemReader> reader(body: Item<out ItemReader>.() -> Unit) {
        val readerClass = T::class
        verifyNamedAnnotation(readerClass)
        val item = Item("reader", readerClass)
        item.body()
        this.reader = item
    }

    inline fun <reified T : ItemProcessor> processor() {
        processor<T> {}
    }

    inline fun <reified T : ItemProcessor> processor(body: Item<out ItemProcessor>.() -> Unit) {
        val processorClass = T::class
        verifyNamedAnnotation(processorClass)
        val item = Item("processor", processorClass)
        item.body()
        this.processor = item
    }

    inline fun <reified T : ItemWriter> writer() {
        writer<T> {}
    }

    inline fun <reified T : ItemWriter> writer(body: Item<out ItemWriter>.() -> Unit) {
        val writerClass = T::class
        verifyNamedAnnotation(writerClass)
        val item = Item("writer", writerClass)
        item.body()
        this.writer = item
    }

    override fun buildBody(): String {
        verify()
        val xml = StringBuilder()
        xml.append("<chunk item-count='$itemCount' time-limit='$timeLimit' ${buildSkipLimit()} ${buildRetryLimit()}>")
        xml.append(reader!!.buildItem())
        processor?.let {
            xml.append(it.buildItem())
        }
        xml.append(writer!!.buildItem())
        xml.append("</chunk>")
        return xml.toString()
    }

    private fun buildSkipLimit() =
        skipLimit?.let {
            "skip-limit='$skipLimit'"
        } ?: ""

    private fun buildRetryLimit() =
        retryLimit?.let {
            "retry-limit='$retryLimit'"
        } ?: ""

    private fun verify() {
        if (reader == null) {
            throw IllegalStateException("reader mut be set.")
        }
        if (writer == null) {
            throw IllegalStateException("writer mut be set.")
        }
    }

}
