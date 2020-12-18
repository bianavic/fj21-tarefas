package br.com.caelum.tarefas.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.caelum.tarefas.dao.TarefaDao;
import br.com.caelum.tarefas.dto.ErroDeValidacaoDto;
import br.com.caelum.tarefas.modelo.Tarefa;

@Controller
@Transactional
public class TarefasController {
	
	private TarefaDao dao;
	
	@Autowired
	public TarefasController(@Qualifier("jpaTarefaDao") TarefaDao dao) {
		this.dao = dao;
	}

	@ResponseBody
	@RequestMapping(value = "/adicionaTarefa", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> adiciona(@Valid Tarefa tarefa, BindingResult bindResult) { // INJETA o objeto, cuidado, sem validar, sendo null ou nao o objeto sempre estara preenchido
		if (bindResult.hasErrors()) {
			List<FieldError> errosNosAtributos = bindResult.getFieldErrors();

			List<ErroDeValidacaoDto> errosDto = new ArrayList<>();
			for (FieldError fieldError : errosNosAtributos) {
				ErroDeValidacaoDto dto = new ErroDeValidacaoDto(fieldError.getField(), fieldError.getDefaultMessage());
				errosDto.add(dto);
			}

			return ResponseEntity.badRequest().body(errosDto);
		}

		dao.adiciona(tarefa);

		return ResponseEntity.status(HttpStatus.CREATED).body(tarefa); // com esse simples return ja consegue serializar nosso objeto retorna para o json
	}

	@ResponseBody
	@RequestMapping(value = "/listaTarefas", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Tarefa> lista() {
		return dao.lista();
	}

	@ResponseBody // pq tem o responsebody o spring nao procura pelo jdbc
	@RequestMapping(value = "/removeTarefa", method = RequestMethod.DELETE)
	public void remove(Tarefa tarefa) {
		dao.remove(tarefa);
	}

	@ResponseBody
	@RequestMapping(value = "/alteraTarefa", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Tarefa altera(@RequestBody Tarefa tarefa) {
		dao.altera(tarefa);

		return tarefa;
	}
	
	@ResponseBody
	@RequestMapping(value = "/finalizaTarefa", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public Tarefa finaliza(Long id) {
		dao.finaliza(id);
		
		return dao.buscaPorId(id);
	}

}
