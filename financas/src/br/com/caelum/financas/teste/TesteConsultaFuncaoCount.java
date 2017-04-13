package br.com.caelum.financas.teste;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.util.JPAUtil;

public class TesteConsultaFuncaoCount {

	public static void main(String[] args) {

		EntityManager manager = new JPAUtil().getEntityManager();
		
		Conta conta = manager.find(Conta.class, 2);
		
		TypedQuery<Long> query = manager.createQuery("select count(m) from Movimentacao m where m.conta = :conta", Long.class);
		query.setParameter("conta", conta);
		
		Long quantidade = query.getSingleResult();
		
		System.out.println("Total de movimentações: " + quantidade);
		
		manager.close();
	}
}