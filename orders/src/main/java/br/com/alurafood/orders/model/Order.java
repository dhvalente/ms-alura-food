package br.com.alurafood.orders.model;

import br.com.alurafood.orders.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime dateHour;

    @NotNull @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(cascade=CascadeType.PERSIST, mappedBy="order")
    private List<OrderItem> itens = new ArrayList<>();
}
