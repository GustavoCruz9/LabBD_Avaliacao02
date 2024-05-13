package guilherme.gustavo.TrabalhoBd.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ListaChamada {

	private LocalDate dataChamada;
	private int presenca;
	private int ausencia;
	private Matricula matricula;
	private String aula1;
	private String aula2;
	private String aula3;
	private String aula4;
	
}

