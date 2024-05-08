package guilherme.gustavo.TrabalhoBd.persistence;

import java.sql.SQLException;
import java.util.List;

import guilherme.gustavo.TrabalhoBd.model.Aluno;
import guilherme.gustavo.TrabalhoBd.model.Disciplina;
import guilherme.gustavo.TrabalhoBd.model.Dispensa;

public interface ISolicitarDispensaDao {
	
	public String cadastraDispensa(Dispensa d) throws SQLException, ClassNotFoundException;
	public int verificaCpf(Aluno a) throws SQLException, ClassNotFoundException;
	public List<Disciplina> listarDisciplinas(Aluno aluno) throws SQLException, ClassNotFoundException;
	public List<Dispensa> listarDispensas(String cpf) throws SQLException, ClassNotFoundException;

}
