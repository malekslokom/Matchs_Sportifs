<%@ include file="../shared/header.jspf"%>
<%@ include file="../shared/navigation.jspf"%>

<div class="container">
 <c:if  test="${sessionScope.user != null && 'ADMIN'.equals(sessionScope.role)}">

<div class="text-right"> <!-- Add the text-right class to align the content to the right -->
         <a type="button" class="btn btn-primary btn-md " href="<c:url value='/api/arbitres/new'/>">Ajouter Arbitre</a>
    </div>
    </c:if>
 <br>
 <div class="panel panel panel-default" >
  <div class="panel-heading">
   <h3>Liste des Arbitres</h3>
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
       <th width="60%">Actions</th>
     </tr>
    </thead>
    <tbody>
<%--      <c:forEach items="${todos}" var="todo">
      <tr>
       <td>${todo.description}</td>
       <td><fmt:formatDate value="${todo.targetDate}"
         pattern="dd/MM/yyyy" /></td>
       <td><a type="button" class="btn btn-success"
        href="/update-todo?id=${todo.id}">Update</a>
       <a type="button" class="btn btn-warning"
        href="/delete-todo?id=${todo.id}">Delete</a></td>
      </tr>
     </c:forEach> --%>
     
     <c:forEach var="arbitre" items="${listArbitre}">
                <tr>
                    <td><c:out value="${arbitre.id}" /></td>
                    <td><c:out value="${arbitre.nom}" /></td>
                    <td><c:out value="${arbitre.prenom}" /></td>
                    <td><c:out value="${arbitre.nationalite}" /></td>
                    <td><c:out value="${arbitre.dateNaissance}" /></td>
                    <td>
                     <c:if  test="${sessionScope.user != null && 'ADMIN'.equals(sessionScope.role)}">
                    
                       <div style="white-space: nowrap;">
			                <a type="button" class="btn btn-success" href="<c:url value='/api/arbitres/edit'/>?id=${arbitre.id}">Modifier</a>
			                <a type="button" class="btn btn-warning" href="<c:url value='/api/arbitres/delete'/>?id=${arbitre.id}">Supprimer</a>
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