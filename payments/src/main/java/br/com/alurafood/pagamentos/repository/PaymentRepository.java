package br.com.alurafood.pagamentos.repository;

import br.com.alurafood.pagamentos.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment , Long> {
}
