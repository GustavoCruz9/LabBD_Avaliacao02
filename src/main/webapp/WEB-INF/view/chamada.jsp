<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sistema AGIS - Professor</title>
<link rel="stylesheet" type="text/css"
	href='<c:url value = "./resources/css/styleProfessor.css"/>'>
</head>

<body>

	<script src="./resources/js/headerProfessor.js"></script>

	<main>

		<form method="post" action="chamada" class="formChamada">
			<div class="cabecalhoChamada">
				<div class="pesquisaProfessor">
					<input type="text" pattern="[0-9]*" name="codigoProfessor"
						id="codigoProfessor" placeholder="Codigo Professor"
						value="${param.codigoProfessor}"> <input type="submit"
						value="Buscar Disciplinas" id="botao" name="botao">
				</div>

				<div class="disciplinas">
					<select name="codDisciplina" id="codDisciplina">
						<option value="" disabled selected>Selecione a Disciplina</option>
						<c:forEach var="d" items="${disciplinas}">
							<option value="${d.codigoDisciplina}">
								<c:out value="${d.disciplina}" />
							</option>
						</c:forEach>
					</select> <input type="submit" value="Buscar Chamadas" id="botao"
						name="botao">
				</div>

				<input type="submit" value="Nova Chamada" id="botao" name="botao">
			</div>

			<div align="center">
				<c:if test="${not empty erro }">
					<h2>
						<b><c:out value="${erro }" /></b>
					</h2>
				</c:if>
			</div>

			<div align="center">
				<c:if test="${not empty saida }">
					<h3>
						<b><c:out value="${saida }" /></b>
					</h3>
				</c:if>
			</div>


			<div class="conteudoChamada">

				<h2><c:out value="${ListaChamada[0].matricula.disciplina.disciplina}" /></h2>

				<table>
					<thead>
						<tr>
							<th colspan="3">Data</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="l" items="${ListaChamada}">
							<tr>
								<td name="codChamada" id="codChamada" value="${l.codChamada}"><c:out
										value="${l.dataChamada}" /></td>
								<td><input type="submit" value="Visualizar" id="botao"
									name="botao"></td>
								<td><input type="submit" value="Alterar" id="botao"
									name="botao"></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</form>

	</main>

</body>

</html>