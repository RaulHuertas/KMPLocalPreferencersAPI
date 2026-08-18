package com.rhuertas.kmplocalpreferencesapi

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OptionsDataStore(
    private val dataStore: DataStore<Preferences>
) : OptionsStore {
    override val options: Flow<Options> = dataStore.data.map(::toOptions)

    override suspend fun saveOptions(options: Options) {
        dataStore.edit { prefs ->
            prefs[OPTION_COLOR_KEY] = options.color.name
            prefs[OPTION_MODE_KEY] = options.mode
            prefs[OPTION_DARK_MODE_KEY] = options.dark_mode
        }
    }
}

internal val OPTION_COLOR_KEY = stringPreferencesKey("option_color")
internal val OPTION_MODE_KEY = intPreferencesKey("option_mode")
internal val OPTION_DARK_MODE_KEY = booleanPreferencesKey("option_dark_mode")

internal fun toOptions(preferences: Preferences): Options =
    Options(
        color = preferences[OPTION_COLOR_KEY]?.toOptionColor() ?: DefaultOptions.color,
        mode = preferences[OPTION_MODE_KEY] ?: DefaultOptions.mode,
        dark_mode = preferences[OPTION_DARK_MODE_KEY] ?: DefaultOptions.dark_mode
    )

internal fun String.toOptionColor(): OptionColor = when (this) {
    OptionColor.WHITE.name -> OptionColor.WHITE
    OptionColor.GREEN.name -> OptionColor.GREEN
    OptionColor.RED.name -> OptionColor.RED
    else -> DefaultOptions.color
}
