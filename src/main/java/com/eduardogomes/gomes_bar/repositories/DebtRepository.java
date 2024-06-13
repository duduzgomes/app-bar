package com.eduardogomes.gomes_bar.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.eduardogomes.gomes_bar.entities.Debt;

public interface DebtRepository extends JpaRepository<Debt, Long>{
    
    public Page<Debt> findAllByUserId(UUID id, Pageable page);
}


