package br.com.alurafood.pagamentos.model;

import br.com.alurafood.pagamentos.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Positive
    private BigDecimal value;
    @NotBlank
    @Size(max=100)
    private String name;
    @NotBlank
    @Size(max=19)
    private String number;
    @NotBlank
    @Size(max=7)
    private String expiration;
    @NotBlank
    @Size(min=3, max=3)
    private String code;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;
    @NotNull
    private Long orderId;
    @NotNull
    private Long formOfPayment;
}
