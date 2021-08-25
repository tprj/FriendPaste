package xyz.tprj.friendpaste

import dev.moru3.compsql.connection.MariaDBConnection

object DBManager {
    val con = MariaDBConnection(HOST, DATABASE, USERNAME, PASSWORD, mapOf())
}