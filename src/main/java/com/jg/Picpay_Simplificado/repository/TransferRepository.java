package com.jg.Picpay_Simplificado.repository;

import com.jg.Picpay_Simplificado.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}