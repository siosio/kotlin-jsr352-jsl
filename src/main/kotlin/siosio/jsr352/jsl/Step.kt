package siosio.jsr352.jsl

interface Step {
    val name: String
    val nextStep: String?

    fun build(): String
}
