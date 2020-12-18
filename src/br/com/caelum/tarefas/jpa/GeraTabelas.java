package br.com.caelum.tarefas.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.caelum.tarefas.modelo.Tarefa;

public class GeraTabelas {

	public static void main(String[] args) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tarefas");
		
		EntityManager entityManager = factory.createEntityManager();

		Tarefa tarefa = new Tarefa();
		tarefa.setDescricao("Estudar JPA + Hibernate");

		entityManager.getTransaction().begin();
		entityManager.persist(tarefa);
		entityManager.getTransaction().commit();

		entityManager.close();
		
		factory.close();


	}
}
