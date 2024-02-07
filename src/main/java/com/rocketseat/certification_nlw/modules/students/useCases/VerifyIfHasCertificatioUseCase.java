package com.rocketseat.certification_nlw.modules.students.useCases;

import org.springframework.stereotype.Service;

import com.rocketseat.certification_nlw.modules.students.dto.VerifyHasCerticicationDTO;

@Service
public class VerifyIfHasCertificatioUseCase {
    public boolean execute(VerifyHasCerticicationDTO dto){
        if(dto.getEmail().equals("pessoa@email.com") && dto.getTechnology().equals("Java")){
            return true;
        }
        return false;
    }
}
