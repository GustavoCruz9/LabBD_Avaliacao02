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
import guilherme.gustavo.TrabalhoBd.model.Curso;
import guilherme.gustavo.TrabalhoBd.model.Disciplina;
import guilherme.gustavo.TrabalhoBd.model.ListaChamada;
import guilherme.gustavo.TrabalhoBd.model.Matricula;
import guilherme.gustavo.TrabalhoBd.model.Professor;

@Repository
public class HistoricoDao implements IHistoricoDao {

	@Autowired
	private GenericDao gDao;

	@Override
	public int verificaRa(Aluno a) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_validaRa (?, ?)}";
		CallableStatement cs = c.prepareCall(sql);

		cs.setString(1, a.getRa());
		cs.registerOutParameter(2, Types.BIT);

		cs.execute();

		int saida = cs.getInt(2);

		cs.close();
		c.close();

		return saida;
	}

	@Override
	public Matricula populaInfosAluno(Matricula m) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = """
				select ra, nome, nomeCurso, dataPrimeiraMatricula, pontuacaoVestibular, posicaoVestibular
				from dbo.fn_historico(?)
				""";
		PreparedStatement ps = c.prepareStatement(sql);

		ps.setString(1, m.getAluno().getRa());

		ResultSet rs = ps.executeQuery();

		Aluno aluno = new Aluno();
		Curso curso = new Curso();
		Matricula matricula = new Matricula();
		
		if (rs.next()) { 
	        aluno.setRa(rs.getString("ra"));
	        aluno.setNome(rs.getString("nome"));
	        curso.setNome(rs.getString("nomeCurso"));
	        matricula.setDataMatricula(rs.getDate("dataPrimeiraMatricula").toLocalDate());
	        aluno.setPontuacaoVestibular(rs.getInt("pontuacaoVestibular"));
	        aluno.setPosicaoVestibular(rs.getInt("posicaoVestibular"));
	        aluno.setCurso(curso);
	        matricula.setAluno(aluno);
	    }

		return matricula;
	}

	@Override
	public List<Matricula> populaHistorico(Aluno aluno) throws SQLException, ClassNotFoundException {
		List<Matricula> matriculas = new ArrayList<>();
		Connection c = gDao.getConnection();
		String sql = """
				select codDisciplina, nomeDisciplina, nomeProfessor, notaFinal, qtdFaltas
				from fn_matriculaAprovada(?)
				""";
		PreparedStatement ps = c.prepareStatement(sql);

		ps.setString(1, aluno.getRa());

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Matricula m = new Matricula();
			Disciplina d = new Disciplina();
			Professor p = new Professor();
			
			p.setNome(rs.getString("nomeProfessor"));
			
			d.setCodigoDisciplina(rs.getInt("codDisciplina"));
			d.setDisciplina(rs.getString("nomeDisciplina"));
			d.setProfessor(p);
			
			m.setDisciplina(d);
			m.setNota(rs.getString("notaFinal"));
			m.setQtdFaltas(rs.getInt("qtdFaltas"));
			
			matriculas.add(m);
		}

		return matriculas;
	}

}
