package br.com.alurafood.orders.service;

import br.com.alurafood.orders.dtos.OrderDTO;
import br.com.alurafood.orders.dtos.StatusDTO;
import br.com.alurafood.orders.enums.Status;
import br.com.alurafood.orders.model.Order;
import br.com.alurafood.orders.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private final ModelMapper modelMapper;


    public List<OrderDTO> findAll() {
        return repository.findAll().stream()
                .map(p -> modelMapper.map(p, OrderDTO.class))
                .collect(Collectors.toList());
    }

    public OrderDTO findById(Long id) {
        Order pedido = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(pedido, OrderDTO.class);
    }

    public OrderDTO createOrder(OrderDTO dto) {
        Order pedido = modelMapper.map(dto, Order.class);

        pedido.setDateHour(LocalDateTime.now());
        pedido.setStatus(Status.CONFIRMED);
        pedido.getItens().forEach(item -> item.setOrder(pedido));
        Order salvo = repository.save(pedido);

        return modelMapper.map(pedido, OrderDTO.class);
    }

    public OrderDTO updateStatus(Long id, StatusDTO dto) {

        Order pedido = repository.toIdWithItens(id);

        if (pedido == null) {
            throw new EntityNotFoundException();
        }

        pedido.setStatus(dto.getStatus());
        repository.updateStatus(dto.getStatus(), pedido);
        return modelMapper.map(pedido, OrderDTO.class);
    }

    public void approvePaymentOrder(Long id) {

        Order order = repository.toIdWithItens(id);

        if (order == null) {
            throw new EntityNotFoundException();
        }

        order.setStatus(Status.PAID);
        repository.updateStatus(Status.PAID, order);
    }
}
