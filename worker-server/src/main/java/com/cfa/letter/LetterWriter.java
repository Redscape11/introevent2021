package com.cfa.letter;

import com.cfa.objects.letter.Letter;
import com.hazelcast.internal.json.JsonObject;
import org.springframework.batch.item.ItemWriter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

public class LetterWriter implements ItemWriter<Letter> {
    @Override
    public void write(List<? extends Letter> list) throws Exception {
        System.out.println(list);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        for (Letter letter : list) {
            JsonObject json = new JsonObject();
            json.add("message", letter.getMessage());
            json.add("creationDate", letter.getCreationDate().toString());
            json.add("treatmentDate", letter.getTreatmentDate().toString());

            HttpEntity<String> entity = new HttpEntity<>(json.toString(), headers);
            restTemplate.postForObject("http://localhost:9623/letter/new", entity, String.class);
        }
    }
}