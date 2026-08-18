package com.rhuertas.kmplocalpreferencesapi

import androidx.datastore.preferences.core.preferencesOf
import kotlin.test.Test
import kotlin.test.assertEquals

class OptionsDataStoreTest {
    @Test
    fun usesDefaultValuesWhenPreferencesAreMissing() {
        val result = toOptions(preferencesOf())

        assertEquals(DefaultOptions, result)
    }

    @Test
    fun mapsPreferenceValuesToOptions() {
        val prefs = preferencesOf(
            OPTION_COLOR_KEY to OptionColor.RED.name,
            OPTION_MODE_KEY to 5,
            OPTION_DARK_MODE_KEY to true
        )

        val result = toOptions(prefs)

        assertEquals(Options(color = OptionColor.RED, mode = 5, dark_mode = true), result)
    }
}
