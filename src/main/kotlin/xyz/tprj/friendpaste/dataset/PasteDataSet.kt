package xyz.tprj.friendpaste.dataset

import dev.moru3.compsql.annotation.Column
import dev.moru3.compsql.annotation.TableName
import java.sql.Date

@TableName("paste")
class PasteDataSet() {

    @Column("id")
    lateinit var id: String

    @Column("filename")
    lateinit var filename: String

    @Column("password")
    lateinit var password: String

    @Column("created_at")
    lateinit var createdAt: Date

    @Column("delete_at")
    lateinit var deleteAt: Date

    @Column("content")
    lateinit var content: String

    @Column("ip")
    lateinit var ip: String

    constructor(
        id: String,
        filename: String,
        password: String,
        createdAt: Date,
        deleteAt: Date,
        content: String,
        ip: String
    ) : this() {
        this.id = id
        this.filename = filename
        this.password = password
        this.createdAt = createdAt
        this.deleteAt = deleteAt
        this.content = content
        this.ip = ip
    }
}