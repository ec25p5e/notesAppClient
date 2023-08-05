package com.ec25p5e.notesapp.core.data.local.serializer

import androidx.datastore.core.Serializer
import com.ec25p5e.notesapp.core.data.util.CryptoManager
import com.ec25p5e.notesapp.feature_settings.domain.models.AppSettings
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

/* class AppSettingsSerializer(
    private val cryptoManager: CryptoManager
) : Serializer<AppSettings> {

    override val defaultValue: AppSettings
        get() = AppSettings()

    override suspend fun readFrom(input: InputStream): AppSettings {
        val decryptedBytes = cryptoManager.decrypt(input)
        return try {
            Json.decodeFromString(
                deserializer = AppSettings.serializer(),
                string = decryptedBytes.decodeToString()
            )
        } catch(e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: AppSettings, output: OutputStream) {
        cryptoManager.encrypt(
            bytes = Json.encodeToString(
                serializer = AppSettings.serializer(),
                value = t
            ).encodeToByteArray(),
            outputStream = output
        )
    }
} */

@Suppress("BlockingMethodInNonBlockingContext")
object AppSettingsSerializer : Serializer<AppSettings> {

    override val defaultValue: AppSettings
        get() = AppSettings()

    override suspend fun readFrom(input: InputStream): AppSettings {
        return try {
            Json.decodeFromString(
                deserializer = AppSettings.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: AppSettings, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = AppSettings.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}