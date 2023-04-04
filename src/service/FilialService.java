package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import modelo.Filial;
import modelo.Funcionario;

@Stateless
public class FilialService extends GenericService<Filial> {

	public FilialService() {
		super(Filial.class);
	}
	
	public Long retornarNumFuncionariosFilial(Filial f) {
		final CriteriaBuilder criteriaB = getEntityManager().getCriteriaBuilder();	
    	final CriteriaQuery<Long> cQuery = criteriaB.createQuery(Long.class);
    	
    	final Root<Funcionario> rootAtd = cQuery.from(Funcionario.class);
    	
    	Expression<Filial> expFilial = rootAtd.get("filial");
		
    	cQuery.select(criteriaB.count(expFilial)).where(criteriaB.equal(expFilial, f));
    	
    	Long result = getEntityManager().createQuery(cQuery).getSingleResult();
    	
    	return result;
	}
	
	public List<Filial> retornarFiliaisOrdenadaPorNome() {
		final CriteriaBuilder criteriaB = getEntityManager().getCriteriaBuilder();	
    	final CriteriaQuery<Filial> cQuery = criteriaB.createQuery(Filial.class);
    	
    	final Root<Filial> rootAtd = cQuery.from(Filial.class);
    	
    	Expression<String> expNome = rootAtd.get("nome");
    	
    	cQuery.orderBy(criteriaB.asc(expNome));
    
    	List<Filial> result = getEntityManager().createQuery(cQuery).getResultList();
    	
    	return result;
	}
}
