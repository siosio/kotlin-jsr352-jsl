# JBeretのジョブ定義をKotlinでできるやつ

## JOB定義例
```kotlin
package sample

import siosio.jsr352.jsl.*

class SampleBatchletJob : JobBuilder {
    override val job: Job = job("sample") {
        property("job-property", "job-value")

        listener<SampleJobListener> {
            property("property", "値")
        }

        batchlet<SampleBatchlet>(
            name = "my-step",
            nextStep = "next-step") {
            property("name", "hoge")
        }

        batchlet<SampleBatchlet2>("next-step")
    }
}
```

## 実行例
ジョブIDの代わりにジョブ定義を持つクラスの完全修飾名を指定してジョブを実行する
```text
java org.jberet.se.Main sample.SampleBatchletJob
```

