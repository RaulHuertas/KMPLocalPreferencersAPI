package com.rhuertas.kmplocalpreferencesapi

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform