package br.com.caelum.financas.dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.TipoMovimentacao;

public class MovimentacaoDao {

	private EntityManager manager;

	public MovimentacaoDao(EntityManager manager) {
		this.manager = manager;
	}

	public Double getMediaDaContaPeloTipo(Conta conta, TipoMovimentacao tipoMovimentacao) {
		
		TypedQuery<Double> query = manager.createNamedQuery("Movimentacao.mediaDaContaPeloTipo", Double.class);
		
		query.setParameter("conta", conta);
		query.setParameter("tipoMovimentacao", tipoMovimentacao);
		
		return query.getSingleResult();
	}
	
	public Long totalDeMovimentacoes(Conta conta) {

	    TypedQuery<Long> query = manager.createNamedQuery("Movimentacao.totalDeMovimentacoes", Long.class);
	    query.setParameter("conta", conta);

	    return query.getSingleResult();
	}
}
