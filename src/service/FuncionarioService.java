package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import modelo.Funcionario;

@Stateless
public class FuncionarioService extends GenericService<Funcionario> {

	public FuncionarioService() {
		super(Funcionario.class);
	}

	public List<Funcionario> retornarFuncionariosOrdenadoPorNome() {
		final CriteriaBuilder criteriaB = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Funcionario> cQuery = criteriaB.createQuery(Funcionario.class);

		final Root<Funcionario> rootFunc = cQuery.from(Funcionario.class);

		Expression<String> expNome = rootFunc.get("nome");

		cQuery.orderBy(criteriaB.asc(expNome));

		List<Funcionario> result = getEntityManager().createQuery(cQuery).getResultList();

		return result;
	}
	
	public List<Funcionario> retornarFuncionariosOrdenadoPeloSalario() {
		final CriteriaBuilder criteriaB = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Funcionario> cQuery = criteriaB.createQuery(Funcionario.class);

		final Root<Funcionario> rootFunc = cQuery.from(Funcionario.class);

		//Expression<String> expNome = rootFunc.get("nome");

		cQuery.orderBy(criteriaB.desc(rootFunc.get("salario")));

		List<Funcionario> result = getEntityManager().createQuery(cQuery).getResultList();

		return result;
	}

	public List<Funcionario> retornarFuncionariosFiltradosSalarioFilial(Long idFilial, Double salarioMenor,
			Double salarioMaior) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Funcionario> criteriaQuery = cb.createQuery(Funcionario.class);

		Root<Funcionario> rootFunc = criteriaQuery.from(Funcionario.class);

		
		// tratamento para intervalos não válidos
		if ((salarioMenor == 0 && salarioMaior == 0) || (salarioMenor < 0 || salarioMaior < 0) || (salarioMenor > salarioMaior)) {
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage("Insira um intervalo válido!"));
			retornarFuncionariosOrdenadoPeloSalario();
		} else {
			// tratamento para exibição de todas filiais
			if (idFilial == -1) {
				criteriaQuery.select(rootFunc).where(cb.between(rootFunc.<Double>get("salario"), salarioMenor, salarioMaior));
			} else {	
				criteriaQuery.select(rootFunc).where(cb.equal(rootFunc.get("filial"), idFilial),
						cb.between(rootFunc.<Double>get("salario"), salarioMenor, salarioMaior));
			}
		}

		criteriaQuery.orderBy(cb.desc(rootFunc.get("salario")));

		List<Funcionario> resultado = getEntityManager().createQuery(criteriaQuery).getResultList();

		return resultado;
	}

	public List<Funcionario> listarFuncionariosPorNome(String nome) {

		final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Funcionario> criteriaQuery = criteriaBuilder.createQuery(Funcionario.class);

		final Root<Funcionario> rootFuncionario = criteriaQuery.from(Funcionario.class);

		final Expression<String> expNome = rootFuncionario.get("nome");
		criteriaQuery.select(rootFuncionario);
		criteriaQuery.where(criteriaBuilder.like(expNome, "%" + nome + "%"));
		criteriaQuery.orderBy(criteriaBuilder.asc(expNome));

		List<Funcionario> resultado = getEntityManager().createQuery(criteriaQuery).getResultList();

		return resultado;

	}
}
