<%@ include file="../shared/header.jspf"%>
<%@ include file="../shared/navigation.jspf"%>

<div class="container">
 <c:if  test="${sessionScope.user != null && 'ADMIN'.equals(sessionScope.role)}">

<div class="text-right"> <!-- Add the text-right class to align the content to the right -->
         <a type="button" class="btn btn-primary btn-md " href="<c:url value='/api/joueurs/new'/>">Ajouter Joueur</a>
    </div>
    </c:if>
 <br>
 <div class="panel panel panel-default" >
  <div class="panel-heading">
   <h3>Liste des Joueurs</h3>
  </div>
  <div class="panel-body">
   <table class="table table-striped">
    <thead>
     <tr>
      <th width="20%">ID</th>
       <th width="20%">Nom</th>
       <th width="20%">Prénom</th>
       <th width="20%">Nationalité</th>
       <th width="20%">Date De Naissance</th>
       <th width="20%">Equipe Actuelle</th>
       <th width="20%">Maillot</th>
	    <th width="20%">Poste</th>
	    <th width="20%">Equipe Precedente</th>
       <th width="60%">Actions</th>
     </tr>
    </thead>
    <tbody>
     <c:forEach var="joueur" items="${listJoueur}">
                <tr>
                    <td><c:out value="${joueur.id}" /></td>
                    <td><c:out value="${joueur.nom}" /></td>
                    <td><c:out value="${joueur.prenom}" /></td>
                    <td><c:out value="${joueur.nationalite}" /></td>
                    <td><c:out value="${joueur.dateNaissance}" /></td>
                     <td>${joueur.equipeActuelle.nom}</td>
                    <td>${joueur.maillot}</td>
			        <td>${joueur.post}</td>
			        <td>${joueur.equipePrecedente != null ? joueur.equipePrecedente.nom : 'N/A'}</td>
                    <td>
                     <c:if  test="${sessionScope.user != null && 'ADMIN'.equals(sessionScope.role)}">
                    
                       <div style="white-space: nowrap;">
			                <a type="button" class="btn btn-success" href="<c:url value='/api/joueurs/edit'/>?id=${joueur.id}">Modifier</a>
			                <a type="button" class="btn btn-warning" href="<c:url value='/api/joueurs/delete'/>?id=${joueur.id}">Supprimer</a>
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