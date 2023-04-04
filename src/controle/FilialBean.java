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
import service.FilialService;

@ViewScoped
@ManagedBean
public class FilialBean {
	
	@EJB
	private FilialService filialService;
	
	private Filial filial = new Filial();
	
	private List<Filial> listaFiliais = new ArrayList<Filial>();
	
	@PostConstruct
	public void incializar() {
		listarFiliais();
	}
	
	private void listarFiliais() {
		listaFiliais = filialService.retornarFiliaisOrdenadaPorNome(); 
	}
	
	public Long pegarNumFuncionarios(Filial f) {
		filial.setNumFuncionarios(filialService.retornarNumFuncionariosFilial(f));
		Long result = filial.getNumFuncionarios();
		return result;
	}
	
	public void gravarFilial() {
		String msg="";
		if(filial.getId()==null) {
			filialService.create(filial);
			msg="Gravada";
		}else {
			filialService.merge(filial);
			msg="Atualizada";
		}
		
		FacesContext.getCurrentInstance().addMessage("msg", 
				new FacesMessage("Filial "+msg+" com sucesso!"));
		filial = new Filial();
		listarFiliais();
	}
	
	public void excluirFilial(Filial f) {
		filialService.remove(f);
		listarFiliais();
		FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage("Filial excluida com sucesso!"));
	}
	
	public void editarFilial(Filial f) {		
			filialService.merge(f);	
	}
	
	public void carregarFilial(Filial pAtual) {
		filial = pAtual;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public List<Filial> getListaFiliais() {
		return listaFiliais;
	}

	public void setListaFiliais(List<Filial> listaFiliais) {
		this.listaFiliais = listaFiliais;
	}
	
	
}
