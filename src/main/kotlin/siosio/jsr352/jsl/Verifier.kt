package siosio.jsr352.jsl

import javax.inject.*
import kotlin.reflect.*

interface Verifier {

  fun verifyNamedAnnotation(bean: KClass<*>) {
    if (!bean.annotations.any { it is Named }) {
      throw IllegalArgumentException("bean class not have Named annotation. class: ${bean.qualifiedName}")
    }
  }

}
