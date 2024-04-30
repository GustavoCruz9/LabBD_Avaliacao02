package guilherme.gustavo.TrabalhoBd.persistence;

import java.sql.SQLException;
import java.util.List;

import guilherme.gustavo.TrabalhoBd.model.Aluno;
import guilherme.gustavo.TrabalhoBd.model.Matricula;

public interface IMatricula <T> {
	
	public String iMatricula(Matricula m) throws SQLException, ClassNotFoundException;
	public List<T> listarDisciplinas(Aluno a) throws SQLException, ClassNotFoundException;
	public List<T> listarMatriculas(Aluno a) throws SQLException, ClassNotFoundException;
	public int verificaRa(Aluno a) throws SQLException, ClassNotFoundException;
	
}
