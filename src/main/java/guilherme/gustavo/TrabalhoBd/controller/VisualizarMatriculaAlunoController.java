package guilherme.gustavo.TrabalhoBd.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import guilherme.gustavo.TrabalhoBd.model.Aluno;
import guilherme.gustavo.TrabalhoBd.model.Disciplina;
import guilherme.gustavo.TrabalhoBd.model.Matricula;
import guilherme.gustavo.TrabalhoBd.persistence.MatriculaDao;

@Controller
public class VisualizarMatriculaAlunoController {

	@Autowired
	private MatriculaDao mDao;

	@RequestMapping(name = "visualizarMatriculaAluno", value = "/visualizarMatriculaAluno", method = RequestMethod.GET)
	public ModelAndView vizualizarMatriculaAlunoGet(ModelMap model) {
		return new ModelAndView("visualizarMatriculaAluno");
	}

	@RequestMapping(name = "visualizarMatriculaAluno", value = "/visualizarMatriculaAluno", method = RequestMethod.POST)
	public ModelAndView vizualizarMatriculaAlunoPost(@RequestParam Map<String, String> param, ModelMap model) {
		String cmd = param.get("botao");
		String pesquisaRa = param.get("pesquisaRa");

		String saida = "";
		String erro = "";

//		List<Matricula> disciplinas = new ArrayList<>();
		List<Matricula> matriculas = new ArrayList<>();

		Aluno a = new Aluno();
		Matricula m = new Matricula();
		Disciplina d = new Disciplina();

		a.setRa(pesquisaRa);

		String aux = "";

		if (!cmd.contains("pesquisa RA")) {

			if (cmd.contains(".")) {
				aux = cmd.substring(0, cmd.length() - 1);
				d.setCodigoDisciplina(Integer.parseInt(aux));
			} else {
				d.setCodigoDisciplina(Integer.parseInt(cmd));
			}

			m.setAluno(a);
			m.setDisciplina(d);
		}

		try {
			if (cmd.contains("pesquisa RA")) {
				if (a.getRa().length() == 9) {
					if (verificaRa(a) == 1) {
						matriculas = listarMatriculas(a);
						if (matriculas.isEmpty()) {
							erro = "O aluno do Ra " + pesquisaRa + " nao possui matriculas";
						}
					} else {
						erro = "RA invalido";
					}
				} else {
					erro = "Tamanho de RA incorreto";
				}

			} // else {
//				saida = cadastrarMatricula(m);
//				disciplinas = listarDisciplinas(a);
//			}

		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();

//			try {
//				disciplinas = listarDisciplinas(a);
//			} catch (SQLException | ClassNotFoundException e1) {
//				erro = e1.getMessage();
//			}

		} finally {
			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
//			model.addAttribute("disciplinas", disciplinas);
			model.addAttribute("matriculas", matriculas);
		}
			return new ModelAndView("visualizarMatriculaAluno");
		
	}

	private int verificaRa(Aluno a) throws SQLException, ClassNotFoundException {
		return mDao.verificaRa(a);
	}

	private List<Matricula> listarMatriculas(Aluno a) throws SQLException, ClassNotFoundException {
		List<Matricula> matriculas = new ArrayList<>();
		matriculas = mDao.listarMatriculas(a);
		return matriculas;
	}
}
