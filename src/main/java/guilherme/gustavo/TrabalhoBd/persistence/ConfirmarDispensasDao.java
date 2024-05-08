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
public class ConfirmarDispensasDao implements IConfirmarDispensasDao {

	@Autowired
	private GenericDao gDao;

	@Override
	public List<Dispensa> listarDispensas() throws SQLException, ClassNotFoundException {

		List<Dispensa> dispensas = new ArrayList<>();
		Connection c = gDao.getConnection();
		String sql = """
				select a.ra, a.nome, disp.dataDispensa, d.nome as nomeDisciplina, disp.instituicao, d.codDisciplina
				from Dispensa disp, Aluno a, Disciplina d
				where disp.codDisciplina = d.codDisciplina
				and disp.cpf = a.cpf
				and disp.statusDispensa like 'em analise'
				""";

		PreparedStatement ps = c.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Aluno a = new Aluno();
			Dispensa d = new Dispensa();
			Disciplina disc = new Disciplina();
			
			a.setRa(rs.getString("ra"));
			a.setNome(rs.getString("nome"));
			d.setAluno(a);

			disc.setDisciplina(rs.getString("nomeDisciplina"));
			disc.setCodigoDisciplina(rs.getInt("codDisciplina"));
			d.setDisciplina(disc);
			
			d.setDataSolicitacao(rs.getDate("dataDispensa").toLocalDate());
			d.setInstituicao(rs.getString("instituicao"));

			dispensas.add(d);
		}

		return dispensas;
	}

	@Override
	public List<Dispensa> listarDispensasComCpf(Aluno a) throws SQLException, ClassNotFoundException {
		
		List<Dispensa> dispensas = new ArrayList<>();
		Connection c = gDao.getConnection();
		String sql = """
				select a.ra, a.nome, disp.dataDispensa, d.nome as nomeDisciplina, disp.instituicao,  d.codDisciplina
				from Dispensa disp, Aluno a, Disciplina d
				where disp.codDisciplina = d.codDisciplina
				and disp.cpf = a.cpf
				and disp.statusDispensa like 'em analise'
				and disp.cpf = ?
				""";
	
		PreparedStatement ps = c.prepareStatement(sql);
		
		ps.setString(1, a.getCpf());
		
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Dispensa d = new Dispensa();
			Disciplina disc = new Disciplina();
			a = new Aluno();
			
			a.setRa(rs.getString("ra"));
			a.setNome(rs.getString("nome"));
			d.setAluno(a);

			disc.setDisciplina(rs.getString("nomeDisciplina"));
			disc.setCodigoDisciplina(rs.getInt("codDisciplina"));
			d.setDisciplina(disc);
			
			d.setDataSolicitacao(rs.getDate("dataDispensa").toLocalDate());
			d.setInstituicao(rs.getString("instituicao"));

			dispensas.add(d);
		}
				
		return dispensas;
	}

	@Override
	public String AtualizarDispensa(Dispensa disp) throws SQLException, ClassNotFoundException {
		
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_atualizaDispensa (?, ?, ?, ?)}";
		CallableStatement cs = c.prepareCall(sql);
		
		cs.setString(1, disp.getAluno().getRa());
		cs.setInt(2, disp.getDisciplina().getCodigoDisciplina());
		cs.setString(3, disp.getStatusDispensa());
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
		String sql = "{CALL sp_consultaCpf(?, ?)}";
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
	public int verificaCpfSeExsite(Aluno a) throws SQLException, ClassNotFoundException {
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

}
