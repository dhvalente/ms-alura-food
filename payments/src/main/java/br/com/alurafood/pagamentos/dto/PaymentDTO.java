package br.com.alurafood.pagamentos.dto;

import br.com.alurafood.pagamentos.enums.Status;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class PaymentDTO {

    private Long id;
    private BigDecimal value;
    private String name;
    private String number;
    private String expiration;
    private String code;
    private Status status;
    private Long formOfPayment;
    private Long orderId;
}
