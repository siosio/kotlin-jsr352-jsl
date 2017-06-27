package siosio.jsr352.jsl

interface JobBuilder {

    fun job(id: String, init: Job.() -> Unit): Job {
        val job = Job(id)
        job.init()
        return job
    }

    val job: Job
}
