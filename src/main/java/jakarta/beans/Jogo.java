package jakarta.beans;

public class Jogo {
	
	private Integer codigo;
	
	private String nome;
	
	private Desenvolvedora desenvolvedora;
	
	public Jogo() {
		
	}
	
	public Jogo(Integer codigo, String nome) {
		super();
		this.codigo = codigo;
		this.nome = nome;
	}
	
	public Desenvolvedora getDesenvolvedora() {
		return desenvolvedora;
	}

	public void setDesenvolvedora(Desenvolvedora desenvolvedora) {
		this.desenvolvedora = desenvolvedora;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
