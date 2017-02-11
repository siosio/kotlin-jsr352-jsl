package siosio.jsr352.jsl

data class Property(val name: String, val value: String) {
  fun build() : String = "<property name=\"${name}\" value=\"${value}\" />"
}

