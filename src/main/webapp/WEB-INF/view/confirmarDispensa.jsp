<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sistema AGIS - Secretaria Acadêmica</title>
<link rel="stylesheet" type="text/css" href='<c:url value = "./resources/css/styleSecretariaCurso.css"/>'>
</head>

<body>

    <script src="./resources/js/header.js"></script>

    <main>
        <div class="titulo">
            <h1>Confirmar Dispensas</h1>
        </div>
        
         <div align="center">
            <c:if test="${not empty erro }">
                <h2>
                    <b><c:out value="${erro }" /></b>
                </h2>
            </c:if>
        </div>

        <br />
        <div align="center">
            <c:if test="${not empty saida }">
                <h3>
                    <b><c:out value="${saida }" /></b>
                </h3>
            </c:if>
        </div> 
        
        <br/>

        <form action="confirmarDispensa" method="post">

            <table>
                <thead>
                    <tr>
                        <th>RA</th>
                        <th>Nome</th>
                        <th>Disciplina</th>
                        <th>Selecionar</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="dispensa" items="${dispensas}"> 
                        <tr>
                            <td>11111111></td>
                            <td>Gus</td>
                            <td>Redes</td>
                            <td>
                                <button class="confirmar">Confirmar</button>
                                <button class="negar">Negar</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </form>

        <br />
        
    </main>

    <script src="./resources/js/navegacao.js"></script> 

</body>
</html>