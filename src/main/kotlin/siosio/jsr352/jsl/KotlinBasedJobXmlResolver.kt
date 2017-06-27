package siosio.jsr352.jsl

import org.jberet.tools.*
import java.io.*

class KotlinBasedJobXmlResolver : AbstractJobXmlResolver() {

    override fun resolveJobXml(jobXml: String, classLoader: ClassLoader): InputStream {
        val jobDefinitionClass = jobXml.removeSuffix(".xml")
        val instance = classLoader.loadClass(jobDefinitionClass).newInstance()

        return if (instance is JobBuilder) {
            ByteArrayInputStream(instance.job.build().toByteArray())
        } else {
            throw IllegalArgumentException("")
        }
    }
}
