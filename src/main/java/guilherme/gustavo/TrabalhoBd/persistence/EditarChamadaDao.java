package guilherme.gustavo.TrabalhoBd.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import guilherme.gustavo.TrabalhoBd.model.Aluno;
import guilherme.gustavo.TrabalhoBd.model.Disciplina;
import guilherme.gustavo.TrabalhoBd.model.ListaChamada;
import guilherme.gustavo.TrabalhoBd.model.Matricula;

@Repository
public class EditarChamadaDao implements IEditarChamadaDao{

	@Autowired
	private GenericDao gDao;

	@Override
	public List<ListaChamada> buscarAlunos(Disciplina disc, String dataChamada) throws SQLException, ClassNotFoundException {

		List<ListaChamada> listaChamada = new ArrayList<>();
		Connection c = gDao.getConnection();
		
		String sql = """
				select lc.dataChamada, lc.anoSemestre, a.cpf, a.nome, lc.codDisciplina,  d.horasSemanais, 
				d.nome as nomeDisciplina, d.diaSemana, lc.presenca, lc.ausencia, lc.aula1, lc.aula2, lc.aula3,
				lc.aula4, a.ra 
				from ListaChamada lc, Aluno a, Disciplina d
				where lc.codDisciplina = ?
					and lc.anoSemestre = (dbo.fn_obterAnoSemestre())
					and lc.dataChamada = ?
					and lc.cpf = a.cpf
					and d.codDisciplina = lc.codDisciplina
				""";

		PreparedStatement ps = c.prepareStatement(sql);

		ps.setInt(1, disc.getCodigoDisciplina());
		ps.setString(2, dataChamada);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			
			ListaChamada lc = new ListaChamada();
			Matricula m = new Matricula();
			Aluno a = new Aluno();
			Disciplina d = new Disciplina();
			
			lc.setDataChamada(rs.getDate("dataChamada").toLocalDate());
			
			m.setAnoSemestre(rs.getInt("anoSemestre"));
			
			a.setCpf(rs.getString("cpf"));
			a.setNome(rs.getString("nome"));
			a.setRa(rs.getString("ra"));

			d.setCodigoDisciplina(rs.getInt("codDisciplina"));
			d.setHorasSemanais(rs.getTime("horasSemanais").toLocalTime());
			d.setDiaSemana(rs.getString("diaSemana"));
			d.setDisciplina(rs.getString("nomeDisciplina"));
			
			lc.setPresenca(rs.getInt("presenca"));
			lc.setAusencia(rs.getInt("ausencia"));
			lc.setAula1(rs.getString("aula1"));
			lc.setAula2(rs.getString("aula2"));
			lc.setAula3(rs.getString("aula3"));
			lc.setAula4(rs.getString("aula4"));
			
			m.setDisciplina(d);
			m.setAluno(a);
			lc.setMatricula(m);

			listaChamada.add(lc);

		}
		return listaChamada;
	}
	
	@Override
	public void atualiazarChamada(ListaChamada listaChamada) throws SQLException, ClassNotFoundException {
		Connection C = gDao.getConnection();
		String sql = "{CALL sp_atualizaChamada(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
		
		CallableStatement cs = C.prepareCall(sql);
		
		cs.setInt(1, listaChamada.getPresenca());
		cs.setInt(2, listaChamada.getAusencia());
		cs.setString(3, listaChamada.getAula1());
		cs.setString(4, listaChamada.getAula2());
		cs.setString(5, listaChamada.getAula3());
		cs.setString(6, listaChamada.getAula4());
		cs.setInt(7, listaChamada.getMatricula().getDisciplina().getCodigoDisciplina());
		cs.setString(8, listaChamada.getMatricula().getAluno().getCpf());
		cs.setString(9, listaChamada.getDataChamada().toString());
	
		cs.execute();

		cs.close();
		C.close();
	}
}
