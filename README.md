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

        step(name = "my-step", next = "next-step") {
            batchlet<SampleBatchlet> {
              property("name", "hoge")
            }
        
        }
        
        step(name = "next-step") {
          batchlet<SampleBatchlet2>()
        }
    }
}
```

## 実行例
ジョブIDの代わりにジョブ定義を持つクラスの完全修飾名を指定してジョブを実行する
```text
java org.jberet.se.Main sample.SampleBatchletJob
```

