package guilherme.gustavo.TrabalhoBd.persistence;

import java.sql.SQLException;
import java.util.List;

import guilherme.gustavo.TrabalhoBd.model.Disciplina;
import guilherme.gustavo.TrabalhoBd.model.ListaChamada;

public interface IEditarChamadaDao {

	public List<ListaChamada> buscarAlunos(Disciplina disc, String dataChamada) throws SQLException, ClassNotFoundException;
	public void atualiazarChamada(ListaChamada listaChamada) throws SQLException, ClassNotFoundException;
	
}
