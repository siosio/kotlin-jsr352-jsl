package siosio.jsr352.jsl

import org.assertj.core.api.Assertions.*
import org.junit.*
import javax.batch.api.chunk.*
import javax.inject.*

class ChunkStepTest {

    @Test
    fun readerAndWriter() {
        val job = object : JobBuilder {
            override fun job() =
                job("sample-job") {
                    chunk("chunk-step") {
                        itemCount = 100

                        reader<TestReader>()
                    }
                }
        }.job()

        assertThat(job)
            .hasFieldOrPropertyWithValue("id", "sample-job")

        assertThat(job.steps.first())
            .hasFieldOrPropertyWithValue("name", "chunk-step")
            .hasFieldOrPropertyWithValue("itemCount", 100)
            .hasFieldOrPropertyWithValue("reader", TestReader::class)

    }

    @Named("test-reader")
    class TestReader: AbstractItemReader() {
        override fun readItem(): Any {
            TODO("not implemented")
        }
    }
}
