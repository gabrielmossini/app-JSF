package jakarta.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.beans.Desenvolvedora;
import jakarta.beans.Jogo;
import jakarta.beans.Personagem;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Named
@ConversationScoped
@Transactional
public class PersonagemController implements Serializable {
	
	@Inject
	private Conversation conversation;
	
	@PersistenceContext
	private EntityManager em;

	private static final long serialVersionUID = 1L;

	private List<Personagem> personagensRecuperados;

	private Personagem personagem;
	
	private List<Jogo> jogos = new ArrayList<Jogo>();
	
	private String nomeJogoSelecionado;

	public String confirmarCadastro() {
		List<Jogo> jog = em.createNativeQuery("select * from jogo", Jogo.class).getResultList();
		for (Jogo j : jog) {
			if (nomeJogoSelecionado.equals(j.getNome())) {
				personagem.setJogo(j);
				break;
			}
		}
		
		try {
			if (personagem == null) {
				personagem = new Personagem();
			}

			em.persist(personagem);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastro confirmado com sucesso!", ""));

			return "consultarpersonagem.xhtml";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao confirmar cadastro", e.getMessage()));

			return null; 
		}
	}

	public String prepararCadastro() {
		jogos = em.createNativeQuery("select * from jogo", Jogo.class).getResultList();
		
		personagem = new Personagem();
		return "cadastrarpersonagem.xhtml";
	}

	public void removerPersonagem(Personagem p) {
		try {
			Personagem personagem = em.merge(p);
			em.remove(personagem);

			FacesContext f = FacesContext.getCurrentInstance();
			String mensagem = "Registro excluído com sucesso!";
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, "");
			f.addMessage(null, facesMessage);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir", e.getMessage()));
		}
	}

	public String prepararConsulta() {
		conversation.begin();
		personagensRecuperados =
				em.createNativeQuery("select * from personagem", Personagem.class).getResultList();

		for (Personagem p : personagensRecuperados) {
			System.out.println("personagem: " + p.getCodigo() + " - " + p.getNome());
		}
		return "consultarpersonagem.xhtml";
	}
	
	
	public String prepararEdicao(Personagem p) {
	    if (p != null) {
	        this.personagem = p;
	    } else {
	        this.personagem = new Personagem();
	    }
	    jogos = em.createNativeQuery("SELECT * FROM jogo", Jogo.class).getResultList();
	    return "editarpersonagem.xhtml";
	}
	
	public String atualizaPersonagem() {
	    try {
	        Personagem existingPersonagem = em.find(Personagem.class, personagem.getCodigo());
	        if (existingPersonagem == null) {
	            FacesContext.getCurrentInstance().addMessage(null,
	                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Código inválido! Personagem não encontrado.", ""));
	            return null;
	        }

	        existingPersonagem.setNome(personagem.getNome());

	        if (nomeJogoSelecionado != null && !nomeJogoSelecionado.isEmpty()) {
	            Jogo selectedJogo = em.createQuery(
	                "SELECT d FROM Jogo d WHERE d.nome = :nome", Jogo.class)
	                .setParameter("nome", nomeJogoSelecionado)
	                .getSingleResult();
	            existingPersonagem.setJogo(selectedJogo);
	        }

	        em.merge(existingPersonagem);
	        FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_INFO, "Personagem atualizado com sucesso!", ""));
	        return "consultarpersonagem.xhtml";  
	    } catch (Exception e) {
	        FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao atualizar Persongem", e.getMessage()));
	        e.printStackTrace();
	        return null;
	    }
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
