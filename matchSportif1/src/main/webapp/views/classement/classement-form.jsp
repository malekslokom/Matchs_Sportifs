<%@ include file="../shared/header.jspf"%>
<%@ include file="../shared/navigation.jspf"%>
<div class="container">
 <div class="row">
  <div class="col-md-6 col-md-offset-3 ">
   <div class="panel panel panel-default">
   
    <c:if test="${classement != null}">
            <div class="panel-heading">Modifier Classement</div>
      </c:if>
     <c:if test="${classement == null}">
               <div class="panel-heading">Ajouter Classement</div>
     </c:if>
    
    <div class="panel-body">
   <c:if test="${classement != null}">
      <form action="<c:url value='/api/classements/update?id=${classement.id}'/>" method="post">
    </c:if>
    <c:if test="${classement == null}">
       <form action="<c:url value='/api/classements/insert'/>" method="post">
    </c:if>


     <c:if test="${classement != null}">
	   <div class="form-group row">
		  <label for="idCla" class="col-sm-2 col-form-label">Identifiant</label>
		  <div class="col-sm-10">
             <input  type="text" class="form-control" id="idCla" name="id" value="<c:out value='${classement.id}' />" />
           </div>
	  	</div>
      </c:if>
<div class="form-group row">
    <label for="equipe" class="col-sm-2 col-form-label">Équipe</label>
    <div class="col-sm-10">
        <c:choose>
            <c:when test="${classement != null && classement.equipe != null}">
                <!-- Display the team name as a disabled text field -->
                <input type="text" class="form-control" id="equipe" name="equipeName" value="${classement.equipe.nom}" disabled />
                <!-- Include a hidden field to hold the id of the team -->
                <input type="hidden" name="idEquipe" value="${classement.equipe.idEquipe}" />
            </c:when>
            <c:otherwise>
                <!-- Display the dropdown for selecting a team -->
                <select class="form-control" id="equipe" name="idEquipe">
                    <option value="">Sélectionner une équipe</option>
                    <c:forEach items="${listEquipe}" var="equipe">
                        <option value="${equipe.idEquipe}">${equipe.nom}</option>
                    </c:forEach>
                </select>
            </c:otherwise>
        </c:choose>
    </div>
</div>
	  <div class="form-group row">
	    <label for="points" class="col-sm-2 col-form-label">Points</label>
	    <div class="col-sm-10">
	      <input type="text" name="points" class="form-control" id="points" placeholder="points" value="<c:out value='${classement.points}' />" />
	    </div>
	  </div>
	   <div class="form-group row">
	    <label for="pointsDom" class="col-sm-2 col-form-label">Points Domiciles</label>
	    <div class="col-sm-10">
	    	<input type="text" name="pointsDom" class="form-control" id="pointsDom" placeholder="Points Domiciles" value="<c:out value='${classement.pointsDom}' />" />
	    </div>
	  </div>

 	<div class="form-group row">
	    <label for="pointsExt" class="col-sm-2 col-form-label">Points Exterieur</label>
	    <div class="col-sm-10">
	    	<input type="text" name="pointsExt" class="form-control" id="pointsExt" placeholder="Points Exterieur" value="<c:out value='${classement.pointsExt}' />" />
	    </div>
	  </div>
	   	<div class="form-group row">
	    <label for="matchesPlayed" class="col-sm-2 col-form-label">Matchs Joués</label>
	    <div class="col-sm-10">
	    	<input type="text" name="matchesPlayed" class="form-control" id="matchesPlayed" placeholder="Matchs Joués" value="<c:out value='${classement.matchesPlayed}' />" />
	    </div>
	  </div>
	 
		<div class="form-group row">
           <div class="col-sm-10 col-sm-offset-2 text-right">
              <button type="submit" class="btn btn-primary btn-md mr-2">Enregistrer</button>
              <button type="reset" class="btn btn-secondary btn-md">Annuler</button>
           </div>
        </div>
	</form>
<script>
    <c:if test="${not empty errorClassement}">
        alert('${errorClassement}');
    </c:if>
</script>
    </div>
   </div>
  </div>
 </div>
</div>
<%@ include file="../shared/footer.jspf"%>
