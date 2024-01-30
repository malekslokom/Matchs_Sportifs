<%@ include file="../shared/header.jspf"%>
<%@ include file="../shared/navigation.jspf"%>

<div class="container">
 <c:if  test="${sessionScope.user != null && 'ADMIN'.equals(sessionScope.role)}">

    <div class="text-right">
        <a type="button" class="btn btn-primary btn-md " href="<c:url value='/api/lieuxAcceuil/new'/>">Ajouter Lieu d'Accueil</a>
    </div>
    </c:if>
    <br>
    <div class="panel panel panel-default" >
        <div class="panel-heading">
            <h3>Liste des Lieux d'Accueil</h3>
        </div>
        <div class="panel-body">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th width="20%">ID</th>
                        <th width="20%">Type</th>
                        <th width="20%">Nom</th>
                        <th width="20%">Adresse</th>
                  
                        <th width="20%">Courriel</th>
                        <th width="20%">Infos PMR</th>
                        <th width="20%">Commentaires</th>
                        <th width="20%">telephone</th>
                        <th width="20%">Site Internet</th>
                        <th width="20%">Capacite</th>
                        <th width="60%">Actions</th>
                        
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="lieuAcceuil" items="${listLieuAcceuil}">
                        <tr>
                            <td><c:out value="${lieuAcceuil.idlieuAcceuil}" /></td>
                            <td><c:out value="${lieuAcceuil.type}" /></td>
                            <td><c:out value="${lieuAcceuil.nom}" /></td>
                            <td>
                                <!-- Assuming you have an Adresse object associated with Lieu d'Accueil -->
                                <c:choose>
                                    <c:when test="${lieuAcceuil.adresse ne null}">
                                        <c:out value="${lieuAcceuil.adresse.rue}, ${lieuAcceuil.adresse.ville},${lieuAcceuil.adresse.codePostal}" />
                                    </c:when>
                                    <c:otherwise>
                                        No Address Available
                                    </c:otherwise>
                                </c:choose>
                            </td>
                             <td><c:out value="${lieuAcceuil.courriel}" /></td>
                              <td><c:out value="${lieuAcceuil.infosPMR}" /></td>
                               <td><c:out value="${lieuAcceuil.commentaires}" /></td>
                                <td><c:out value="${lieuAcceuil.telephone}" /></td>
                                 <td><c:out value="${lieuAcceuil.siteInternet}" /></td>
                                  <td><c:out value="${lieuAcceuil.capacite}" /></td>
                                  
                            <td>
                             <c:if  test="${sessionScope.user != null && 'ADMIN'.equals(sessionScope.role)}">
                            
                                <div style="white-space: nowrap;">
                                    <a type="button" class="btn btn-success" href="<c:url value='/api/lieuxAcceuil/edit'/>?id=${lieuAcceuil.idlieuAcceuil}">Modifier</a>
                                    <a type="button" class="btn btn-warning" href="<c:url value='/api/lieuxAcceuil/delete'/>?id=${lieuAcceuil.idlieuAcceuil}">Supprimer</a>
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
