package guilherme.gustavo.TrabalhoBd.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import guilherme.gustavo.TrabalhoBd.model.Aluno;
import guilherme.gustavo.TrabalhoBd.model.Disciplina;
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
				select a.nome as nomeAluno, a.cpf, d.nome, d.codDisciplina, d.horasSemanais, d.diaSemana
				from Matricula m, Disciplina d, Aluno a
				where a.cpf = m.cpf
				      and m.codDisciplina = d.codDisciplina
					  and d.codDisciplina = ?
					  and m.anoSemestre = (dbo.fn_obterAnoSemestre()) 
				""";
		
		PreparedStatement ps = c.prepareStatement(sql);

		ps.setInt(1, disc.getCodigoDisciplina());

		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
			
			Matricula m = new Matricula();
			Aluno a = new Aluno();
			Disciplina d = new Disciplina();
			
			a.setNome(rs.getString("nomeAluno"));
			a.setCpf(rs.getString("cpf"));
			m.setAluno(a);
			
			d.setDisciplina(rs.getString("nome"));
			d.setCodigoDisciplina(rs.getInt("codDisciplina"));
			d.setHorasSemanais(rs.getTime("horasSemanais").toLocalTime());
			d.setDiaSemana(rs.getString("diaSemana"));
			m.setDisciplina(d);
			
			matriculas.add(m);
			
		}
		return matriculas;
	}
	
	
}
