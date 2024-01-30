<%@ include file="../shared/header.jspf"%>
<%@ include file="../shared/navigation.jspf"%>
<div class="container">
 <div class="row">
  <div class="col-md-6 col-md-offset-3 ">
   <div class="panel panel panel-default">
   
    <c:if test="${joueur != null}">
            <div class="panel-heading">Modifier Joueur</div>
      </c:if>
     <c:if test="${joueur == null}">
               <div class="panel-heading">Ajouter Joueur</div>
     </c:if>
    
    <div class="panel-body">
   <c:if test="${joueur != null}">
      <form action="<c:url value='/api/joueurs/update?id=${joueur.id}'/>" method="post">
    </c:if>
    <c:if test="${joueur == null}">
       <form action="<c:url value='/api/joueurs/insert'/>" method="post">
    </c:if>


     <c:if test="${joueur != null}">
	   <div class="form-group row">
		  <label for="idArb" class="col-sm-2 col-form-label">Identifiant</label>
		  <div class="col-sm-10">
             <input  type="text" class="form-control" id="idArb" name="id" value="<c:out value='${joueur.id}' />" />
           </div>
	  	</div>
      </c:if>

	  <div class="form-group row">
	    <label for="nomJoueur" class="col-sm-2 col-form-label">Nom</label>
	    <div class="col-sm-10">
	      <input type="text" class="form-control" id="nomJoueur" name="nom" placeholder="Nom Joueur" value="<c:out value='${joueur.nom}' />" />
	    </div>
	  </div>
	  <div class="form-group row">
	    <label for="prenomJoueur" class="col-sm-2 col-form-label">Prénom</label>
	    <div class="col-sm-10">
	      <input type="text" name="prenom" class="form-control" id="prenomJoueur" placeholder="Prénom Joueur" value="<c:out value='${joueur.prenom}' />" />
	    </div>
	  </div>
	   <div class="form-group row">
	    <label for="dateNaissance" class="col-sm-2 col-form-label">Date De Naissance</label>
	    <div class="col-sm-10">
	    	<input type="text" name="dateNaissance" class="form-control" id="dateNaissance" placeholder="YYYY-MM-AA" value="<c:out value='${joueur.dateNaissance}' />" />
	    </div>
	  </div>

 	<div class="form-group row">
	    <label for="nationalite" class="col-sm-2 col-form-label">Nationalité</label>
	    <div class="col-sm-10">
	    	<input type="text" name="nationalite" class="form-control" id="nationalite" placeholder="Nationalité" value="<c:out value='${joueur.nationalite}' />" />
	    </div>
	  </div>
<%-- 	   <div class="form-group row">
    	<label for="equipeActuelle" class="col-sm-2 col-form-label">Equipe Actuelle:</label>
    	<div class="col-sm-10">
    		<input type="text" name="equipeActuelle" class="form-control" id="equipeActuelle" placeholder="equipe Actuelle"  value="<c:out value='${joueur.equipeActuelle.nom}' />" />
		</div>
	 </div> --%>
	 
	 
	<%-- <div class="form-group row">
	    <label for="equipeActuelle" class="col-sm-2 col-form-label">Équipe Actuelle:</label>
	    <div class="col-sm-10">
	        <select class="form-control" id="equipeActuelle" name="equipeActuelle">
	            <c:if test="${joueur != null && joueur.listEquipe != null}">
	                <option value="${joueur.equipeActuelle.id}">${joueur.equipeActuelle.nom}</option>
	            </c:if>
	            <c:forEach items="${listEquipe}" var="equipe">
	                <c:if test="${joueur == null || joueur.equipeActuelle == null || joueur.equipeActuelle.idEquipe != equipeActuelle.idEquipe}">
	                    <option value="${equipe.idEquipe}">${equipe.nom}</option>
	                </c:if>
	            </c:forEach>
	        </select>
	    </div>
	</div> --%>
<div class="form-group row">
    <label for="equipeActuelle" class="col-sm-2 col-form-label">Équipe Actuelle:</label>
    <div class="col-sm-10">
        <select class="form-control" id="equipeActuelle" name="equipeActuelle">
            <option value="">Sélectionnez une équipe</option> <!-- Ajout de cette ligne -->
            <c:forEach items="${listEquipe}" var="equipe">
                <c:choose>
                    <c:when test="${joueur != null && joueur.equipeActuelle != null && joueur.equipeActuelle.idEquipe == equipe.idEquipe}">
                        <option value="${equipe.idEquipe}" selected>${equipe.nom}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${equipe.idEquipe}">${equipe.nom}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>
    </div>
</div>
	  <div class="form-group row">
	  	<label for="maillot"  class="col-sm-2 col-form-label">Maillot:</label>
		  <div class="col-sm-10">
	    	<input type="text" name="maillot"   class="form-control" id="maillot" placeholder="maillot" value="<c:out value='${joueur.maillot}' />" />
	     </div>
	  </div>

	<div class="form-group row">
    	<label for="poste" class="col-sm-2 col-form-label">Poste:</label>
	    <div class="col-sm-10">
	    	<input type="text" name="poste" class="form-control" id="poste" placeholder="poste"  value="<c:out value='${joueur.post}' />" />
		</div>
	 </div>
	 
	
	 
	<%--  <div class="form-group row">
    	<label for="equipePrecedente" class="col-sm-2 col-form-label">Equipe Precedente:</label>
    	<div class="col-sm-10">
    		<input type="text" name="equipePrecedente" class="form-control" id="equipePrecedente" placeholder="Equipe Precedente" value="<c:out value='${joueur.equipePrecedente}' />" />
	  	</div>
	 </div> --%>
	 
<%-- 	 <div class="form-group row">
	    <label for="equipePrecedente" class="col-sm-2 col-form-label">Équipe Précédente</label>
	    <div class="col-sm-10">
	        <select class="form-control" id="equipePrecedente" name="equipePrecedente">
	            <c:if test="${joueur != null && joueur.listEquipe != null}">
	                <option value="${joueur.equipePrecedente.id}">${joueur.equipePrecedente.nom}</option>
	            </c:if>
	            <c:forEach items="${listEquipe}" var="equipe">
	                <c:if test="${joueur == null || joueur.equipePrecedente == null || joueur.equipePrecedente.idEquipe != equipePrecedente.idEquipe}">
	                    <option value="${equipe.idEquipe}">${equipe.nom}</option>
	                </c:if>
	            </c:forEach>
	        </select>
	    </div>
	</div> --%>
	
	<div class="form-group row">
    <label for="equipePrecedente" class="col-sm-2 col-form-label">Équipe Précédente</label>
    <div class="col-sm-10">
        <select class="form-control" id="equipePrecedente" name="equipePrecedente">
            <option value="">Sélectionnez une équipe</option> <!-- Ajout de cette ligne -->
            <c:forEach items="${listEquipe}" var="equipe">
                <c:choose>
                    <c:when test="${joueur != null && joueur.equipePrecedente != null && joueur.equipePrecedente.idEquipe == equipe.idEquipe}">
                        <option value="${equipe.idEquipe}" selected>${equipe.nom}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${equipe.idEquipe}">${equipe.nom}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>
    </div>
</div>
	 
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