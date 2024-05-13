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

@Repository
public class CadastrarChamadaDao implements ICadastrarChamadaDao{
	
	@Autowired
	private GenericDao gDao;

	@Override
	public List<Matricula> buscarAlunos(Disciplina disc) throws SQLException, ClassNotFoundException {
		
		List<Matricula> matriculas = new ArrayList<>();
		Connection c = gDao.getConnection();
		String sql = """
				select a.nome as nomeAluno, a.ra, a.cpf, d.nome, d.codDisciplina, d.horasSemanais, d.diaSemana, m.anoSemestre
				from Matricula m, Disciplina d, Aluno a
				where a.cpf = m.cpf
				      and m.codDisciplina = d.codDisciplina
					  and d.codDisciplina = ?
					  and m.anoSemestre = (dbo.fn_obterAnoSemestre())
					  and m.statusMatricula <> 'Aprovado' and
					  m.statusMatricula <> 'Dispensado'
				""";
		
		PreparedStatement ps = c.prepareStatement(sql);

		ps.setInt(1, disc.getCodigoDisciplina());

		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
			
			Matricula m = new Matricula();
			Aluno a = new Aluno();
			Disciplina d = new Disciplina();
			
			a.setNome(rs.getString("nomeAluno"));
			a.setRa(rs.getString("ra"));
			a.setCpf(rs.getString("cpf"));
			m.setAluno(a);
			
			d.setDisciplina(rs.getString("nome"));
			d.setCodigoDisciplina(rs.getInt("codDisciplina"));
			d.setHorasSemanais(rs.getTime("horasSemanais").toLocalTime());
			d.setDiaSemana(rs.getString("diaSemana"));
			m.setDisciplina(d);
			
			m.setAnoSemestre(rs.getInt("anoSemestre"));
			
			matriculas.add(m);
			
		}
		return matriculas;
	}

	@Override
	public void cadastrarChamada(ListaChamada listaChamada) throws SQLException, ClassNotFoundException {
		Connection C = gDao.getConnection();
		String sql = "{CALL sp_cadastraChamada (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
		
		CallableStatement cs = C.prepareCall(sql);
		
		cs.setString(1, listaChamada.getDataChamada().toString());
		cs.setInt(2, listaChamada.getMatricula().getAnoSemestre());
		cs.setString(3, listaChamada.getMatricula().getAluno().getCpf());
		cs.setInt(4, listaChamada.getMatricula().getDisciplina().getCodigoDisciplina());
		cs.setInt(5, listaChamada.getPresenca());
		cs.setInt(6, listaChamada.getAusencia());
		cs.setString(7, listaChamada.getAula1());
		cs.setString(8, listaChamada.getAula2());
		cs.setString(9, listaChamada.getAula3());
		cs.setString(10, listaChamada.getAula4());
		
		cs.execute();

		cs.close();
		C.close();

	}
	
	
}
