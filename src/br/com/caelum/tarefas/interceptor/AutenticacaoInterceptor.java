package br.com.caelum.tarefas.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import br.com.caelum.tarefas.seguranca.GerenciadorDeToken;

public class AutenticacaoInterceptor extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest rq, HttpServletResponse rs, Object handler) throws Exception {
		
			String uri = rq.getRequestURI();
			if (uri.endsWith("/login")) {
				return true;
			}
			
			String token = pegaTokenJwt(rq);
			if (token == null) {
				rs.sendError(401, "Nao autorizado: token nao encontrado...");
				return false;
			}
			
			GerenciadorDeToken gerenciadorDeToken = new GerenciadorDeToken();
			if (!gerenciadorDeToken.isValido(token)) {
				rs.sendError(403, "Token invalido: não está autorizado a acessar este recurso...");
				return false;
			}
			
			return true;
		}

		private String pegaTokenJwt(HttpServletRequest rq) {
			String authorizationHeader = rq.getHeader("Authorization");
			
			if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
				return authorizationHeader.substring(7, authorizationHeader.length());
			}
			return null;
		}
}