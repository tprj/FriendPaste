package xyz.tprj.friendpaste

import dev.moru3.compsql.datatype.DataType
import dev.moru3.compsql.datatype.types.text.VARCHAR
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import xyz.tprj.friendpaste.DBManager.con

@SpringBootApplication
class FriendPasteApplication

const val PASSWORD = ""
const val USERNAME = "root"
const val HOST = "103.29.71.17"
const val DATABASE = ""

fun main(args: Array<String>) {

    con.table("paste") {
        column("id", VARCHAR(36))
        column("filename", VARCHAR(32))
        column("password", VARCHAR(127))
        column("created_at", DataType.DATE)
        column("delete_at", DataType.DATE)
        column("content", DataType.LONGTEXT)
        column("ip", VARCHAR(127))
    }.send(false)

    runApplication<FriendPasteApplication>(*args)
}
