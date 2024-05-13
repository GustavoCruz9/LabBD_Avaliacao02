package guilherme.gustavo.TrabalhoBd.persistence;

import java.sql.SQLException;
import java.util.List;

import guilherme.gustavo.TrabalhoBd.model.Disciplina;
import guilherme.gustavo.TrabalhoBd.model.ListaChamada;
import guilherme.gustavo.TrabalhoBd.model.Matricula;

public interface ICadastrarChamadaDao {
	
	public List<Matricula> buscarAlunos(Disciplina d) throws SQLException, ClassNotFoundException;
	public void cadastrarChamada(ListaChamada listaChamada) throws SQLException, ClassNotFoundException;
}
