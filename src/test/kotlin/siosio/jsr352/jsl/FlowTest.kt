package siosio.jsr352.jsl

import org.assertj.core.api.*
import org.assertj.core.api.Assertions.*
import org.junit.*
import javax.batch.api.*
import javax.batch.api.chunk.*
import javax.enterprise.context.*
import javax.inject.*

class FlowTest {

    @Test
    fun buildFlowTest() {
        val job = object : JobBuilder {
            override val job: Job
                get() =
                job("sample-job") {
                    flow(name = "flow", next = "flow2") {
                        step("step1") {
                            batchlet<FlowBatchlet>()
                        }
                    }
                    flow(name="flow2") {
                        step("step2") {
                            chunk {
                                reader<FlowReader>()
                                writer<FlowWriter>()
                            }
                        }
                    }
                }
        }.job

        assertThat(job.elements)
                .hasSize(2)
        assertThat(job.elements[0])
                .hasFieldOrPropertyWithValue("name", "flow")
                .hasFieldOrPropertyWithValue("next", "flow2")
        assertThat(job.elements[1])
                .hasFieldOrPropertyWithValue("name", "flow2")
                .hasFieldOrPropertyWithValue("next", null)

        val xml = job.build()
        println("xml = ${xml}")
        assertThat(xml)
                // language=xml
                .isXmlEqualTo("""
                    <job id='sample-job' restartable='true' xmlns='http://xmlns.jcp.org/xml/ns/javaee' version='1.0'>
                      <flow id='flow' next='flow2'>
                        <step id='step1' allow-start-if-complete='false'>
                          <batchlet ref='flowBatchlet' />
                        </step>
                      </flow>
                      <flow id='flow2'>
                        <step id='step2' allow-start-if-complete='false'>
                          <chunk item-count='10' time-limit='0'>
                            <reader ref='flowReader' />
                            <writer ref='flowWriter' />
                          </chunk>
                        </step>
                      </flow>
                    </job>
                """)

    }

    @Named
    @Dependent
    class FlowBatchlet : AbstractBatchlet() {
        override fun process(): String {
            return "ok"
        }
    }

    @Named
    @Dependent
    class FlowReader: AbstractItemReader() {
        override fun readItem(): Any {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    @Named
    @Dependent
    class FlowWriter: AbstractItemWriter() {
        override fun writeItems(items: MutableList<Any>?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}
