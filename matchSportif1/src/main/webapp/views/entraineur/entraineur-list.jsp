<%@ include file="../shared/header.jspf"%>
<%@ include file="../shared/navigation.jspf"%>

<div class="container">
 <c:if  test="${sessionScope.user != null && 'ADMIN'.equals(sessionScope.role)}">
<div class="text-right"> <!-- Add the text-right class to align the content to the right -->
         <a type="button" class="btn btn-primary btn-md " href="<c:url value='/api/entraineurs/new'/>">Ajouter Entraineur</a>
    </div>
    </c:if>
 <br>
 <div class="panel panel panel-default" >
  <div class="panel-heading">
   <h3>Liste des Entraineurs</h3>
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
       <th width="20%">Equipe</th>
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
     
     <c:forEach var="entraineur" items="${listEntraineur}">
                <tr>
                    <td><c:out value="${entraineur.idEntraineur}" /></td>
                    <td><c:out value="${entraineur.nom}" /></td>
                    <td><c:out value="${entraineur.prenom}" /></td>
                    <td><c:out value="${entraineur.nationalite}" /></td>
                    <td><c:out value="${entraineur.dateNaissance}" /></td>
                    <td><c:out value="${entraineur.equipe.nom}" /></td>
                    <td>
                     <c:if  test="${sessionScope.user != null && 'ADMIN'.equals(sessionScope.role)}">
                       <div style="white-space: nowrap;">
			                <a type="button" class="btn btn-success" href="<c:url value='/api/entraineurs/edit'/>?id=${entraineur.idEntraineur}">Modifier</a>
			                <a type="button" class="btn btn-warning" href="<c:url value='/api/entraineurs/delete'/>?id=${entraineur.idEntraineur}">Supprimer</a>
			            </div>
			            </c:if>
                    </td>
                </tr>
            </c:forEach>
    </tbody>
   </table>
  </div>
 </div>
 
<%@ include file="../shared/footer.jspf"%>