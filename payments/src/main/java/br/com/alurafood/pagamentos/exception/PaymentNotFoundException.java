package br.com.alurafood.pagamentos.exception;

public class PaymentNotFoundException extends RuntimeException  {

    public PaymentNotFoundException(Long id) {
        super(id + " não encontrado(a)");
    }
}
