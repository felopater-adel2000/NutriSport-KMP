package org.kmp.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform