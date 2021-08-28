package xyz.tprj.friendpaste

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.server.ResponseStatusException
import xyz.tprj.friendpaste.dataset.PasteDataSet

@Controller
class WebController {

    @RequestMapping(path = ["/paste"], method = [RequestMethod.GET])
    fun paste(model: Model, @RequestParam(required = false) id: String? = null): String {
        if (id == null) {
            return "index"
        } else {
            val dataSet: PasteDataSet = DBManager.con.get(PasteDataSet::class.java) {
                where("id").equal(id)
            }.getOrNull(0)?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
            model.addAttribute("filename", dataSet.filename)
            model.addAttribute("content", dataSet.content.replace("'", "\\'").replace("\n", "\\n").replace("\r", "\\r"))
            return "paste"
        }
    }

}