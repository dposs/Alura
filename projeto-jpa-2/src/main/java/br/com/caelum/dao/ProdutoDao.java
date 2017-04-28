package br.com.caelum.dao;

import java.util.List;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;

import br.com.caelum.model.Produto;

@Repository
public class ProdutoDao {

	@PersistenceContext
	private EntityManager em;

	public List<Produto> getProdutos() {
		EntityGraph<Produto> graph = em.createEntityGraph(Produto.class);
		graph.addAttributeNodes("categorias");
		
		return em.createQuery("select distinct p from Produto p", Produto.class)
				.setHint(QueryHints.HINT_LOADGRAPH, graph)
				.getResultList();
	}

	public Produto getProduto(Integer id) {
		EntityGraph<Produto> graph = em.createEntityGraph(Produto.class);
		graph.addAttributeNodes("categorias");
		
		CriteriaBuilder criteria = em.getCriteriaBuilder();
		CriteriaQuery<Produto> query = criteria.createQuery(Produto.class);
		Root<Produto> produto = query.from(Produto.class);
		
		query.where(criteria.equal(produto.get("id"), id));
		
		return em.createQuery(query)
				.setHint(QueryHints.HINT_LOADGRAPH, graph)
				.getSingleResult();
	}

//	public List<Produto> getProdutos(String nome, Integer categoriaId, Integer lojaId) {
//		CriteriaBuilder criteria = em.getCriteriaBuilder();
//		CriteriaQuery<Produto> query = criteria.createQuery(Produto.class);
//		Root<Produto> produto = query.from(Produto.class);
//
//		Path<String> nomePath = produto.get("nome");
//		Path<Integer> lojaPath = produto.get("loja").get("id");
//		Path<Integer> categoriaPath = produto.join("categorias").get("id");
//
//		List<Predicate> predicates = new ArrayList<>();
//
//		if (!nome.isEmpty()) {
//			Predicate nomeIgual = criteria.like(nomePath, "%" + nome + "%");
//			predicates.add(nomeIgual);
//		}
//		if (categoriaId != null) {
//			Predicate categoriaIgual = criteria.equal(categoriaPath, categoriaId);
//			predicates.add(categoriaIgual);
//		}
//		if (lojaId != null) {
//			Predicate lojaIgual = criteria.equal(lojaPath, lojaId);
//			predicates.add(lojaIgual);
//		}
//
//		query.where(predicates.toArray(new Predicate[0]));
//		
//		TypedQuery<Produto> typedQuery = em.createQuery(query);
//		return typedQuery.getResultList();
//	}
	
	public List<Produto> getProdutosConjunction(String nome, Integer categoriaId, Integer lojaId) {
		EntityGraph<Produto> graph = em.createEntityGraph(Produto.class);
		graph.addAttributeNodes("categorias");
		
		CriteriaBuilder criteria = em.getCriteriaBuilder();
		CriteriaQuery<Produto> query = criteria.createQuery(Produto.class);
		Root<Produto> produto = query.from(Produto.class);
		query.select(produto);

		Path<String> pNomeProduto = produto.get("nome");
		Path<Integer> pLojaId = produto.get("loja").get("id");
		Path<Integer> pCategoriaId = produto.join("categorias", JoinType.LEFT).get("id");

		Predicate where = criteria.conjunction();
		
		if (!nome.isEmpty()) {
			where = criteria.and(where, criteria.like(pNomeProduto, "%" + nome + "%"));
		}
		
		if (categoriaId != null) {
			where = criteria.and(where, criteria.equal(pCategoriaId, categoriaId));
		}
		
		if (lojaId != null) {
			where = criteria.and(where, criteria.equal(pLojaId, lojaId));
		}

		query.where(where);
		
		return em.createQuery(query)
				.setHint(QueryHints.HINT_FETCHGRAPH, graph)
				.getResultList();
	}

	public void insere(Produto produto) {
		if (produto.getId() == null)
			em.persist(produto);
		else
			em.merge(produto);
	}

}
