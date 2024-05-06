<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sistema AGIS - Tela Inicial</title>
<link rel="stylesheet" type="text/css" href='<c:url value = "./resources/css/styleIndex.css"/>'>
</head>

<body>

	<div class="container">

		<img src='<c:url value = "./resources/images/LogoAGIS.png"/>' alt="">

		<div class="entrar-como">
			<label for="">Entrar como:</label>
		</div>

		<a href="menuSecretaria" class="funcional">Secretaria Academica</a>
		<a href="matriculaAluno" class="funcional">Aluno</a>
		<a href="" class="botao">Professor</a>

	</div>
	
</html>