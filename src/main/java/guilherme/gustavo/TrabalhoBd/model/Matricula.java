package guilherme.gustavo.TrabalhoBd.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Matricula {

	private Disciplina disciplina;
	private Aluno aluno;
	private String status;
	private Double nota;
	private LocalDate dataMatricula;
	private int qtdFaltas;
	private int anoSemestre;
	
}
