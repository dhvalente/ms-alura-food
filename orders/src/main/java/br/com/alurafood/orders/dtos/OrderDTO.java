package br.com.alurafood.orders.dtos;


import br.com.alurafood.orders.enums.Status;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    private LocalDateTime dataHora;
    private Status status;
    private List<OrderItemDTO> itens = new ArrayList<>();



}
