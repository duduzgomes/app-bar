package com.eduardogomes.gomes_bar.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.eduardogomes.gomes_bar.dto.CreateDebtDto;
import com.eduardogomes.gomes_bar.entities.Debt;
import com.eduardogomes.gomes_bar.services.DebtService;

@RestController
@RequestMapping("/debt")
public class DebtController {

    @Autowired
    DebtService debtService;

    @PostMapping
    public ResponseEntity<Debt> createDebt(@RequestBody CreateDebtDto debt, JwtAuthenticationToken token) throws Exception{
        var createdDebt = debtService.createDebt(debt, token);
        return ResponseEntity.ok().body(createdDebt);
    }

    @GetMapping
    public ResponseEntity<List<Debt>> listDebts(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int pageSize, 
        JwtAuthenticationToken token){

        var debts = debtService.listDebts(page, pageSize, token);
        return ResponseEntity.ok().body(debts.getContent());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDebt(@PathVariable("id") Long id, JwtAuthenticationToken token){
        debtService.deleteDebt(id, token);
        return ResponseEntity.ok().build();
    }
}
