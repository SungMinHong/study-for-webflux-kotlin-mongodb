package chapter3.chapter3o6

class LateInitDemo {
    lateinit var name: String

    fun initializeName() {
        println("Before assignment: ${::name.isInitialized}")
        name = "World"
        println("After assignment: ${::name.isInitialized}")
    }
}