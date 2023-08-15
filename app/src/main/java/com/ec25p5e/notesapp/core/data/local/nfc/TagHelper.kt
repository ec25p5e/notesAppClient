package com.ec25p5e.notesapp.core.data.local.nfc

import android.content.res.Resources
import android.nfc.FormatException
import android.nfc.Tag
import android.nfc.TagLostException
import android.nfc.tech.MifareClassic
import android.nfc.tech.MifareUltralight
import android.nfc.tech.NfcA
import android.nfc.tech.TagTechnology
import android.util.Log
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.domain.models.WriteResultData
import java.io.IOException
import java.lang.Math.ceil

private const val TAG = "TagHelper"
private const val sectorRef = 1
private const val firstBlockNum: Byte = 8
private const val lastBlockNum: Byte = 11

@ExperimentalUnsignedTypes
val appCookie = hexToBytes("1337b347").toList()

@ExperimentalUnsignedTypes
private val factoryKey = hexToBytes("FFFFFFFFFFFF")

fun tagIdAsString(tag: TagTechnology) = tagIdAsString(tag.tag)

fun tagIdAsString(tag: Tag): String = tag.id.toHex(":")

fun ByteArray.toHex(separator: String = " "): String =
    joinToString(separator) { eachByte -> "%02x".format(eachByte).uppercase() }

fun describeTag(tag: Tag): String {
    return try {
        describeTagType(getTagTechnology(tag))
    } catch (ex: Exception) {
        Log.w(TAG, "describeTagType failed with $ex")
        Resources.getSystem().getString(R.string.identify_unsupported_type)
    }
}

fun getTagTechnology(tag: Tag): TagTechnology {
    return when {
        tag.techList.contains(MifareClassic::class.java.name) ->
            MifareClassic.get(tag)
        tag.techList.contains(MifareUltralight::class.java.name) ->
            MifareUltralight.get(tag)
        tag.techList.contains(NfcA::class.java.name) ->
            NfcA.get(tag)
        else ->
            throw FormatException("Can only handle MifareClassic, MifareUltralight and NfcA")
    }
}

fun describeTagType(tag: TagTechnology): String {
    return when (tag) {
        is MifareClassic ->
            when (tag.type) {
                MifareClassic.TYPE_CLASSIC -> "Mifare Classic"
                MifareClassic.TYPE_PLUS -> "Mifare Plus"
                MifareClassic.TYPE_PRO -> "Mifare Pro"
                else -> "Mifare Classic (${
                    Resources.getSystem().getString(R.string.identify_unknown_type)
                })"
            }
        is MifareUltralight ->
            when (tag.type) {
                MifareUltralight.TYPE_ULTRALIGHT -> "Mifare Ultralight"
                MifareUltralight.TYPE_ULTRALIGHT_C -> "Mifare Ultralight C"
                else -> "Mifare Ultralight (${
                    Resources.getSystem().getString(R.string.identify_unknown_type)
                })"
            }
        is NfcA ->
            "NfcA (SAK: ${tag.sak.toString().padStart(2, '0')}, ATQA: ${tag.atqa.toHex()})"
        else ->
            Resources.getSystem().getString(R.string.identify_unsupported_type)
    }
}

fun connectTo(tag: Tag): TagTechnology? {
    return getTagTechnology(tag).apply { connect() }
}

@ExperimentalUnsignedTypes
fun readFromTag(tag: Tag): UByteArray {
    val id = tagIdAsString(tag)
    return try {
        Log.i(TAG, "Tag $id techList: ${techListOf(tag).joinToString(", ")}")
        val result = getTagTechnology(tag).use { tech ->
            when (tech) {
                is MifareClassic -> readFromTag(tech)
                is MifareUltralight -> readFromTag(tech)
                is NfcA -> readFromTag(tech)
                else -> ubyteArrayOf()
            }
        }
        dropTrailingZeros(result)
    } catch (ex: Exception) {
        Log.e("$TAG.readFromTag", ex.toString())
        ubyteArrayOf()
    }
}

@ExperimentalUnsignedTypes
fun dropTrailingZeros(bytes: UByteArray): UByteArray {
    if (bytes.isEmpty()) return bytes

    val lastNonZeroIndex = bytes.indexOfLast { value -> value > 0u }
    if (lastNonZeroIndex == 0) return ubyteArrayOf()

    return bytes.sliceArray(0..lastNonZeroIndex)
}

@ExperimentalUnsignedTypes
fun readFromTag(tag: MifareClassic): UByteArray {
    if (!tag.isConnected) tag.connect()
    var result = ubyteArrayOf()

    val key = factoryKey.asByteArray()
    if (tag.authenticateSectorWithKeyA(sectorRef, key)) {
        val blockIndex = tag.sectorToBlock(sectorRef)
        val block = tag.readBlock(blockIndex).toUByteArray()
        tag.close()

        Log.w(TAG, "Bytes in sector: ${byteArrayToHex(block).joinToString(" ")}")

        if (block.take(appCookie.size) == appCookie) {
            Log.i(TAG, "This is a System MifareClassic tag")
        }

        result = block
    } else {
        tag.close()
        Log.e(TAG, "Authentication of sector $appCookie failed!")
    }

    return result
}

@ExperimentalUnsignedTypes
fun readFromTag(tag: MifareUltralight): UByteArray {
    if (!tag.isConnected) tag.connect()

    val tagType = when (tag.type) {
        MifareUltralight.TYPE_ULTRALIGHT -> "ULTRALIGHT"
        MifareUltralight.TYPE_ULTRALIGHT_C -> "ULTRALIGHT_C"
        else -> "ULTRALIGHT (UNKNOWN)"
    }

    val block = tag.readPages(8).toUByteArray()
    tag.close()

    if (block.take(appCookie.size) == appCookie) {
        Log.i(TAG, "This is a System MIFARE $tagType tag")
    }

    Log.i(TAG, "Bytes in sector: ${byteArrayToHex(block).joinToString(" ")}")

    return block
}

@ExperimentalUnsignedTypes
fun readFromTag(tag: NfcA): UByteArray {
    if (!tag.isConnected) tag.connect()

    val block = tag.transceive(
        byteArrayOf(
            0x3A.toByte(),  // FAST_READ
            firstBlockNum,
            lastBlockNum,
        ),
    ).toUByteArray()
    tag.close()

    if (block.take(appCookie.size) == appCookie) {
        Log.i(TAG, "This is a System NFCA tag")
    }

    return block
}

@ExperimentalUnsignedTypes
fun writeSystem(tag: TagTechnology, data: UByteArray): WriteResultData {
    var description: String = ""
    val result: WriteResult = try {
        description = describeTagType(tag)
        when (tag) {
            is MifareClassic -> writeTag(tag, data)
            is MifareUltralight -> writeTag(tag, data)
            is NfcA -> writeTag(tag, data)
            else -> WriteResult.UnsupportedFormat
        }
    } catch (ex: TagLostException) {
        WriteResult.TagUnavailable
    } catch (ex: FormatException) {
        WriteResult.UnsupportedFormat
    } catch (ex: Exception) {
        WriteResult.UnknownError
    }

    return WriteResultData(description, result)
}

@ExperimentalUnsignedTypes
fun writeTag(tag: MifareClassic, data: UByteArray): WriteResult {
    val result: WriteResult
    try {
        if (!tag.isConnected) tag.connect()
    } catch (ex: IOException) {
        return WriteResult.TagUnavailable
    }

    val key = factoryKey.asByteArray() // TODO allow configuration
    result = if (tag.authenticateSectorWithKeyB(sectorRef, key)) {
        val blockIndex = tag.sectorToBlock(sectorRef)
        val block = toFixedLengthBuffer(data, MifareClassic.BLOCK_SIZE)
        tag.writeBlock(blockIndex, block)

        Log.i(
            TAG, "Wrote ${byteArrayToHex(data)} to tag ${
                tagIdAsString(
                    tag.tag
                )
            }"
        )
        WriteResult.Success
    } else {
        WriteResult.AuthenticationFailure
    }

    tag.close()

    return result
}

@ExperimentalUnsignedTypes
fun writeTag(tag: MifareUltralight, data: UByteArray): WriteResult {
    try {
        if (!tag.isConnected) tag.connect()
    } catch (ex: IOException) {
        return WriteResult.TagUnavailable
    }

    val len = data.size
    Log.i(TAG, "data byte size $len")

    val pagesNeeded = ceil(data.size.toDouble() / MifareUltralight.PAGE_SIZE).toInt()

    val block = toFixedLengthBuffer(data, MifareUltralight.PAGE_SIZE * pagesNeeded)
    var current = 0

    for (index in 0 until pagesNeeded) {
        val next = current + MifareUltralight.PAGE_SIZE
        val part = block.slice(current until next).toByteArray()
        tag.writePage(8 + index, part)
        Log.i(
            TAG,
            "Wrote ${byteArrayToHex(part.toUByteArray())} to tag ${tagIdAsString(tag.tag)}"
        )
        current = next
    }

    return WriteResult.Success
}


@ExperimentalUnsignedTypes
fun writeTag(tag: NfcA, data: UByteArray): WriteResult {
    try {
        if (!tag.isConnected) tag.connect()
    } catch (ex: IOException) {
        return WriteResult.TagUnavailable
    }

    return when (tag.sak.toInt()) {
        0 ->
            writeMifareUltralight(tag, data)
        8, 9, 10, 11, 18 ->
            WriteResult.UnsupportedFormat
        else ->
            WriteResult.UnsupportedFormat
    }
}

fun writeMifareUltralight(tag: NfcA, data: UByteArray): WriteResult {
    val len = data.size
    var pageNum = firstBlockNum
    val pagesize = MifareUltralight.PAGE_SIZE
    val pagesNeeded = ceil(data.size.toDouble() / pagesize).toInt()

    Log.i(TAG, "data byte size $len")

    var current = 0
    val block = toFixedLengthBuffer(data, pagesize * pagesNeeded)
    for (index in 0 until pagesNeeded) {
        val next = current + pagesize
        val data = byteArrayOf(0xA2.toByte(), pageNum) + block.slice(current until next)
            .toByteArray()
        Log.i(TAG, "Will transceive(${data.toHex()})")
        val result = tag.transceive(data)
        current = next
        Log.i(TAG, "transceive(${data.toHex()}) returned ${result.toHex()}")
        if (result.size != 1 || result[0] != 0x0A.toByte()) {
            Log.e(TAG, "transceive did not return `ACK (0A)`. Got `${result.toHex()}` instead.")
            tag.close()
            return WriteResult.NfcATransceiveNotOk(result)
        }
        pageNum++
    }

    tag.close()
    return WriteResult.Success
}

@ExperimentalUnsignedTypes
fun toFixedLengthBuffer(bytes: UByteArray, size: Int): ByteArray {
    val block = UByteArray(size) { 0u }
    bytes.forEachIndexed { index, value -> block[index] = value }
    return block.toByteArray()
}

fun techListOf(tag: TagTechnology?) = techListOf(tag?.tag)

fun techListOf(tag: Tag?): List<String> {
    return tag?.techList?.map { str -> str.drop(str.lastIndexOf('.') + 1) } ?: listOf()
}