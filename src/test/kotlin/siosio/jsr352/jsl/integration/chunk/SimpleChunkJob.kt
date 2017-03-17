package siosio.jsr352.jsl.integration.chunk

import siosio.jsr352.jsl.*

class SimpleChunkJob : JobBuilder {
    override fun job(): Job =
        job("simple-chunk") {
            chunk("first") {
                itemCount = 100

                reader<SampleReader>()
                processor<SampleProcessor>()
                writer<SampleWriter>()
            }
        }

}
