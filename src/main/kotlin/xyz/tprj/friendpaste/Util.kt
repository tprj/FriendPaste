package xyz.tprj.friendpaste

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder


object Util {

    private val FILENAMES = listOf("moru.yml", "moru.txt", "moru.xml", "moru.html", "moru.java", "moru.kt", "moru.c", "moru.cpp", "moru.cs", "moru.cc", "moru.php", "moru.go", "moru.css", "moru.sass", "moru.ts", "moru.js", "moru.bat", "moru.sh", "moru.md", "moru.kts", "moru.moru")

    fun getFilenameRandom(): String {
        return FILENAMES.random()
    }

    private val ENCODER = BCryptPasswordEncoder()

    fun encodePassword(rawPassword: String): String {
        return ENCODER.encode(rawPassword)
    }

    fun matchPassword( rawPassword: String, encodedPassword: String): Boolean {
        return ENCODER.matches(rawPassword, encodedPassword)
    }

}