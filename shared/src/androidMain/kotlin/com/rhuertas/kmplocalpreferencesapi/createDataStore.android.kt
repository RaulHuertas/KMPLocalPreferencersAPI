package com.rhuertas.kmplocalpreferencesapi
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.okio.OkioStorage
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferencesSerializer
import okio.FileSystem
import okio.Path.Companion.toPath

private const val DATA_STORE_FILE_NAME = "kmplocalpreferencesapi.preferences_pb"

fun createDataStore(context: Context): DataStore<Preferences> = createDataStore(
    storage = OkioStorage(
        fileSystem = FileSystem.SYSTEM,
        serializer = PreferencesSerializer,
        producePath = { context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath.toPath() }
    )
)