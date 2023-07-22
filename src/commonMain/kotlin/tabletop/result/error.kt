package tabletop.result

data class NotFound<T>(
    val expected: T,
    val message: String? = null,
)