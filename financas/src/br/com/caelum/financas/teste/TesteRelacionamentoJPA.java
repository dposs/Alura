package br.com.caelum.financas.teste;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.EntityManager;

import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.TipoMovimentacao;
import br.com.caelum.financas.util.JPAUtil;

public class TesteRelacionamentoJPA {

	public static void main(String[] args) {

		Conta conta = new Conta();
		conta.setTitular("Daniel Possamai");
		conta.setBanco("Bradesco");
		conta.setNumero("00478113");
		conta.setAgencia("175");
		
		Movimentacao movimentacao = new Movimentacao();
		movimentacao.setDescricao("Alura");
		movimentacao.setData(Calendar.getInstance());
		movimentacao.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		movimentacao.setValor(new BigDecimal("123.9"));
		
		movimentacao.setConta(conta);
		
		EntityManager entityManager = new JPAUtil().getEntityManager();
		entityManager.getTransaction().begin();
		
		entityManager.persist(conta);
		entityManager.persist(movimentacao);
		
		entityManager.getTransaction().commit();
		
		entityManager.close();
	}
}