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
public class MatriculaAlunoController {
	
	@Autowired
	private MatriculaDao mDao;

	@RequestMapping(name = "matriculaAluno", value = "/matriculaAluno", method = RequestMethod.GET)
	public ModelAndView matriculaAlunoGet(@RequestParam Map<String, String> param, ModelMap model) {
		String cmd = param.get("botao");
		String pesquisaRa = param.get("pesquisaRa");
		Aluno a = new Aluno();

		a.setRa(pesquisaRa);
		List<Matricula> disciplinas = new ArrayList<>();

		a.setRa(pesquisaRa);

		String saida = "";
		String erro = "";

		try {
			if (a.getRa().length() == 9) {
				if (verificaRa(a) == 1) {
					disciplinas = listarDisciplinas(a);
					if (disciplinas.isEmpty()) {
						erro = "Voce concluiu todas as disciplinas de seu curso";
					}
				} else {
					erro = "RA inválido";
				}
			} else {
				erro = "Tamanho de RA incorreto";
			}

		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			model.addAttribute("disciplinas", disciplinas);
			model.addAttribute("pesquisaRa", pesquisaRa);

//			if (cmd.contains("pesquisa Ra")) {
//				RequestDispatcher rd = request.getRequestDispatcher("menuAluno.jsp");
//				rd.forward(request, response);
//			} else {
//				RequestDispatcher rd = request.getRequestDispatcher("cadastrarMatricula.jsp");
//				rd.forward(request, response);
//			}

		}
		
		return new ModelAndView("matriculaAluno");
	}
	
	@RequestMapping(name = "matriculaAluno", value = "/matriculaAluno", method = RequestMethod.POST)
	public ModelAndView matriculaAlunoPost(@RequestParam Map<String, String> param, ModelMap model) {
		String cmd = param.get("botao");
		String pesquisaRa = param.get("pesquisaRa");

		String saida = "";
		String erro = "";

		List<Matricula> disciplinas = new ArrayList<>();
//		List<Matricula> matriculas = new ArrayList<>();

		Aluno a = new Aluno();
		Matricula m = new Matricula();
		Disciplina d = new Disciplina();

		a.setRa(pesquisaRa);

		String aux = "";

		if (!cmd.contains("pesquisa Ra")) {

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
//			if (cmd.contains("Pesquisa RA")) {
//				if (a.getRa().length() == 9) {
//					if (verificaRa(a) == 1) {
//						matriculas = listarMatriculas(a);
//						if (matriculas.isEmpty()) {
//							erro = "O aluno do Ra " + pesquisaRa + " não possui matriculas";
//						}
//					} else {
//						erro = "RA inválido";
//					}
//				} else {
//					erro = "Tamanho de RA incorreto";
//				}
//
//			} else {
			
			saida = cadastrarMatricula(m);
			disciplinas = listarDisciplinas(a);
			
			//}

		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();

			try {
				disciplinas = listarDisciplinas(a);
			} catch (SQLException | ClassNotFoundException e1) {
				erro = e1.getMessage();
			}

		} finally {
			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			model.addAttribute("disciplinas", disciplinas);
//			model.addAttribute("matriculas", matriculas);

//			if (cmd.contains("Pesquisa Ra")) {
//				RequestDispatcher rd = request.getRequestDispatcher("vizualizarMatricula.jsp");
//				rd.forward(request, response);
//			}
//			if (cmd.contains("Pesquisa RA")
//					|| (!cmd.contains(".")) && !cmd.contains("Pesquisa Ra") && !cmd.contains("pesquisa RA") && !cmd.contains("pesquisa Ra")) {
//				RequestDispatcher rd = request.getRequestDispatcher("cadastrarMatricula.jsp");
//				rd.forward(request, response);
//			}
//			if (cmd.contains("pesquisa RA")) {
//				RequestDispatcher rd = request.getRequestDispatcher("vizualizarMatriculasAluno.jsp");
//				rd.forward(request, response);
//			}
//			if (cmd.contains(".") || cmd.contains("pesquisa Ra")) {
//				RequestDispatcher rd = request.getRequestDispatcher("menuAluno.jsp");
//				rd.forward(request, response);
//			}

		}
		
		return new ModelAndView("matriculaAluno");
	}
	
	private int verificaRa(Aluno a) throws SQLException, ClassNotFoundException {
		return mDao.verificaRa(a);
	}

	private String cadastrarMatricula(Matricula m) throws SQLException, ClassNotFoundException {
		return mDao.iMatricula(m);
	}

//	private List<Matricula> listarMatriculas(Aluno a) throws SQLException, ClassNotFoundException {
//		List<Matricula> matriculas = new ArrayList<>();
//		matriculas = mDao.listarMatriculas(a);
//		return matriculas;
//	}

	private List<Matricula> listarDisciplinas(Aluno a) throws SQLException, ClassNotFoundException {
		List<Matricula> disciplinas = new ArrayList<>();
		disciplinas = mDao.listarDisciplinas(a);
		return disciplinas;
	}
}
