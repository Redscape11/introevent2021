package com.cfa.letter;

import com.cfa.objects.letter.Letter;
import org.springframework.batch.item.ItemProcessor;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LetterProcessor implements ItemProcessor<String, Letter> {

    @Override
    public Letter process(String message) throws InterruptedException {
        log.info("==========>DEBUT Traitement<========");
        Thread.sleep(5000);
        Letter letter = new Letter();
        letter.setMessage(message);
        letter.setTreatmentDate(new Date());
        letter.setCreationDate(new Date());
        log.info("==========>FIN Traitement<========");
        return letter;
    }
}
