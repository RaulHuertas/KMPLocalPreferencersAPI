package com.rhuertas.kmplocalpreferencesapi

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return sayHello(platform.name)
    }
}