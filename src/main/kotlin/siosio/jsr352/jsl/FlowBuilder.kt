package siosio.jsr352.jsl

class FlowBuilder(val name: String, val nextStep: String? = null) {

    var flow: Flow = Flow(name, nextStep)

    fun step(name: String, next: String? = null, init: StepBuilder.() -> Unit) {
        val step = StepBuilder(name, next)
                .apply(init)
                .step ?: throw IllegalStateException("must be set batchlet or chunk.")
        flow.addStep(step)
    }
}
