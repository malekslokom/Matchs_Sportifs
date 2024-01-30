<%@ include file="../shared/header.jspf"%>
<%@ include file="../shared/navigation.jspf"%>
<div class="container">
 <div class="row">
  <div class="col-md-6 col-md-offset-3 ">
   <div class="panel panel panel-default">
   
    <c:if test="${equipe != null}">
            <div class="panel-heading">Modifier Equipe</div>
      </c:if>
     <c:if test="${equipe == null}">
               <div class="panel-heading">Ajouter Equipe</div>
     </c:if>
    
    <div class="panel-body">
   <c:if test="${equipe != null}">
            <form action="<c:url value='/api/equipes/update?id=${equipe.idEquipe}'/>" method="post">
    </c:if>
    <c:if test="${equipe == null}">
       <form action="<c:url value='/api/equipes/insert'/>" method="post">
    </c:if>

     <c:if test="${equipe != null}">
	   <div class="form-group row">
		  <label for="idEq" class="col-sm-2 col-form-label">Identifiant</label>
		  <div class="col-sm-10">
             <input type="hidden" class="form-control" id="idEq" name="id" value="<c:out value='${equipe.idEquipe}' />" />
           </div>
	  	</div>
      </c:if>

	  <div class="form-group row">
	    <label for="nomEquipe" class="col-sm-2 col-form-label">Nom</label>
	    <div class="col-sm-10">
	      <input type="text" class="form-control" id="nomEquipe" name="nom" placeholder="Nom Equipe" value="<c:out value='${equipe.nom}' />" required />
	    </div>
	  </div>
	   <div class="form-group row">
	    <label for="dateNaissance" class="col-sm-2 col-form-label">Date De Création</label>
	    <div class="col-sm-10">
	    	<input type="date" name="anneeNaissance" class="form-control" id="anneeNaissance" value="<c:out value='${equipe.anneeNaissance}' />" required />
	    </div>
	  </div>
  <c:if test="${ equipe!= null}">
<!-- Section to remove current players -->
    <!-- Section to remove current players -->
		<c:if test="${not empty equipe.joueursActuels}">
		   <h4>Selectionner les joueurs actuels à enlever</h4>
		   <c:forEach var="joueur" items="${equipe.joueursActuels}">
		       <!-- Hidden field to send the player ID -->
		       <input type="hidden" name="allPlayerIds" value="${joueur.id}" />
		       <!-- Checkbox to indicate if the player should be removed -->
		       <label>
		           <input type="checkbox" name="removePlayers" value="${joueur.id}" > <c:out value="${joueur.nom} ${joueur.prenom} ${joueur.maillot}"/>
		       </label>
		       <br>
		   </c:forEach>
		</c:if>
      <!-- Section to add new players -->
    
      <h4>Ajouter de nouveaux joueurs </h4>
      <c:forEach var="joueur" items="${joueurSansEquipe}">
          <input type="checkbox" name="addPlayers" value="${joueur.id}"> <c:out value="${joueur.nom} ${joueur.prenom} "/> <br>
      </c:forEach>

      <!-- Section to remove current coaches -->
		<c:if test="${not empty equipe.entraineurs }">
		  <h4>Selectionner les entraineurs actuels à enlever</h4>
		  <c:forEach var="entraineur" items="${equipe.entraineurs}">
		      <!-- Hidden field to send the coach ID -->
		      <input type="hidden" name="allCoachIds" value="${entraineur.idEntraineur}" />
		      <!-- Checkbox to indicate if the coach should be removed -->
		      <label>
		          <input type="checkbox" name="removeCoaches" value="${entraineur.idEntraineur}" > <c:out value="${entraineur.nom} ${entraineur.prenom}  "/>
		      </label>
		      <br>
		  </c:forEach>
		</c:if>
      
      <!-- Section to add new coaches -->
      <h4>Ajouter de nouveaux entraineurs </h4>
      <c:forEach var="entraineur" items="${entraineurSansEquipe}">
          <input type="checkbox" name="addCoaches" value="${entraineur.idEntraineur}"> <c:out value="${entraineur.nom} ${entraineur.prenom} "/> <br>
      </c:forEach>
      </c:if>

		<div class="form-group row">
           <div class="col-sm-10 col-sm-offset-2 text-right">
              <button type="submit" class="btn btn-primary btn-md mr-2">Enregistrer</button>
              <button type="reset" class="btn btn-secondary btn-md">Annuler</button>
           </div>
        </div>
	</form>
    </div>
   </div>
  </div>
 </div>
</div>
<%@ include file="../shared/footer.jspf"%>