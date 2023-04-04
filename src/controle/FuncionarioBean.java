package controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import modelo.Filial;
import modelo.Funcionario;
import service.FilialService;
import service.FuncionarioService;

@ViewScoped
@ManagedBean
public class FuncionarioBean {

	@EJB
	private FuncionarioService funcionarioService;
	@EJB
	private FilialService filialService;

	private Funcionario funcionario = new Funcionario();
	private List<Funcionario> listaFuncionarios = new ArrayList<Funcionario>();
	// justifica-se a criação de outra lista, pois do contrário, afetaria a tela de
	// Funcionários
	private List<Funcionario> listaFuncionariosOrdenadosPeloSalario = new ArrayList<Funcionario>();

	private List<Filial> filiais = new ArrayList<Filial>();
	private Long idFilialSelecionada = 0L;
	private String filtro = "";

	private Double salarioMenor;
	private Double salarioMaior;

	@PostConstruct
	public void iniciar() {
		filiais = filialService.listAll();
		listarFuncionarios();
		carregarListaFuncionariosListadosSalario();
		
	}

	private void listarFuncionarios() {
		listaFuncionarios = funcionarioService.retornarFuncionariosOrdenadoPorNome();
	}

	private void carregarListaFuncionariosListadosSalario() {
		listaFuncionariosOrdenadosPeloSalario = funcionarioService.retornarFuncionariosOrdenadoPeloSalario();
	}

	public void filtrarFuncionario() {
		listaFuncionarios = funcionarioService.listarFuncionariosPorNome(filtro);
		if (listaFuncionarios.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("msg",
					new FacesMessage("Nenhum Funcionário encontrado para essa pesquisa!"));
		}
		
	}

	public void filtrarFuncionariosFaixaSalarial(Filial filial, Double salarioMenor, Double salarioMaior) {
		listaFuncionariosOrdenadosPeloSalario = funcionarioService
				.retornarFuncionariosFiltradosSalarioFilial(idFilialSelecionada, salarioMenor, salarioMaior);
		if (listaFuncionariosOrdenadosPeloSalario.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("msg",
					new FacesMessage("Nenhum Funcionário encontrado com essa faixa salarial!"));
		}
	}

	public void gravarFuncionario() {
		Long idAux = 0L;

		try {
			idAux = funcionario.getFilial().getId();
		} catch (Exception e) {
			// TODO: handle exception
		}

		funcionario.setFilial(this.filialService.obtemPorId(idFilialSelecionada));
		String msg = "";

		if (funcionario.getId() == null) {
			funcionarioService.create(funcionario);
			msg = "Gravado";
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage("Funcionário " + msg + " sucesso!"));
			funcionario = new Funcionario();
			this.idFilialSelecionada = 0L;
			

		} else {
			if (idFilialSelecionada == idAux) {
				System.out.println("idFilialSelecionada ============>" + idAux);
				System.out.println("idFilialSelecionada ============>" + idFilialSelecionada);
				funcionarioService.merge(funcionario);
				msg = "Atualizado";
				FacesContext.getCurrentInstance().addMessage("msg",
						new FacesMessage("Funcionário " + msg + " sucesso!"));
				funcionario = new Funcionario();
				this.idFilialSelecionada = 0L;
				

			} else {
				FacesContext.getCurrentInstance().addMessage("msg",
						new FacesMessage("A Filial não pode ser alterada!"));
				funcionario = new Funcionario();
				this.idFilialSelecionada = 0L;
			}
		}
		listarFuncionarios();
	}

	public void excluirFuncionario(Funcionario func) {
		funcionarioService.remove(func);
		listarFuncionarios();
		FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage("Funcionário excluído com sucesso!"));
	}

	public void carregarFuncionario(Funcionario func) {
		funcionario = func;
		idFilialSelecionada = funcionario.getFilial().getId();		

	}

	public FuncionarioService getFuncionarioService() {
		return funcionarioService;
	}

	public void setFuncionarioService(FuncionarioService funcionarioService) {
		this.funcionarioService = funcionarioService;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public FilialService getFilialService() {
		return filialService;
	}

	public void setFilialService(FilialService filialService) {
		this.filialService = filialService;
	}

	public List<Filial> getFiliais() {
		return filiais;
	}

	public void setFiliais(List<Filial> filiais) {
		this.filiais = filiais;
	}

	public Long getIdFilialSelecionada() {
		return idFilialSelecionada;
	}

	public void setIdFilialSelecionada(Long idFilialSelecionada) {
		this.idFilialSelecionada = idFilialSelecionada;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public Double getSalarioMenor() {
		return salarioMenor;
	}

	public void setSalarioMenor(Double salarioMenor) {
		this.salarioMenor = salarioMenor;
	}

	public Double getSalarioMaior() {
		return salarioMaior;
	}

	public void setSalarioMaior(Double salarioMaior) {
		this.salarioMaior = salarioMaior;
	}

	public List<Funcionario> getListaFuncionariosOrdenadosPeloSalario() {
		return listaFuncionariosOrdenadosPeloSalario;
	}

	public void setListaFuncionariosOrdenadosPeloSalario(List<Funcionario> listaFuncionariosOrdenadosPeloSalario) {
		this.listaFuncionariosOrdenadosPeloSalario = listaFuncionariosOrdenadosPeloSalario;
	}

}
