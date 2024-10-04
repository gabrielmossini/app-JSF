package jakarta.controlador;

import java.io.Serializable;
import java.util.List;

import jakarta.beans.Desenvolvedora;
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
public class DesenvolvedoraController implements Serializable {
	
	@PersistenceContext
	private EntityManager em;
	
	@Inject
	private Conversation conversation;

	private static final long serialVersionUID = 1L;
	private List<Desenvolvedora> desenvolvedorasRecuperadas;
	private Desenvolvedora desenvolvedora;

	public String confirmarCadastro() {
		try {
			if (desenvolvedora == null) {
				desenvolvedora = new Desenvolvedora();
			}

			em.persist(desenvolvedora);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastro confirmado com sucesso!", ""));

			return "consultardesenvolvedora.xhtml";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao confirmar cadastro", e.getMessage()));

			return null; 
		}
	}


	public String prepararCadastro() {
		desenvolvedora = new Desenvolvedora();
		return "cadastrardesenvolvedora.xhtml";
	}

	public void removerDesenvolvedora(Desenvolvedora d) {
		try {
			Desenvolvedora desenvolvedora = em.merge(d);
			em.remove(desenvolvedora);

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
		desenvolvedorasRecuperadas =
				em.createNativeQuery("select * from desenvolvedora", Desenvolvedora.class).getResultList();

		for (Desenvolvedora d : desenvolvedorasRecuperadas) {
			System.out.println("desenvolvedora: " + d.getCodigo() + " - " + d.getNome());
		}

		return "consultardesenvolvedora.xhtml";
	}
	
	public String prepararMenu() {
		conversation.end();
		return "menuprincipal.xhtml";
	}

	public List<Desenvolvedora> getDesenvolvedorasRecuperadas() {
		return desenvolvedorasRecuperadas;
	}

	public void setDesenvolvedorasRecuperadas(List<Desenvolvedora> desenvolvedorasRecuperadas) {
		this.desenvolvedorasRecuperadas = desenvolvedorasRecuperadas;
	}

	public Desenvolvedora getDesenvolvedora() {
		return desenvolvedora;
	}

	public void setDesenvolvedora(Desenvolvedora desenvolvedora) {
		this.desenvolvedora = desenvolvedora;
	}
}
