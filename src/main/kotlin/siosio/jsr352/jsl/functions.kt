package siosio.jsr352.jsl

import javax.inject.*
import kotlin.reflect.*

fun beanName(bean: KClass<*>): String {
  val value = bean.java.getAnnotation(Named::class.java)?.value

  return value?.let {
    if (it.isEmpty()) {
      val name = bean.simpleName!!
      name.first().toLowerCase() + name.substring(1)
    } else {
      it
    }
  } ?: throw IllegalArgumentException("bean not have Named annoation.")
}
