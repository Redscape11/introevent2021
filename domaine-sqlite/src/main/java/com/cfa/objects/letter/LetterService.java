package com.cfa.objects.letter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class LetterService {

    @Autowired
    LetterRepository letterRepository;

    public List<Letter> getAllLetters() {
        return letterRepository.findAll();
    }

    public Letter saveLetter(String message, Date creationDate, Date treatmentDate) {
        Letter letter = new Letter();
        letter.setMessage(message);
        letter.setCreationDate(creationDate);
        letter.setTreatmentDate(treatmentDate);
        letterRepository.save(letter);
        return letter;
    }

    public Letter getLetter(int id) {
        try {
            return letterRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            return null;
        }

    }
}
