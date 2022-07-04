package chapter3.chapter3o4

class Customer(val name: String) {
    private var _messages: List<String>? = null // null 허용 private 속성의 초기화

    val messages: List<String>   // 불러올 속성
        get() { // private 함수
            if (_messages == null) {
                _messages = loadMessages()
            }
            return _messages!!
        }

    private fun loadMessages(): MutableList<String> =
        mutableListOf(
            "Initial contact",
            "Convinced them to use Kotlin",
            "Sold training class. Sweet."
        ).also { println("Loaded messages") }
}