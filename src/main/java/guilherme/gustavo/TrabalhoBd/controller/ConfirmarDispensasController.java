package guilherme.gustavo.TrabalhoBd.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import guilherme.gustavo.TrabalhoBd.model.Dispensa;
import guilherme.gustavo.TrabalhoBd.persistence.ConfirmarDispensasDao;

@Controller
public class ConfirmarDispensasController {

	@Autowired
	ConfirmarDispensasDao cdDao;

	@RequestMapping(name = "confirmarDispensa", value = "/confirmarDispensa", method = RequestMethod.GET)
	public ModelAndView confirmarDispensaGet(@RequestParam Map<String, String> param, ModelMap model) {

		String cpf = param.get("cpf");

		String saida = "";
		String erro = "";

		List<Dispensa> dispensas = new ArrayList<>();

		if (cpf == null) {
			try {
				dispensas = listarDispensas();
				if (dispensas.isEmpty()) {
					erro = "Nao existe solicitacoes de dispensa no momento";
				}
			} catch (SQLException | ClassNotFoundException e) {
				erro = e.getMessage();
			} finally {
				model.addAttribute("saida", saida);
				model.addAttribute("erro", erro);
				model.addAttribute("dispensas", dispensas);
				model.addAttribute("cpf", cpf);
			}
		}

		return new ModelAndView("confirmarDispensa");
	}

	@RequestMapping(name = "confirmarDispensa", value = "/confirmarDispensa", method = RequestMethod.POST)
	public ModelAndView confirmarDispensaPost(@RequestParam Map<String, String> param, ModelMap model) {
		
		String cpf = param.get("cpf");
		String codDisciplina = param.get("codDisciplina");
		String ra = param.get("ra");
		String cmd = param.get("botao");
		
		String saida = "";
		String erro = "";	
		List<Dispensa> dispensas = new ArrayList<>();
		
	
		if(cmd.contains("Pesquisa Cpf")) {
			if(cpf.trim().isEmpty()) {
				erro = "Por favor, informe o Cpf";
			}
		}
		
		if (!erro.isEmpty()) {
			try {
				dispensas = listarDispensas();
				if(dispensas.isEmpty()) {
					erro = "Nao existe solicitacoes de dispensa no momento";
				}
			} catch (SQLException | ClassNotFoundException e) {
				erro = e.getMessage();
			} finally {
				model.addAttribute("erro", erro);
				model.addAttribute("dispensas", dispensas);

			}
			
			return new ModelAndView("confirmarDispensa");
		}
			
		
		Dispensa disp = new Dispensa();
		Disciplina d = new Disciplina();
		Aluno a = new Aluno();
		

		if(cmd.contains("Aceitar") || cmd.contains("Negar")) {
			
			a.setRa(ra);
			disp.setAluno(a);
			
			d.setCodigoDisciplina(Integer.parseInt(codDisciplina));
			disp.setDisciplina(d);
			
			if(cmd.contains("Aceitar")) {
				disp.setStatusDispensa("Deferido");
			}else {
				disp.setStatusDispensa("Indeferido");
			}
			
		}
		
		if(cmd.contains("Pesquisa Cpf")) {
			a.setCpf(cpf);
		}
		
		try {
			
			if (a.getCpf() != null) {
				if (a.getCpf().length() == 11) {
					if(verificaCpf(a) == 1) {
						if(verificaCpfSeExiste(a) == 0) {
							dispensas = listarDispensasComCpf(a);
							if(dispensas.isEmpty()) {
								erro = "Nao existe solicitacoes de dispensa no momento";
							}
						}else {
							erro = "Cpf nao cadastrado";
						}
					}
				}else {
					erro = "Tamanho de Cpf invalido";
				}
			}
			
			if(cmd.contains("Aceitar") || cmd.contains("Negar")) {
				saida = cofirmarDispensa(disp);
				dispensas = listarDispensas();
				if(dispensas.isEmpty()) {
					erro = "Nao existe solicitacoes de dispensa no momento";
				}
			}

		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			model.addAttribute("dispensas", dispensas);
		}
		
		
		return new ModelAndView("confirmarDispensa");
	}

	private int verificaCpfSeExiste(Aluno a) throws ClassNotFoundException, SQLException {
		return cdDao.verificaCpfSeExsite(a);
	}
	
	private int verificaCpf(Aluno a) throws ClassNotFoundException, SQLException {
		return cdDao.verificaCpf(a);
	}

	private List<Dispensa> listarDispensasComCpf(Aluno a) throws ClassNotFoundException, SQLException {
		List<Dispensa> dispensas = new ArrayList<>();
		dispensas = cdDao.listarDispensasComCpf(a);
		return dispensas;
	}

	private String cofirmarDispensa(Dispensa disp) throws ClassNotFoundException, SQLException {
		String saida = cdDao.AtualizarDispensa(disp);
		return saida;
	}

	private List<Dispensa> listarDispensas() throws SQLException, ClassNotFoundException {
		List<Dispensa> dispensas = new ArrayList<>();
		dispensas = cdDao.listarDispensas();
		return dispensas;
	}

}