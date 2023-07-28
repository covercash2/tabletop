/* (C)2023 */
package tabletop.io

import kotlinx.serialization.SerializationException
import kotlinx.serialization.StringFormat
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import tabletop.result.Err
import tabletop.result.Ok
import tabletop.result.Result

typealias SerializationResult<T> = Result<T, SerializationError>
sealed interface SerializationError {
    data class Exception(val exception: kotlin.Exception) : SerializationError
}

inline fun <reified T> StringFormat.encode(data: T): SerializationResult<String> =
    try {
        Ok(encodeToString(data))
    } catch (e: SerializationException) {
        Err(SerializationError.Exception(e))
    }

inline fun <reified T> StringFormat.decode(string: String): SerializationResult<T> =
    try {
        Ok(decodeFromString(string))
    } catch (e: SerializationException) {
        Err(SerializationError.Exception(e))
    }
