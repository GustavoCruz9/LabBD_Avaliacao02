package guilherme.gustavo.TrabalhoBd.persistence;

import java.sql.SQLException;
import java.util.List;

import guilherme.gustavo.TrabalhoBd.model.Disciplina;
import guilherme.gustavo.TrabalhoBd.model.Matricula;

public interface ICadastrarChamadaDao {
	
	public List<Matricula> buscarAlunos(Disciplina d) throws SQLException, ClassNotFoundException;
}
