package chapter.chapter2o3

import java.text.NumberFormat

object OverloadKt {
    // 기본 파라미터가 정의된 코틀린 함수
    @JvmStatic
    @JvmOverloads
    fun addProduct(name: String, price: Double = 0.0, desc: String? = null) =
        "Adding product with $name, ${desc ?: "None"}, and " + NumberFormat.getCurrencyInstance().format(price)
}