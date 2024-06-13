package com.eduardogomes.gomes_bar.services;

import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.eduardogomes.gomes_bar.dto.CreateDebtDto;
import com.eduardogomes.gomes_bar.entities.Debt;
import com.eduardogomes.gomes_bar.repositories.DebtRepository;
import com.eduardogomes.gomes_bar.repositories.UserRepository;

@Service
public class DebtService {

    @Autowired
    DebtRepository debtRepository;
    
    @Autowired
    UserRepository userRepository;

    public Debt createDebt(CreateDebtDto debt, JwtAuthenticationToken token) throws Exception{
        var user = userRepository.findById(UUID.fromString(token.getName()));
        
        var newDebt = new Debt();
        newDebt.setName(debt.name());
        newDebt.setValue(debt.value());
        newDebt.setUser(user.get());

        return debtRepository.save(newDebt);
    }

    public void deleteDebt(Long idDebt, JwtAuthenticationToken token) {
        var debt = debtRepository.findById(idDebt)
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(debt.getUser().getId().equals(UUID.fromString(token.getName()))){
            debtRepository.deleteById(debt.getId());
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    public Page<Debt> listDebts(int page, int pageSize, JwtAuthenticationToken token){
        var userId = UUID.fromString(token.getName());
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "createdAt");
        return debtRepository.findAllByUserId(userId, pageable);
        
    }
}
