package siosio.jsr352.jsl

import org.assertj.core.api.Assertions.*
import org.junit.*
import javax.batch.api.chunk.*
import javax.inject.*

class ChunkStepTest {

    @Test
    fun readerAndWriter() {
        val job = object : JobBuilder {
          override val job: Job
            get() =
            job("sample-job") {
              chunk("chunk-step") {
                itemCount = 100
                timeLimit = 1000
                skipLimit = 2
                retryLimit = 3
                reader<TestReader>()
                processor<TestProcessor>()
                writer<TestWriter>()
              }
            }
        }.job

        assertThat(job)
            .hasFieldOrPropertyWithValue("id", "sample-job")

        assertThat(job.steps.first())
            .hasFieldOrPropertyWithValue("name", "chunk-step")
            .hasFieldOrPropertyWithValue("itemCount", 100)
            .hasFieldOrPropertyWithValue("timeLimit", 1000)
            .hasFieldOrPropertyWithValue("skipLimit", 2)
            .hasFieldOrPropertyWithValue("reader", Item("reader", TestReader::class))
            .hasFieldOrPropertyWithValue("processor", Item("processor", TestProcessor::class))
            .hasFieldOrPropertyWithValue("writer", Item("writer", TestWriter::class))

        val xml = job.build()
        assertThat(xml)
            // language=xml
            .isXmlEqualTo("""
            <job id='sample-job' restartable='true' xmlns='http://xmlns.jcp.org/xml/ns/javaee' version='1.0'>
              <step id="chunk-step" allow-start-if-complete='false' >
                <chunk item-count='100' time-limit='1000' skip-limit='2' retry-limit='3'>
                  <reader ref='test-reader' />
                  <processor ref='test-processor' />
                  <writer ref='test-writer' />
                </chunk>
              </step>
            </job>
            """)
    }

    @Test
    fun readerAndWriterWithProperties() {
        val job = object : JobBuilder {
          override val job: Job
            get() =
            job("sample-job") {
              chunk("chunk-step") {
                reader<TestReader> {
                  property("key", "value")
                }

                processor<TestProcessor> {
                  property("prop", "prop-value")
                }

                writer<TestWriter> {
                  property("key2", "value2")
                }
              }
            }
        }.job

        assertThat(job.build())
            // language=xml
            .isXmlEqualTo("""
            <job id='sample-job' restartable='true' xmlns='http://xmlns.jcp.org/xml/ns/javaee' version='1.0'>
              <step id="chunk-step" allow-start-if-complete='false'>
                <chunk item-count='10' time-limit='0'>
                  <reader ref='test-reader'>
                    <properties>
                      <property name='key' value='value' />
                    </properties>
                  </reader>
                  <processor ref='test-processor'>
                    <properties>
                      <property name='prop' value='prop-value' />
                    </properties>
                  </processor>
                  <writer ref='test-writer'>
                    <properties>
                      <property name='key2' value='value2' />
                    </properties>
                  </writer>
                </chunk>
              </step>
            </job>
            """)
    }

    @Named("test-reader")
    class TestReader : AbstractItemReader() {
        override fun readItem(): Any {
            TODO("not implemented")
        }
    }

    @Named("test-writer")
    class TestWriter : AbstractItemWriter() {
        override fun writeItems(items: MutableList<Any>?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    @Named("test-processor")
    class TestProcessor : ItemProcessor {
        override fun processItem(item: Any?): Any {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}
