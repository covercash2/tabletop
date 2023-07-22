package tabletop.result

sealed interface Result<T, E>
data class Ok<T, E>(val value: T) : Result<T, E>
data class Err<T, E>(val error: E) : Result<T, E>

fun <T, R, E> Result<T, E>.map(fn: (T) -> R): Result<R, E> = when (this) {
    is Err -> Err(error)
    is Ok -> Ok(fn(value))
}

fun <T, R, E> Result<T, E>.andThen(fn: (T) -> Result<R, E>): Result<R, E> = when (this) {
    is Err -> Err(error)
    is Ok -> fn(value)
}

fun <T, E, F> Result<T, E>.mapErr(fn: (E) -> F): Result<T, F> = when (this) {
    is Err -> Err(fn(error))
    is Ok -> Ok(value)
}

fun <T, E> Result<T, E>.getOrThrow(): T = when (this) {
    is Err -> throw RuntimeException("error extracting value: $error")
    is Ok -> value
}

fun <T, E> Result<T, E>.errOrThrow(): E = when (this) {
    is Err -> error
    is Ok -> throw RuntimeException("expected error, got \"$value\"")
}

fun <T: Any, E: Any> T?.toResult(err: E): Result<T, E> = when (this) {
    null -> Err(err)
    else -> Ok(this)
}

data class Results<T, E>(
    val successes: Sequence<T>,
    val errors: Sequence<E>,
)

fun <T, E> Sequence<Result<T, E>>.partitionErrors(): Results<T, E> =
    Results(
        successes = filter { it is Ok }.map { it.getOrThrow() },
        errors = filter { it is Err }.map { it.errOrThrow() },
    )