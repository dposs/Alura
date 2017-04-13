package br.com.caelum.financas.teste;

import javax.persistence.EntityManager;

import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.util.JPAUtil;

public class TesteJPA {

	public static void main(String[] args) {

		double inicio = System.currentTimeMillis();
		
		// Detached
		Conta contaDetached = new Conta();
		contaDetached.setId(9);
		contaDetached.setTitular("Daniel Possamai");
		contaDetached.setBanco("Bradesco");
		contaDetached.setNumero("00478113");
		contaDetached.setAgencia("175");
		
		// Transient
		Conta contaTransient = new Conta();
		contaTransient.setTitular("Daniel Possamai");
		contaTransient.setBanco("Bradesco");
		contaTransient.setNumero("00478113");
		contaTransient.setAgencia("175");
		
		EntityManager entityManager = new JPAUtil().getEntityManager();
		entityManager.getTransaction().begin();
		
		// Managed
		Conta contaManaged = entityManager.find(Conta.class, 9); // Entire object (with sql select)
		contaManaged = entityManager.getReference(Conta.class, 9); // Only id object (without sql select)
		
		entityManager.persist(contaTransient);
		
		contaDetached.setTitular("Daniel 1");
		contaDetached.setTitular("Daniel 3");
		contaDetached = entityManager.merge(contaDetached);
		
		// Removed
		entityManager.remove(contaManaged);
		entityManager.remove(contaTransient);
		entityManager.remove(contaDetached);
		
		entityManager.getTransaction().commit();
		
		entityManager.close();
		
		 double fim = System.currentTimeMillis();
	     System.out.println("Executado em: " + (fim - inicio)/1000 + "s");
	}

}
