package com.rocketseat.certification_nlw.modules.students.useCases;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocketseat.certification_nlw.modules.questions.entities.QuestionEntity;
import com.rocketseat.certification_nlw.modules.questions.repositories.QuestionRepository;
import com.rocketseat.certification_nlw.modules.students.dto.StudentCertificationAnswerDTO;
import com.rocketseat.certification_nlw.modules.students.dto.VerifyHasCerticicationDTO;
import com.rocketseat.certification_nlw.modules.students.entities.AnswersCertificatioEntity;
import com.rocketseat.certification_nlw.modules.students.entities.CertificationStudentEntity;
import com.rocketseat.certification_nlw.modules.students.entities.StudentEntity;
import com.rocketseat.certification_nlw.modules.students.repositories.CertificationStudentRepository;
import com.rocketseat.certification_nlw.modules.students.repositories.StudentRepository;

@Service
public class StudentCertificationAnswersUseCase {
    
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CertificationStudentRepository certificationStudentRepository;

    @Autowired
    private VerifyIfHasCertificatioUseCase verifyIfHasCertificatioUseCase;

    public CertificationStudentEntity execute(StudentCertificationAnswerDTO dto) throws Exception{

        var hasCertification = this.verifyIfHasCertificatioUseCase.execute(new VerifyHasCerticicationDTO(dto.getEmail(),dto.getTechnology()));

        if(hasCertification){
            throw new Exception("voce ja tirou sua certificacao");
        }
        
        List<QuestionEntity> questionsEntity = questionRepository.findByTechnology(dto.getTechnology());
        List<AnswersCertificatioEntity> answersCertifications = new ArrayList<>();

        AtomicInteger correctAnswers = new AtomicInteger(0);

        dto.getQuestionAnswers()
            .stream().forEach(questionAnswer -> {
                var question = questionsEntity.stream()
                    .filter(q -> q.getId().equals(questionAnswer.getQuestionID())).findFirst().get();

                var findCorrectAlternative = question.getAlternatives().stream()
                    .filter(alternative -> alternative.isCorrect()).findFirst().get();

                if(findCorrectAlternative.getId().equals(questionAnswer.getAlternativeID())){
                    questionAnswer.setCorrect(true);
                    correctAnswers.incrementAndGet();
                }else{
                    questionAnswer.setCorrect(false);
                }

                var answersCertificationEntity = AnswersCertificatioEntity.builder()
                .answearID(questionAnswer.getAlternativeID())
                .questionID(questionAnswer.getQuestionID())
                .isCorrect(questionAnswer.isCorrect()).build();
                
                answersCertifications.add(answersCertificationEntity);
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

        CertificationStudentEntity certificationStudentEntity = 
            CertificationStudentEntity.builder()
            .technology(dto.getTechnology())
            .studentID(studentID)
            .grade(correctAnswers.get())
            .build();
     
        var certificationStudentCreated = certificationStudentRepository.save(certificationStudentEntity);
       
        answersCertifications.stream().forEach(answerCertification ->{
            answerCertification.setCertificationID(certificationStudentEntity.getId());
            answerCertification.setCertificationStudentEntity(certificationStudentEntity);
        });

        certificationStudentEntity.setAnswearsCertificatioEntity(answersCertifications);
        certificationStudentRepository.save(certificationStudentEntity);
        return certificationStudentCreated;
    }
}
