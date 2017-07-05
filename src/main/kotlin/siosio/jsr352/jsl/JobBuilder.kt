package siosio.jsr352.jsl

interface JobBuilder {

    fun job(id: String, init: Job.() -> Unit): Job {
        return Job(id).apply(init)
    }

    val job: Job
}
