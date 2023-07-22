package tabletop.io

import com.akuleshov7.ktoml.Toml
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import tabletop.result.Err
import tabletop.result.Ok
import tabletop.result.Result

typealias TomlResult<T> = Result<T, TomlError>

sealed interface TomlError {
    data class Exception(val exception: kotlin.Exception) : TomlError
}

inline fun <reified T> Toml.tryEncodeString(data: T): TomlResult<String> =
    try {
        Ok(encodeToString(data))
    } catch (e: SerializationException) {
        Err(TomlError.Exception(e))
    }

inline fun <reified T> Toml.tryDecodeString(content: String): TomlResult<T> =
    try {
        Ok(decodeFromString(content))
    } catch (e: SerializationException) {
        Err(TomlError.Exception(e))
    }
