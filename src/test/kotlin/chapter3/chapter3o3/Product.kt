package chapter3.chapter3o3

data class Product(
    // equals, hashCode, toString 등이 완벽하게 갖춰진 엔티티를 나타내는 클래스를 생성하고 싶은 경우 data 키워드를 사용한다.
    val name: String,
    val price: Double,
    val onSale: Boolean = false
)
