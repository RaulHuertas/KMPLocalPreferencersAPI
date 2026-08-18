package com.rhuertas.kmplocalpreferencesapi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface OptionsStore {
    val options: Flow<Options>
    suspend fun saveOptions(options: Options)
}

class InMemoryOptionsStore(
    initialOptions: Options = DefaultOptions
) : OptionsStore {
    private val state = MutableStateFlow(initialOptions)

    override val options: Flow<Options> = state.asStateFlow()

    override suspend fun saveOptions(options: Options) {
        state.value = options
    }
}

fun saveOptionsToStoreAsync(
    options: Options,
    store: OptionsStore,
    coroutineScope: CoroutineScope,
) {
    //store.saveOptions(options)
    coroutineScope.launch { store.saveOptions(options) }
}