package guilherme.gustavo.TrabalhoBd.persistence;

import java.sql.SQLException;

import guilherme.gustavo.TrabalhoBd.model.Aluno;
import guilherme.gustavo.TrabalhoBd.model.Telefone;

public interface ITelefone {
	
	public String iudTelefone(String acao, Aluno a, Telefone t) throws SQLException, ClassNotFoundException;
	
}
