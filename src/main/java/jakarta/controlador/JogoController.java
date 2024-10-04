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
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Named
@ConversationScoped
@Transactional
public class JogoController implements Serializable {
	
	@Inject
	private Conversation conversation;
	
	@PersistenceContext
	private EntityManager em;

	private static final long serialVersionUID = 1L;

	private List<Jogo> jogosRecuperados;

	private Jogo jogo;
	
	private List<Desenvolvedora> desenvolvedoras = new ArrayList<Desenvolvedora>();

	private String nomeDesenvolvedoraSelecionada;


	public String confirmarCadastro() {
		
		List<Desenvolvedora> desenv = em.createNativeQuery("select * from desenvolvedora", Desenvolvedora.class).getResultList();
		for (Desenvolvedora d : desenv) {
			if (nomeDesenvolvedoraSelecionada.equals(d.getNome())) {
				jogo.setDesenvolvedora(d);
				break;
			}
		}
		
		try {
			if (jogo == null) {
				jogo = new Jogo();
			}

			em.persist(jogo);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastro confirmado com sucesso!", ""));

			return "consultarjogo.xhtml";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao confirmar cadastro", e.getMessage()));

			return null; 
		}
	}

	public String prepararCadastro() {
		desenvolvedoras = em.createNativeQuery("select * from desenvolvedora", Desenvolvedora.class).getResultList();
		
		jogo = new Jogo();
		return "cadastrarjogo.xhtml";
	}

	public void removerJogo(Jogo j) {
		try {
			Jogo jogo = em.merge(j);
			em.remove(jogo);

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

		jogosRecuperados =
				em.createNativeQuery("select * from jogo", Jogo.class).getResultList();

		for (Jogo j : jogosRecuperados) {
			System.out.println("jogo: " + j.getCodigo() + " - " + j.getNome());
		}
		
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
