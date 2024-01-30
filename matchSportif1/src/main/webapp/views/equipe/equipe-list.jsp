<%@ include file="../shared/header.jspf"%>
<%@ include file="../shared/navigation.jspf"%>

<div class="container">
 <c:if test="${sessionScope.user != null && 'ADMIN'.equals(sessionScope.role)}">
    <div class="text-right">
        <a type="button" class="btn btn-primary btn-md" href="<c:url value='/api/equipes/new'/>">Ajouter Equipes</a>
    </div>
    </c:if>
    <br>
    <div class="panel panel panel-default">
        <div class="panel-heading">
            <h3>Liste des Equipes</h3>
        </div>
        <div class="panel-body">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th width="20%">ID</th>
                        <th width="20%">Nom</th>
                        <th width="20%">Date De Naissance</th>
                        <th width="20%">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="equipe" items="${listEquipe}">
                        <tr>
                            <td><c:out value="${equipe.idEquipe}" /></td>
                            <td>
                                <a href="<c:url value='/api/equipes/${equipe.idEquipe}/detail'/>">
                                    <c:out value="${equipe.nom}" />
                                </a>
                            </td>
                            <td><c:out value="${equipe.anneeNaissance}" /></td>
                            <td>
                             <c:if test="${sessionScope.user != null && 'ADMIN'.equals(sessionScope.role)}">
                                <div style="white-space: nowrap;">
                                    <a type="button" class="btn btn-success" href="<c:url value='/api/equipes/edit'/>?id=${equipe.idEquipe}">Modifier</a>
                                    <a type="button" class="btn btn-warning" href="<c:url value='/api/equipes/delete'/>?id=${equipe.idEquipe}">Supprimer</a>
                                </div>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="../shared/footer.jspf"%>