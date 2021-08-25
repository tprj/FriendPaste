package xyz.tprj.friendpaste

object Util {

    private val FILENAMES = listOf("moru.yml", "moru.txt", "moru.xml", "moru.html", "moru.java", "moru.kt", "moru.c", "moru.cpp", "moru.cs", "moru.cc", "moru.php", "moru.go", "moru.css", "moru.sass", "moru.ts", "moru.js", "moru.bat", "moru.sh", "moru.md", "moru.kts", "moru.moru")

    fun getFilenameRandom(): String {
        return FILENAMES.random()
    }

}