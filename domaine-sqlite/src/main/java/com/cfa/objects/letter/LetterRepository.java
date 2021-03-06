package com.cfa.objects.letter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Integer> {
}
