<%@ include file="../shared/header.jspf"%>
<%@ include file="../shared/navigation.jspf"%>

<div class="container">
 <c:if  test="${sessionScope.user != null && 'ADMIN'.equals(sessionScope.role)}">

    <div class="text-right">
        <a type="button" class="btn btn-primary btn-md " href="<c:url value='/api/rencontres/new'/>">Ajouter Rencontre</a>
    </div>
    </c:if>
    <br>
    <div class="panel panel panel-default">
        <div class="panel-heading">
            <h3>Liste des Rencontres</h3>
        </div>
        <div class="panel-body">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th width="5%">ID</th>
                        <th width="20%">Equipe Domicile</th>
                        <th width="20%">Equipe Extérieur</th>
                        <th width="10%">Date Début</th>
                        <th width="10%">Heure Début</th>
                        <th width="10%">Date Fin</th>
                        <th width="10%">Heure Fin</th>
                        <th width="15%">Lieu Accueil</th>
                        
                        <th width="10%">URL Billetrie</th>
                        <th width="20%">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="rencontre" items="${listRencontre}">
                        <tr>
                          <%--   <td><c:out value="${rencontre.idRencontre}" /></td> --%>
                            <td>
                                <a href="<c:url value='/api/rencontres/${rencontre.idRencontre}/details'/>">
                                    <c:out value="${rencontre.idRencontre}" />
                                </a>
                            </td>
                            <c:choose>
							    <c:when test="${not empty rencontre.equipeRencontres and rencontre.equipeRencontres.size() >= 2}">
							       <%--  <td><c:out value="${rencontre.equipeRencontres[0].equipe.nom}" /></td>
							        <td><c:out value="${rencontre.equipeRencontres[1].equipe.nom}" /></td> --%>
							        <c:forEach var="equipeRencontre" items="${rencontre.equipeRencontres}">
									    <td><c:out value="${equipeRencontre.equipe.nom}" /></td>
									</c:forEach>
							        
							    </c:when>
							    <c:otherwise>
							        <td>N/A</td>
							        <td>N/A</td>
							    </c:otherwise>
							</c:choose>


                            <td><c:out value="${rencontre.dateDebut}" /></td>
                            <td><c:out value="${rencontre.heureDebut}" /></td>
                            <td><c:out value="${rencontre.dateFin}" /></td>
                            <td><c:out value="${rencontre.heureFin}" /></td>
                            <td><c:out value="${rencontre.lieuAcceuil.nom}" /></td>
                            
                            <td><c:out value="${rencontre.urlBilletrie}" /></td>
                            <td>
                             <c:if  test="${sessionScope.user != null && 'ADMIN'.equals(sessionScope.role)}">
                            
                                <div style="white-space: nowrap;">
                                    <a type="button" class="btn btn-success" href="<c:url value='/api/rencontres/edit'/>?id=${rencontre.idRencontre}">Modifier</a>
                                    <a type="button" class="btn btn-warning" href="<c:url value='/api/rencontres/delete'/>?id=${rencontre.idRencontre}">Supprimer</a>
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
