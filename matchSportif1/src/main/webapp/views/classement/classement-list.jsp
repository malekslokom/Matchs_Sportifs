<%@ include file="../shared/header.jspf"%>
<%@ include file="../shared/navigation.jspf"%>
<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="com.matchSportifs.model.User" %>

<div class="container">
    <c:if test="${sessionScope.user != null && 'ADMIN'.equals(sessionScope.role)}"><div class="text-right"> <!-- Add the text-right class to align the content to the right -->
         <a type="button" class="btn btn-primary btn-md " href="<c:url value='/api/classements/new'/>">Ajouter Classement</a>
    </div>
    </c:if>
 <br>
 <div class="panel panel panel-default" >
  <div class="panel-heading">
   <h3>Liste des Classements</h3>
  </div>
  <div class="panel-body">
   <table class="table table-striped">
    <thead>
     <tr>
      <th width="20%">ID</th>
       <th width="20%">Nom Equipe</th>
       <th width="20%">Points</th>
       <th width="20%">Points Domicile</th>
       <th width="20%">Points Extérieur</th>
       <th width="60%">Matchs Joués</th>
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
     
     <c:forEach var="classement" items="${listClassement}">
                <tr>
                    <td><c:out value="${classement.id}" /></td>
                    <td><c:out value="${classement.equipe.nom}" /></td>
                    <td><c:out value="${classement.points}" /></td>
                    <td><c:out value="${classement.pointsDom}" /></td>
                    <td><c:out value="${classement.pointsExt}" /></td>
                    <td><c:out value="${classement.matchesPlayed}" /></td>
                    <td>
                     <c:if test="${sessionScope.user != null && 'ADMIN'.equals(sessionScope.role)}">
                       <div style="white-space: nowrap;">
			                <a type="button" class="btn btn-success" href="<c:url value='/api/classements/edit'/>?id=${classement.id}">Modifier</a>
			                <a type="button" class="btn btn-warning" href="<c:url value='/api/classements/delete'/>?id=${classement.id}">Supprimer</a>
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