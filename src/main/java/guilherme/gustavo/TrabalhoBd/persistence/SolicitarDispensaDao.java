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
import guilherme.gustavo.TrabalhoBd.model.Dispensa;

@Repository
public class SolicitarDispensaDao implements ISolicitarDispensaDao {

	@Autowired
	private GenericDao gDao;

	@Override
	public String cadastraDispensa(Dispensa d) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_iDispensa (?, ?, ?, ?)}";
		CallableStatement cs = c.prepareCall(sql);


		cs.setString(1, d.getAluno().getCpf());
		cs.setInt(2, d.getDisciplina().getCodigoDisciplina());
		cs.setString(3, d.getInstituicao());
		cs.registerOutParameter(4, Types.VARCHAR);

		cs.execute();

		String saida = cs.getString(4);
		cs.close();
		c.close();

		return saida;
	}

	@Override
	public int verificaCpf(Aluno a) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_validaCpfDuplicado (?, ?)}";
		CallableStatement cs = c.prepareCall(sql);

		cs.setString(1, a.getCpf());
		cs.registerOutParameter(2, Types.BIT);

		cs.execute();

		int saida = cs.getInt(2);

		cs.close();
		c.close();

		return saida;
	}

	@Override
	public List<Disciplina> listarDisciplinas(Aluno aluno) throws SQLException, ClassNotFoundException {
		List<Disciplina> disciplinas = new ArrayList<>();
		Connection c = gDao.getConnection();
		String sql = """
				select d.nome, d.codDisciplina  from Disciplina d left outer join Dispensa disp on d.codDisciplina = disp.codDisciplina
				where d.codCurso = (select codCurso from Aluno where cpf = ?) and disp.cpf is null
				""";
		PreparedStatement ps = c.prepareStatement(sql);

		ps.setString(1, aluno.getCpf());

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
	public List<Dispensa> listarDispensas(String cpf) throws SQLException, ClassNotFoundException {
		List<Dispensa> dispensas = new ArrayList<>();
		Connection c = gDao.getConnection();
		String sql = """
				select codDisciplina, dataDispensa, instituicao, statusDispensa
				from Dispensa
				where cpf = ?
				""";
		PreparedStatement ps = c.prepareStatement(sql);

		ps.setString(1, cpf);

		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			Dispensa d = new Dispensa();
			Disciplina disc = new Disciplina();
			
			disc.setCodigoDisciplina(rs.getInt("codDisciplina"));
			d.setDisciplina(disc);
			d.setDataSolicitacao(rs.getDate("dataDispensa").toLocalDate());
			d.setInstituicao(rs.getString("instituicao"));
			d.setStatusDispensa(rs.getString("statusDispensa"));
			
			dispensas.add(d);
		}

		return dispensas;
	}
}
