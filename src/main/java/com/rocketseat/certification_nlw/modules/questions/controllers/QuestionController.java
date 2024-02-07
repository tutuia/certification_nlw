package com.rocketseat.certification_nlw.modules.questions.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rocketseat.certification_nlw.modules.questions.dto.AlternativesResultDTO;
import com.rocketseat.certification_nlw.modules.questions.dto.QuestionResultDTO;
import com.rocketseat.certification_nlw.modules.questions.entities.AlternativesEntity;
import com.rocketseat.certification_nlw.modules.questions.entities.QuestionEntity;
import com.rocketseat.certification_nlw.modules.questions.repositories.QuestionRepository;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/technology/{technology}")
    public List<QuestionResultDTO> findByTechnology(@PathVariable String technology){
        var result = this.questionRepository.findByTechnology(technology);
        return result.stream().map(question -> mapQuestionToDTO(question))
            .collect(Collectors.toList());        
    }

    static QuestionResultDTO mapQuestionToDTO(QuestionEntity question){
        List<AlternativesResultDTO> alternativesResultDTO = 
            question.getAlternatives().stream()
            .map(alternative -> mapAlternativesResultDTO(alternative))
            .collect(Collectors.toList());

        return QuestionResultDTO.builder()
            .description(question.getDescription())
            .technology(question.getTechnology())
            .alternatives(alternativesResultDTO)
            .id(question.getId()).build();
    }

    static AlternativesResultDTO mapAlternativesResultDTO(AlternativesEntity alternative){
        return AlternativesResultDTO.builder()
            .id(alternative.getId())
            .description(alternative.getDescription()).build(); 
    }
}
