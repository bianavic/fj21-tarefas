package br.com.caelum.tarefas.controller.advice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.caelum.tarefas.dto.ErroDeValidacaoDto;

@ControllerAdvice
public class TrataErroValidacao {
	
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public List<ErroDeValidacaoDto> trataErroDeValidacao(BindException e) {
		// List<FieldError> errosNosAtributos = e.getFieldErrors();
			
			List<ErroDeValidacaoDto> errosDto = new ArrayList<>();
			
			List<FieldError> errosNosAtributos = e.getBindingResult().getFieldErrors();
			for (FieldError fieldError : errosNosAtributos) {
				ErroDeValidacaoDto dto = new ErroDeValidacaoDto(fieldError.getField(), fieldError.getDefaultMessage());
				errosDto.add(dto);
			}		
			return errosDto;	
	}
	
}
