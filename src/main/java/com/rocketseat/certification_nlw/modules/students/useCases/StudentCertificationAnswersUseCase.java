package com.rocketseat.certification_nlw.modules.students.useCases;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocketseat.certification_nlw.modules.questions.entities.QuestionEntity;
import com.rocketseat.certification_nlw.modules.questions.repositories.QuestionRepository;
import com.rocketseat.certification_nlw.modules.students.dto.StudentCertificationAnswerDTO;
import com.rocketseat.certification_nlw.modules.students.entities.AnswersCertificatioEntity;
import com.rocketseat.certification_nlw.modules.students.entities.CertificationStudentEntity;
import com.rocketseat.certification_nlw.modules.students.entities.StudentEntity;
import com.rocketseat.certification_nlw.modules.students.repositories.StudentRepository;

@Service
public class StudentCertificationAnswersUseCase {
    
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public StudentCertificationAnswerDTO execute(StudentCertificationAnswerDTO dto){
        
        List<QuestionEntity> questionsEntity = questionRepository.findByTechnology(dto.getTechnology());

        dto.getQuestionAnswers()
            .stream().forEach(questionAnswer -> {
                var question = questionsEntity.stream()
                    .filter(q -> q.getId().equals(questionAnswer.getQuestionID())).findFirst().get();

                var findCorrectAlternative = question.getAlternatives().stream()
                    .filter(alternative -> alternative.isCorrect()).findFirst().get();

                if(findCorrectAlternative.getId().equals(questionAnswer.getAlternativeID())){
                    questionAnswer.setCorrect(true);
                }else{
                    questionAnswer.setCorrect(false);
                }
            });
        //buscar as alternativas corretas das perguntas
            // - correta ou incorreta
        //salvar as informacoes da certificacao

        var student = studentRepository.findByEmail(dto.getEmail());
        UUID studentID;
        if(student.isEmpty()){
            var studentCreated = StudentEntity.builder().email(dto.getEmail()).build();
            studentRepository.save(studentCreated);
            studentID = studentCreated.getId();
        }else{
            studentID = student.get().getId();
        }

        List<AnswersCertificatioEntity> answersCertifications = new ArrayList<>();

        CertificationStudentEntity certificationStudentEntity = 
            CertificationStudentEntity.builder()
            .technology(dto.getTechnology())
            .studentID(studentID)
        //    .answearsCertificatioEntity(answersCertifications)
            .build();
        return dto;
    }
}
