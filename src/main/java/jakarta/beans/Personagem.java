package jakarta.beans;

public class Personagem {
	
	private Integer codigo;
	
	private String nome;
	
	private Jogo jogo;
	
	public Personagem() {
		
	}
	
	public Personagem(Integer codigo, String nome) {
		super();
		this.codigo = codigo;
		this.nome = nome;
	}

	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
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
