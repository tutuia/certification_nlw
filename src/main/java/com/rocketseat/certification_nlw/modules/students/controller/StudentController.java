package com.rocketseat.certification_nlw.modules.students.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rocketseat.certification_nlw.modules.students.dto.VerifyHasCerticicationDTO;
import com.rocketseat.certification_nlw.modules.students.useCases.VerifyIfHasCertificatioUseCase;

@RestController
@RequestMapping("/students")
public class StudentController {
    
    @Autowired
    private VerifyIfHasCertificatioUseCase verifyIfHasCertificatioUseCase;

    @PostMapping("/verifyIfHasCertification")
    public String verifyIfHasCertification(@RequestBody VerifyHasCerticicationDTO verify){
        if(verifyIfHasCertificatioUseCase.execute(verify)){
            return "Estudante já fez a prova";
        }
        return "Estudante pode fazer a prova";
    }
}