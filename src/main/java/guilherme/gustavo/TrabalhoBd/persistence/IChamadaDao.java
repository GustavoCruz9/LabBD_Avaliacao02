package guilherme.gustavo.TrabalhoBd.persistence;

import java.sql.SQLException;
import java.util.List;

import guilherme.gustavo.TrabalhoBd.model.Disciplina;
import guilherme.gustavo.TrabalhoBd.model.ListaChamada;
import guilherme.gustavo.TrabalhoBd.model.Professor;

public interface IChamadaDao {
	public int ValidaProfessor (Professor p) throws SQLException, ClassNotFoundException; 
	public List<Disciplina> buscaDisciplina (Professor p) throws SQLException, ClassNotFoundException;
	public List<ListaChamada> buscaChamada (Disciplina d) throws SQLException, ClassNotFoundException;
}
