package guilherme.gustavo.TrabalhoBd.persistence;

import java.sql.SQLException;

import guilherme.gustavo.TrabalhoBd.model.Aluno;

public interface IAluno {
	
	public String iuAluno(String acao, Aluno a) throws SQLException, ClassNotFoundException;
	public int verificaCpf(Aluno a) throws SQLException, ClassNotFoundException;
	
}