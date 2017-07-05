package siosio.jsr352.jsl

class FlowHolder(flows: MutableList<Flow> = mutableListOf()) : MutableList<Flow> by flows {

    fun buildFlow(): String {
        val sb = StringBuilder()
        forEach {
            sb.append(it.build())
        }
        return sb.toString()
    }
}
