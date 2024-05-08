package guilherme.gustavo.TrabalhoBd.persistence;

import java.sql.SQLException;
import java.util.List;

import guilherme.gustavo.TrabalhoBd.model.Aluno;
import guilherme.gustavo.TrabalhoBd.model.Matricula;

public interface IHistoricoDao {

	public int verificaRa(Aluno a) throws SQLException, ClassNotFoundException;
	public Matricula populaInfosAluno(Matricula m) throws SQLException, ClassNotFoundException;
	public List<Matricula> populaHistorico(Aluno aluno) throws SQLException, ClassNotFoundException;
	
}
