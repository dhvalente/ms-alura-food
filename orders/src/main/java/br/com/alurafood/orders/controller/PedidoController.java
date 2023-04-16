package br.com.alurafood.orders.controller;

import br.com.alurafood.orders.dtos.OrderDTO;
import br.com.alurafood.orders.service.OrderService;
import br.com.alurafood.orders.dtos.StatusDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class PedidoController {

        @Autowired
        private OrderService service;

        @GetMapping()
        public List<OrderDTO> listarTodos() {
            return service.findAll();
        }

        @GetMapping("/{id}")
        public ResponseEntity<OrderDTO> listarPorId(@PathVariable @NotNull Long id) {
            OrderDTO dto = service.findById(id);

            return  ResponseEntity.ok(dto);
        }

        @GetMapping("/porta")
        public String retornaPorta(@Value("${local.server.port}") String porta){
            return String.format("Requisição respondida pela instância executando na porta %s", porta);
        }

        @PostMapping()
        public ResponseEntity<OrderDTO> realizaPedido(@RequestBody @Valid OrderDTO dto, UriComponentsBuilder uriBuilder) {
            OrderDTO pedidoRealizado = service.createOrder(dto);

            URI endereco = uriBuilder.path("/pedidos/{id}").buildAndExpand(pedidoRealizado.getId()).toUri();

            return ResponseEntity.created(endereco).body(pedidoRealizado);

        }

        @PutMapping("/{id}/status")
        public ResponseEntity<OrderDTO> updateStatus(@PathVariable Long id, @RequestBody StatusDTO status){
           OrderDTO dto = service.updateStatus(id, status);

            return ResponseEntity.ok(dto);
        }


        @PutMapping("/{id}/pago")
        public ResponseEntity<Void> aprovaPagamento(@PathVariable @NotNull Long id) {
            service.approvePaymentOrder(id);

            return ResponseEntity.ok().build();

        }
}
