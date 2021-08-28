package xyz.tprj.friendpaste

import dev.moru3.compsql.connection.MariaDBConnection

object DBManager {

    lateinit var con : MariaDBConnection

    fun init(host: String, database: String, username: String, password: String) {
        con = MariaDBConnection(host, database, username, password, mapOf())
    }
}