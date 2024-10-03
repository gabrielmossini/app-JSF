package jakarta.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.beans.Jogo;
import jakarta.beans.Personagem;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.fema.JogoDao;
import jakarta.fema.PersonagemDao;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ConversationScoped
public class PersonagemController implements Serializable {
	
	@Inject
	private Conversation conversation;

	private static final long serialVersionUID = 1L;

	private List<Personagem> personagensRecuperados;

	private Personagem personagem;
	
	private List<Jogo> jogos = new ArrayList<Jogo>();
	
	private String nomeJogoSelecionado;

	public String confirmarCadastro() {
		List<Jogo> jogs = new JogoDao().recuperarTodos();
		for (Jogo j : jogs) {
			if (nomeJogoSelecionado.equals(j.getNome())) {
				personagem.setJogo(j);
				break;
			}
		}
		
		personagensRecuperados.add(personagem);
		return "consultarpersonagem.xhtml";
	}

	public String prepararCadastro() {
		jogos = new JogoDao().recuperarTodos();
		
		personagem = new Personagem();
		return "cadastrarpersonagem.xhtml";
	}

	public void removerPersonagem(Personagem p) {
		personagensRecuperados.remove(p);
		FacesContext f = FacesContext.getCurrentInstance();
		String mensagem = "Registro excluído com sucesso!";
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, "");
		f.addMessage(null, facesMessage);
	}

	public String prepararConsulta() {
		conversation.begin();
		personagensRecuperados = new PersonagemDao().recuperarTodos();
		return "consultarpersonagem.xhtml";
	}
	
	public String prepararMenu() {
		conversation.end();
		return "menuprincipal.xhtml";
	}

	public String getNomeJogoSelecionado() {
		return nomeJogoSelecionado;
	}

	public void setNomeJogoSelecionado(String nomeJogoSelecionado) {
		this.nomeJogoSelecionado = nomeJogoSelecionado;
	}

	public List<Personagem> getPersonagensRecuperados() {
		return personagensRecuperados;
	}

	public List<Jogo> getJogos() {
		return jogos;
	}

	public void setJogos(List<Jogo> jogos) {
		this.jogos = jogos;
	}

	public void setPersonagensRecuperados(List<Personagem> personagensRecuperados) {
		this.personagensRecuperados = personagensRecuperados;
	}

	public Personagem getPersonagem() {
		return personagem;
	}

	public void setPersonagem(Personagem personagem) {
		this.personagem = personagem;
	}
}
