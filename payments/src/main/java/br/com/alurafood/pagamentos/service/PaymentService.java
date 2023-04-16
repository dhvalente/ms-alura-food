package br.com.alurafood.pagamentos.service;

import br.com.alurafood.pagamentos.dto.PaymentDTO;
import br.com.alurafood.pagamentos.enums.Status;
import br.com.alurafood.pagamentos.exception.PaymentNotFoundException;
import br.com.alurafood.pagamentos.http.OrderClient;
import br.com.alurafood.pagamentos.model.Payment;
import br.com.alurafood.pagamentos.repository.PaymentRepository;
import jakarta.persistence.criteria.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderClient orderClient;
    public PaymentDTO createPayment(PaymentDTO dto) {
        Payment payment = modelMapper.map(dto, Payment.class);
        payment.setStatus(Status.CREATED);
        paymentRepository.save(payment);
        return modelMapper.map(payment, PaymentDTO.class);
    }

    public void deletePaymentById(Long id) {
        paymentRepository.deleteById(id);
    }

    public PaymentDTO updatePayment(Long id, PaymentDTO dto) {
        Payment payment = modelMapper.map(dto, Payment.class);
        payment.setId(id);
        payment = paymentRepository.save(payment);
        return modelMapper.map(payment, PaymentDTO.class);
    }

    public Page<PaymentDTO> findAll(Pageable paginacao) {
        return paymentRepository
                .findAll(paginacao)
                .map(p -> modelMapper.map(p, PaymentDTO.class));
    }

    public PaymentDTO findById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));
        return modelMapper.map(payment, PaymentDTO.class);
    }

    public void confirmPayment(Long id){
        Optional<Payment> payment = paymentRepository.findById(id);

        if (payment.isEmpty()) {
            throw new PaymentNotFoundException(id);
        }
        payment.get().setStatus(Status.CONFIRMED);
        paymentRepository.save(payment.get());
        orderClient.updatePayment(payment.get().getOrderId());
    }
}
