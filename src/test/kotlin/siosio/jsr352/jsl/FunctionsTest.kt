package siosio.jsr352.jsl

import org.assertj.core.api.Assertions.*
import org.junit.*
import javax.inject.*

class FunctionsTest {

  @Test
  fun beanNameTest() {
    assertThat(beanName(Hoge::class)).isEqualTo("hoge")
    assertThat(beanName(Fuga::class)).isEqualTo("fuga-bean")
  }

  @Named
  class Hoge

  @Named(value = "fuga-bean")
  class Fuga
}
