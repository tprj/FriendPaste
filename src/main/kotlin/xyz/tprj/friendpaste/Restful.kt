package xyz.tprj.friendpaste

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import xyz.tprj.friendpaste.dataset.CreateDataSet
import xyz.tprj.friendpaste.dataset.GetDataSet
import xyz.tprj.friendpaste.dataset.GetResponseDataSet
import xyz.tprj.friendpaste.dataset.PasteDataSet
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
    ): ResponseEntity<PasteDataSet> {
        val id = UUID.randomUUID().toString()
        val now = Date.valueOf(LocalDate.now())
        val deleteAt = Date.valueOf(LocalDate.now().plus(request.deleteAfter.toLong(), ChronoUnit.DAYS))

        PasteDataSet(
            id,
            request.fileName ?: Util.getFilenameRandom(),
            request.deletePass ?: "",
            now,
            deleteAt,
            request.content,
            servlet.remoteAddr
        ).also {
            DBManager.con.add(it)
            return ResponseEntity(it, HttpStatus.CREATED)
        }
    }

    @GetMapping(path = ["/paste/api/v1"])
    fun get(
        @RequestBody(required = true) request: GetDataSet,
        servlet: HttpServletRequest
    ): ResponseEntity<GetResponseDataSet> {
        val dataSet: PasteDataSet = DBManager.con.get(PasteDataSet::class.java) {
            where("id").equal(request.id)
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
    }
}