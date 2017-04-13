package br.com.caelum.financas.teste;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.util.JPAUtil;

public class TesteMovimentacaoConta {

	public static void main(String[] args) {
		
		EntityManager manager = new JPAUtil().getEntityManager();
		
		//Conta conta = manager.find(Conta.class, 1);
		
		//System.out.println(conta.getMovimentacoes().size());
		
		//Movimentacao movimentacao = manager.find(Movimentacao.class, 2);
		//System.out.println(movimentacao.getConta().getTitular());
		
		// Eager
		TypedQuery<Conta> query = manager.createQuery("select c from Conta c join fetch c.movimentacoes", Conta.class);
		List<Conta> contas = query.getResultList();
			
		for (Conta conta : contas) {
			System.out.println("N�mero de movimenta��es: " + conta.getMovimentacoes().size());
		}
		
		manager.close();
	}
	
}
