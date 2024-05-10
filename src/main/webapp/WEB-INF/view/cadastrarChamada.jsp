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

		<form method="post" action="cadastrarChamada"
			class="formCadastrarChamada">

			<input type="hidden" name="codDisciplina"
				value="${param.codDisciplina}">

			<div class="dataContainer">
				<label>Data da Chamada:</label> <input type="date" id="dataChamada"
					name="dataChamada" value="${param.dataChamada}"> <input type="submit" id="botao"
					name="botao" value="Listar Alunos">
			</div>

			<h1>
				<c:out value="${matriculas[0].disciplina.disciplina}" />
			</h1>

			<table>
				<tbody>
					<c:forEach var="m" items="${matriculas}">
						<tr>
							<td>${m.aluno.nome}</td>
							<c:choose>
								<c:when test="${horasSemanais eq '01:40'}">
									<td><input type="checkbox" id="checkbox" name="checkbox" checked>
									</td>
									<td><input type="checkbox" id="checkbox" name="checkbox" checked>
									</td>
								</c:when>
								<c:otherwise>
									<td><input type="checkbox" id="checkbox" name="checkbox" checked>
									</td>
									<td><input type="checkbox" id="checkbox" name="checkbox" checked>
									</td>
									<td><input type="checkbox" id="checkbox" name="checkbox" checked>
									</td>
									<td><input type="checkbox" id="checkbox" name="checkbox" checked>
									</td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<c:if test="${not empty matriculas}">
				<input type="submit" id="botao" name="botao"
				value="Cadastrar Chamada" class="btnCadastrarChamada">
			</c:if>
		</form>

	</main>
</body>
</html>