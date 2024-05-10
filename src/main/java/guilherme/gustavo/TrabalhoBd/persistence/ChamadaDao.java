package guilherme.gustavo.TrabalhoBd.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import guilherme.gustavo.TrabalhoBd.model.Aluno;
import guilherme.gustavo.TrabalhoBd.model.Disciplina;
import guilherme.gustavo.TrabalhoBd.model.ListaChamada;
import guilherme.gustavo.TrabalhoBd.model.Matricula;
import guilherme.gustavo.TrabalhoBd.model.Professor;

@Repository
public class ChamadaDao implements IChamadaDao {

	@Autowired
	private GenericDao gDao;

	@Override
	public List<Disciplina> buscaDisciplina(Professor p) throws SQLException, ClassNotFoundException {

		List<Disciplina> disciplinas = new ArrayList<>();
		Connection c = gDao.getConnection();
		String sql = "select codDisciplina, nome from Disciplina where codProfessor = ?";

		PreparedStatement ps = c.prepareStatement(sql);

		ps.setInt(1, p.getCodProfessor());

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {

			Disciplina d = new Disciplina();

			d.setCodigoDisciplina(rs.getInt("codDisciplina"));
			d.setDisciplina(rs.getString("nome"));

			disciplinas.add(d);
		}

		return disciplinas;
	}

	@Override
	public int ValidaProfessor(Professor p) throws SQLException, ClassNotFoundException {

		Connection c = gDao.getConnection();

		String sql = "{CALL sp_validaProfessor(?, ?)}";
		CallableStatement cs = c.prepareCall(sql);

		cs.setInt(1, p.getCodProfessor());
		cs.registerOutParameter(2, Types.BIT);

		cs.execute();

		int saida = cs.getInt(2);

		cs.close();
		c.close();

		return saida;
	}

	@Override
	public List<ListaChamada> buscaChamada(Disciplina d) throws SQLException, ClassNotFoundException {

		List<ListaChamada> ListaChamada = new ArrayList<>();
		Connection c = gDao.getConnection();
		String sql = """
				select distinct (l.dataChamada), d.nome, l.anoSemestre, l.cpf, l.codDisciplina
				from ListaChamada l , Matricula m, Disciplina d
				where l.anoSemestre = m.anoSemestre
					  and l.codDisciplina = m.codDisciplina
					  and d.codDisciplina = m.codDisciplina
				      and l.codDisciplina = ? 
					  and l.anoSemestre = (dbo.fn_obterAnoSemestre()) 
				group by l.dataChamada, d.nome, l.anoSemestre, l.cpf, l.codDisciplina
				""";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, d.getCodigoDisciplina());
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			
			Aluno a = new Aluno();
			ListaChamada lc = new ListaChamada();
			Matricula m = new Matricula();
			d = new Disciplina();
			
			a.setCpf(rs.getString("cpf"));
			m.setAluno(a);
			
			m.setAnoSemestre(rs.getInt("anoSemestre"));
			d.setCodigoDisciplina(rs.getInt("codDisciplina"));
			d.setDisciplina(rs.getString("nome"));
			m.setDisciplina(d);
			
			lc.setMatricula(m);
			
			lc.setDataChamada(rs.getDate("dataChamada").toLocalDate());
			
			ListaChamada.add(lc);
			
		}
		
		return ListaChamada;
	}

}
