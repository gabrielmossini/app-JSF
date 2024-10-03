package jakarta.fema;

import java.util.ArrayList;
import java.util.List;

import jakarta.beans.Desenvolvedora;

public class DesenvolvedoraDao {
	
	private static List<Desenvolvedora> desenvolvedoras = 
				new ArrayList<Desenvolvedora>();
	
	public DesenvolvedoraDao() {
		desenvolvedoras.add(new Desenvolvedora(1, "Rockstar Games"));
		desenvolvedoras.add(new Desenvolvedora(2, "Jagex"));
		desenvolvedoras.add(new Desenvolvedora(3, "FromSoftware"));
	}
	
	public List<Desenvolvedora> recuperarTodos(){
		return desenvolvedoras;
	}
}
