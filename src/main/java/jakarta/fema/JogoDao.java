package jakarta.fema;

import java.util.ArrayList;
import java.util.List;

import jakarta.beans.Jogo;

public class JogoDao {
	
	private static List<Jogo> jogos = 
				new ArrayList<Jogo>();
	
	public JogoDao() {
		jogos.add(new Jogo(1, "GTA IV"));
		jogos.add(new Jogo(2, "RuneScape"));
		jogos.add(new Jogo(3, "Elden Ring"));
	}
	
	public List<Jogo> recuperarTodos(){
		return jogos;
	}
}
