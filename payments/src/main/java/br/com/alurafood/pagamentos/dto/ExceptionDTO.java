package br.com.alurafood.pagamentos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionDTO {

    private HttpStatus statusCodeError;

    private String messageError;
}
