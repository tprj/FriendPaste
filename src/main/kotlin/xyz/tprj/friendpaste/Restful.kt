package xyz.tprj.friendpaste

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import xyz.tprj.friendpaste.dataset.*
import java.sql.Date
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*
import javax.servlet.http.HttpServletRequest


@RestController
class Restful {

    @PostMapping(path = ["/paste/api/v1"])
    fun create(
        @RequestBody(required = true) request: CreateDataSet,
        servlet: HttpServletRequest
    ): ResponseEntity<GetResponseDataSet> {
        val id = UUID.randomUUID().toString()
        val now = Date.valueOf(LocalDate.now())
        val deleteAt = Date.valueOf(LocalDate.now().plus(request.delete_after?.toLong() ?: 10, ChronoUnit.DAYS))

        PasteDataSet(
            id,
            request.filename ?: Util.getFilenameRandom(),
            request.delete_pass?.let { Util.encodePassword(it) } ?: "",
            now,
            deleteAt,
            request.content ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST),
            servlet.getHeader("CF-Connecting-IP")?:servlet.remoteAddr
        ).also {
            DBManager.con.put(it).send(false)
            return ResponseEntity(GetResponseDataSet(it.id, it.filename, it.createdAt.toString(), it.deleteAt.toString(), it.content,it.ip == (servlet.getHeader("CF-Connecting-IP") ?: servlet.remoteAddr)
            ), HttpStatus.CREATED)
        }
    }

    /*
    @GetMapping(path = ["/paste/api/v1"])
    fun get(
        @RequestParam("id") id: String,
        servlet: HttpServletRequest
    ): ResponseEntity<GetResponseDataSet> {
        val dataSet: PasteDataSet = DBManager.con.get(PasteDataSet::class.java) {
            where("id").equal(id)
        }.getOrNull(0) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

        return ResponseEntity(
            GetResponseDataSet(
                dataSet.id,
                dataSet.filename,
                dataSet.createdAt.toString(),
                dataSet.deleteAt.toString(),
                dataSet.content,
                dataSet.ip == servlet.remoteAddr
            ),
            HttpStatus.OK
        )
    }*/

    @DeleteMapping(path = ["/paste/api/v1"])
    fun get(@RequestBody(required = true) request: DeleteDataSet, servlet: HttpServletRequest) {
        val dataSet: PasteDataSet = DBManager.con.get(PasteDataSet::class.java) {
            where("id").equal(request.id)
        }.getOrNull(0) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        if (dataSet.password == "") {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        }
        if (Util.matchPassword(request.password, dataSet.password)) {
            DBManager.con.delete("paste") {
                where("id").equal(request.id)
            }.send()
            throw ResponseStatusException(HttpStatus.OK)
        } else {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        }
    }
}