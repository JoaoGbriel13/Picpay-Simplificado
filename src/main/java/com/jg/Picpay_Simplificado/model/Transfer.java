package com.jg.Picpay_Simplificado.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_transfer")
@Getter
@Setter
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private BigDecimal value;
    @ManyToOne
    @JoinColumn(name = "payer_id", referencedColumnName = "id")
    private User payer;
    @ManyToOne
    @JoinColumn(name = "payee_id", referencedColumnName = "id")
    private User payee;

    public Transfer(){

    }

    public Transfer(BigDecimal value, User userTransfering, User userReceiving) {
        this.value = value;
        this.payer = userTransfering;
        this.payee = userReceiving;
    }
}
