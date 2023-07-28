/* (C)2023 */
package tabletop.result

sealed interface Result<T, E>
data class Ok<T, E>(val value: T) : Result<T, E>
data class Err<T, E>(val error: E) : Result<T, E>

inline fun <T, R, E> Result<T, E>.map(fn: (T) -> R): Result<R, E> = when (this) {
    is Err -> Err(error)
    is Ok -> Ok(fn(value))
}

inline fun <T, R, E> Result<T, E>.andThen(fn: (T) -> Result<R, E>): Result<R, E> = when (this) {
    is Err -> Err(error)
    is Ok -> fn(value)
}

inline fun <T, E, F> Result<T, E>.mapErr(fn: (E) -> F): Result<T, F> = when (this) {
    is Err -> Err(fn(error))
    is Ok -> Ok(value)
}

inline fun <T, E> Result<T, E>.onErr(fn: (E) -> Unit): Result<T, E> = when (this) {
    is Ok -> this
    is Err -> {
        fn(error)
        this
    }
}

inline fun <T, E> Result<T, E>.extract(fn: (E) -> Unit): T =
    onErr { fn(it) }
        .getOrThrow()

fun <T, E> Result<T, E>.getOrThrow(): T = when (this) {
    is Err -> throw RuntimeException("error extracting value: $error")
    is Ok -> value
}

fun <T, E> Result<T, E>.errOrThrow(): E = when (this) {
    is Err -> error
    is Ok -> throw RuntimeException("expected error, got \"$value\"")
}

fun <T : Any, E : Any> T?.toResult(err: E): Result<T, E> = when (this) {
    null -> Err(err)
    else -> Ok(this)
}

fun <E : Any> Boolean.falseErr(err: E): Result<Unit, E> =
    if (this) {
        Ok(Unit)
    } else {
        Err(err)
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
