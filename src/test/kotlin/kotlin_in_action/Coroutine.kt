package kotlin_in_action

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

class Coroutine {
    fun now() = ZonedDateTime.now().toLocalTime().truncatedTo(ChronoUnit.MILLIS)
    fun log(msg: String) = println("${now()}: ${Thread.currentThread()}: $msg")

    fun launchInGlobalScope() {
        // GlobalScope.launch가 만들어낸 코루틴은 메인함수와 다른 스레드에서 실행됐다.
        GlobalScope.launch {
            log("coroutine started.")
        }
    }

    @Test
    fun globalScope_launch() {
        log("main() started.")
        launchInGlobalScope()
        log("launchInGlobalScope() executed")
        Thread.sleep(5000L)
        log("main() terminated")
    }

    fun runBlockingExample() {
        // runBlocking을 통해 같은 스레드에서 실행되도록 할 수 있다.
        runBlocking {
            launch {
                log("GlobalScope.launch started")
            }
        }
    }

    @Test
    fun globalScope_launch_with_runBlocking() {
        log("main() started.")
        runBlockingExample()
        log("launchInGlobalScope() executed")
        log("main() terminated")
    }


    fun yieldExample() {
        /**
         * 실행결과: 1, 2, 3, 5, 4, 6
         * launch는 즉시 반환된다.
         * runBlocking은 내부 코루틴이 모두 끝난 다음에 반환된다.
         * delay()를 사용한 코루틴은 그 시간이 지날 때까지 다른 코루틴에게 실행을 양보한다.
         * 첫 번째 코루틴이 두 번이나 yield()를 했지만 두 번쨰 코루틴이 delay() 상태에 있었기 떄문에 다시 제어가 첫 번째 코루틴에게 돌아왔다는 사실이다.
         * delay가 너무 짧으면 첫 번째 코루틴의 yield가 동작하기도 한다.
         */
        runBlocking {
            launch {
                log("1")
                yield()
                log("3")
                yield()
                log("5")
            }
            log("after first launch")
            launch {
                log("2")
                delay(100L)
                log("4")
                delay(100L)
                log("6")
            }
            log("after second launch")
        }
    }

    @Test
    fun run_yieldExample() {
        yieldExample()
    }

    fun yieldWithoutDelayExample() {
        /**
         * delay()를 사용한 코루틴은 그 시간이 지날 때까지 다른 코루틴에게 실행을 양보한다. 앞 코드에서 delay(1000L) 대신 yield()를 쓰면 차례대로
         * 1,2,3,4,5,6이 표시될 것이다.
         */
        runBlocking {
            launch {
                log("1")
                yield()
                log("3")
                yield()
                log("5")
            }
            log("after first launch")
            launch {
                log("2")
                yield()
                log("4")
                yield()
                log("6")
            }
            log("after second launch")
        }
    }

    @Test
    fun run_yieldWithoutDelayExample() {
        yieldWithoutDelayExample()
    }

    fun sumAll() {
        /** d1, d2, d3를 하나하나 순서대로 실행하면 총 6초 이상이 걸려야 하지만, 6이라는 결과를 얻을 때까지 총 3초가 걸렸음을 알 수 있다.
         * 또한 async로 코드를 실행하는 데는 시간이 거의 걸리지 않음을 알 수 있다.
         * 그럼에도 불구하고 스레드를 여럿 사용하는 병렬 처리와 달리 모든 async 함수들이 메인 스레드 안에서 실행됨을 볼 수 있다.
         * 이 부분이 async/await과 스레드를 사용한 병렬 처리의 큰 차이다. 특히 이 예제에서는 겨우 3개의 비동기 코드만을 실행했지만,
         * 비동기 코드가 늘어남에 따라 async/await을 사용한 비동기가 빛을 발한다. 실행하려는 작업이 시간이 얼마 걸리지 않거나 I/O에 의한 대기 시간이 크고,
         * CPU 코어 수가 작아 동시에 실행할 수 있는 스레드 개수가 한정된 경우에는 특히 코루틴과 일반 스레드를 사용한 비동기 처리 사이에 차이가 커진다.
         * +) Thread.sleep()은 스레드를 정지시켜 버리니 delay대신 사용한다면 총 6초가 걸리게 된다.
        */
        runBlocking {
            val d1 = async {
                delay(1000L)
                1
            }
            log("after async(d1)")
            val d2 = async {
                delay(2000L)
                2
            }
            log("after async(d2)")
            val d3 = async {
                delay(3000L)
                3
            }
            log("after async(d3)")

            log("1+2+3 = ${d1.await() + d2.await() + d3.await()}")
            log("after await all & add")
        }
    }

    @Test
    fun run_sumAll() {
        """
            Deffered와 Job의 차이는, Job은 아무 타입 파라미터가 없는데 Deffered는 타입 파라미터가 있는 제네릭 타입이라는 점과 Deffered 안에는 await() 함수가 정의돼 있다는 점이다.
            Deffered의 타입 파라미터는 바로 Deffered 코루틴이 계산을 하고 돌려주는 값의 타입이다. 
            Job은 Unit을 돌려주는 Defferred<Unit>이라고 생각할 수도 있을 것이다.
            따라서 async는 코드 블록을 비동기로 실행할 수 있고(제공하는 코루틴 컨텍스트에 따라 여러 스레드를 사용하거나 한 스레드 안에서 제어만 왔다 갔다 할 수도 있다),
            async가 반환하는 Deffered의 await을 사용해서 코루틴이 결과 값을 내놓을 때까지 기다렸다가 결과 값을 얻어낼 수 있다.
        """.trimIndent()
        sumAll()
    }
}