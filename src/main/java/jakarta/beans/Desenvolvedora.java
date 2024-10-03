package jakarta.beans;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Desenvolvedora {
	
	@Id
	private Integer codigo;
	private String nome;
	
	public Desenvolvedora() {
		
	}
	
	public Desenvolvedora(Integer codigo, String nome) {
		super();
		this.codigo = codigo;
		this.nome = nome;
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

	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Desenvolvedora other = (Desenvolvedora) obj;
		return Objects.equals(codigo, other.codigo);
	}

	@Override
	public String toString() {
		return "Atleta [codigo=" + codigo + ", nome=" + nome + "]";
	}


}
