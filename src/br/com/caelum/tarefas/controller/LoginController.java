package br.com.caelum.tarefas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.caelum.tarefas.dao.JdbcTarefaDao;
import br.com.caelum.tarefas.dao.JdbcUsuarioDao;
import br.com.caelum.tarefas.modelo.Usuario;
import br.com.caelum.tarefas.seguranca.GerenciadorDeToken;

@Controller
public class LoginController {
	
	private JdbcUsuarioDao dao;
	
	@Autowired
	public LoginController(JdbcUsuarioDao dao) {
		this.dao = dao;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> login(Usuario usuario) {
		if (dao.existeUsuario(usuario)) {
			GerenciadorDeToken gerenciadorDeToken = new GerenciadorDeToken();
			String tokenJwt = gerenciadorDeToken.geraToken(usuario);
			
			return ResponseEntity.ok(tokenJwt);
		}
		
		return ResponseEntity.badRequest().build();
	}
}
