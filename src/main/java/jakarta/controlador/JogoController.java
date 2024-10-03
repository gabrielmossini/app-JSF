package jakarta.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.beans.Desenvolvedora;
import jakarta.beans.Jogo;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.fema.DesenvolvedoraDao;
import jakarta.fema.JogoDao;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ConversationScoped
public class JogoController implements Serializable {
	
	@Inject
	private Conversation conversation;

	private static final long serialVersionUID = 1L;

	private List<Jogo> jogosRecuperados;

	private Jogo jogo;
	
	private List<Desenvolvedora> desenvolvedoras = new ArrayList<Desenvolvedora>();

	private String nomeDesenvolvedoraSelecionada;


	public String confirmarCadastro() {
		List<Desenvolvedora> desenv = new DesenvolvedoraDao().recuperarTodos();
		for (Desenvolvedora d : desenv) {
			if (nomeDesenvolvedoraSelecionada.equals(d.getNome())) {
				jogo.setDesenvolvedora(d);
				break;
			}
		}
		
		jogosRecuperados.add(jogo);
		return "consultarjogo.xhtml";
	}

	public String prepararCadastro() {
		desenvolvedoras = new DesenvolvedoraDao().recuperarTodos();
		
		jogo = new Jogo();
		return "cadastrarjogo.xhtml";
	}

	public void removerJogo(Jogo j) {
		jogosRecuperados.remove(j);
		FacesContext f = FacesContext.getCurrentInstance();
		String mensagem = "Registro excluído com sucesso!";
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, "");
		f.addMessage(null, facesMessage);
	}

	public String prepararConsulta() {
		conversation.begin();
		jogosRecuperados = new JogoDao().recuperarTodos();
		return "consultarjogo.xhtml";
	}
	
	public String prepararMenu() {
		conversation.end();
		return "menuprincipal.xhtml";
	}

	public List<Desenvolvedora> getDesenvolvedoras() {
		return desenvolvedoras;
	}

	public void setDesenvolvedoras(List<Desenvolvedora> desenvolvedoras) {
		this.desenvolvedoras = desenvolvedoras;
	}

	public String getNomeDesenvolvedoraSelecionada() {
		return nomeDesenvolvedoraSelecionada;
	}

	public void setNomeDesenvolvedoraSelecionada(String nomeDesenvolvedoraSelecionada) {
		this.nomeDesenvolvedoraSelecionada = nomeDesenvolvedoraSelecionada;
	}

	public List<Jogo> getJogosRecuperados() {
		return jogosRecuperados;
	}

	public void setJogosRecuperados(List<Jogo> jogosRecuperados) {
		this.jogosRecuperados = jogosRecuperados;
	}

	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}
}
