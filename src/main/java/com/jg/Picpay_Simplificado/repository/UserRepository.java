package com.jg.Picpay_Simplificado.repository;

import com.jg.Picpay_Simplificado.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User,Long> {
}