package com.cfa.objects.letter;
import com.hazelcast.internal.json.JsonObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value="/letter", produces = "application/json")
public class LetterApiController {

    @Autowired
    LetterService letterService;

    @GetMapping("/all")
    public List<Letter> getAll() {
        return letterService.getAllLetters();
    }

    @PostMapping("/new")
    public Letter createLetter(@RequestBody final String data) throws JSONException {
        JSONObject json = new JSONObject(data);
        String message = json.getString("message");
        String creationDate = json.getString("creationDate");
        String treatmentDate = json.getString("treatmentDate");
        return letterService.saveLetter(message, new Date(), new Date());
    }

    @GetMapping("/{id}")
    public Letter getLetterById(@PathVariable final Integer id) {
        return letterService.getLetter(id);
    }
}
