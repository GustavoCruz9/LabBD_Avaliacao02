package guilherme.gustavo.TrabalhoBd.persistence;

import java.sql.SQLException;
import java.util.List;

import guilherme.gustavo.TrabalhoBd.model.Aluno;
import guilherme.gustavo.TrabalhoBd.model.Dispensa;

public interface IConfirmarDispensasDao {
	public List<Dispensa> listarDispensas() throws SQLException, ClassNotFoundException;
	public List<Dispensa> listarDispensasComCpf(Aluno a) throws SQLException, ClassNotFoundException;
	public String AtualizarDispensa(Dispensa disp) throws SQLException, ClassNotFoundException;
	public int verificaCpf(Aluno a) throws SQLException, ClassNotFoundException;
	public int verificaCpfSeExsite(Aluno a) throws SQLException, ClassNotFoundException;
}
