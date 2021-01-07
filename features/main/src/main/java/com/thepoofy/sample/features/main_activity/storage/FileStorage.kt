package com.thepoofy.sample.features.main_activity.storage

import android.content.Context
import com.squareup.moshi.Moshi
import okio.Buffer
import org.json.JSONException
import timber.log.Timber
import java.io.EOFException
import java.io.FileNotFoundException
import java.lang.reflect.Type
import javax.inject.Inject

class FileStorage @Inject constructor(
    private val context: Context,
    private val moshi: Moshi) {

    fun <T> write(fileName: String, value: T, typeReference: Type) {
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            val sink = Buffer()
            moshi.adapter<T>(typeReference).toJson(sink, value)
            sink.buffer.copyTo(it)
        }
    }

    fun <T> read(key: String, typeReference: Type): T? {
        return try {
            val adapter = moshi.adapter<T>(typeReference)
            val sink = Buffer()
            sink.buffer.readFrom(context.openFileInput(key))
            adapter.fromJson(sink.buffer)
        } catch (e: FileNotFoundException) {
            Timber.v(e, "FileNotFoundException thrown for $key.")
            null
        } catch (e: JSONException) {
            Timber.v(e, "JSONException thrown for $key.")
            null
        } catch (e: EOFException) {
            Timber.v(e, "EOFException thrown for $key.")
            null
        }
    }
}