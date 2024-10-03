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
			// Ensure that 'desenvolvedora' is properly initialized before persisting
			if (desenvolvedora == null) {
				desenvolvedora = new Desenvolvedora();  // Create a new instance if it's null
			}

			// Persist the new instance to the database
			em.persist(desenvolvedora);

			// Commit the transaction and return the navigation outcome
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastro confirmado com sucesso!", ""));

			return "consultardesenvolvedora.xhtml";
		} catch (Exception e) {
			// Handle any errors, such as persistence issues or transaction failures
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao confirmar cadastro", e.getMessage()));

			return null; // Stay on the same page if there's an error
		}
	}


	public String prepararCadastro() {
		desenvolvedora = new Desenvolvedora();
		return "cadastrardesenvolvedora.xhtml";
	}

	public void removerDesenvolvedora(Desenvolvedora d) {
		try {
			// Merge to reattach the entity, ensuring it's managed
			Desenvolvedora desenvolvedora = em.merge(d);
			// Remove the managed entity
			em.remove(desenvolvedora);

			// Commit success message
			FacesContext f = FacesContext.getCurrentInstance();
			String mensagem = "Registro excluído com sucesso!";
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, "");
			f.addMessage(null, facesMessage);
		} catch (Exception e) {
			// Handle possible exceptions
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
