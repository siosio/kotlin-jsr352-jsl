package siosio.jsr352.jsl

import org.jberet.tools.*
import java.io.*

class KotlinBasedJobXmlResolver : AbstractJobXmlResolver() {

    override fun resolveJobXml(jobXml: String, classLoader: ClassLoader): InputStream? {
        val jobDefinitionClass = jobXml.removeSuffix(".xml")
        val instance: Any?
        try {
            instance = classLoader.loadClass(jobDefinitionClass).newInstance()
        } catch(e: Exception) {
            return null
        }

        return if (instance is JobBuilder) {
            ByteArrayInputStream(instance.job.build().toByteArray())
        } else {
            throw IllegalArgumentException("")
        }
    }
}
