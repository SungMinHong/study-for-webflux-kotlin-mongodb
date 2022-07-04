package chapter3.chapter3o4

class LazyCustomer(val name: String) {
    private var _messages: List<String>? = null // null 허용 private 속성의 초기화

    val messages: List<String> by lazy { loadMessages() }

    private fun loadMessages(): MutableList<String> =
        mutableListOf(
            "Initial contact",
            "Convinced them to use Kotlin",
            "Sold training class. Sweet."
        ).also { println("Loaded messages") }
}