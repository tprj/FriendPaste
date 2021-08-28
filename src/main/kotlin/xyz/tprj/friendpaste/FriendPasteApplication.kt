package xyz.tprj.friendpaste

import dev.moru3.compsql.datatype.DataType
import dev.moru3.compsql.datatype.types.text.VARCHAR
import org.apache.commons.cli.*
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import xyz.tprj.friendpaste.DBManager.con
import java.sql.Date
import java.time.LocalDate

@SpringBootApplication
class FriendPasteApplication {

}

fun main(args: Array<String>) {
    var host = ""
    var database = ""
    var username = ""
    var password = ""

    val option = Options().apply {
        addOption(Option.builder("host").hasArg().required().desc("HostName of database server").build())
        addOption(Option.builder("db").hasArg().required().desc("Database name").build())
        addOption(Option.builder("user").hasArg().required().desc("Username").build())
        addOption(Option.builder("pass").hasArg().required().desc("Password").build())
    }

    val parser: CommandLineParser = DefaultParser()

    val cmd: CommandLine = try {
        parser.parse(option, args)
    } catch (ex: ParseException) {
        HelpFormatter().printHelp("[options...]", option)
        return
    }

    DBManager.init(cmd.getOptionValue("host"), cmd.getOptionValue("db"), cmd.getOptionValue("user"), cmd.getOptionValue("pass"))


    con.table("paste") {
        column("id", VARCHAR(36))
        column("filename", VARCHAR(32))
        column("password", VARCHAR(127))
        column("created_at", DataType.DATE)
        column("delete_at", DataType.DATE)
        column("content", DataType.TEXT)
        column("ip", VARCHAR(127))
    }.send(false)

    con.delete("paste") {
        where("delete_at").lessOrEquals(Date.valueOf(LocalDate.now()))
    }.send()

    runApplication<FriendPasteApplication>(*args)
}
