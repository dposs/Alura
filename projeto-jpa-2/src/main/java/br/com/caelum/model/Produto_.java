package br.com.caelum.model;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Produto.class)
public abstract class Produto_ {
	
	public static volatile SingularAttribute<Produto, Integer> id;
	public static volatile SingularAttribute<Produto, String> nome;
	public static volatile SingularAttribute<Produto, String> linkDaFoto;
	public static volatile ListAttribute<Produto, Categoria> categorias;
	public static volatile SingularAttribute<Produto, String> descricao;
	public static volatile SingularAttribute<Produto, Double> preco;
}
