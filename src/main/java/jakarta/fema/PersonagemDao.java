package jakarta.fema;

import java.util.ArrayList;
import java.util.List;

import jakarta.beans.Personagem;

public class PersonagemDao {
	
	private static List<Personagem> personagens = 
				new ArrayList<Personagem>();
	
	public PersonagemDao() {
		personagens.add(new Personagem(1, "Lucia"));
		personagens.add(new Personagem(2, "Wise Old Man"));
		personagens.add(new Personagem(3, "Malenia"));
	}
	
	public List<Personagem> recuperarTodos(){
		return personagens;
	}
}
