<%@ include file="../shared/header.jspf"%>
<%@ include file="../shared/navigation.jspf"%>
<div class="container">
 <div class="row">
  <div class="col-md-6 col-md-offset-3 ">
   <div class="panel panel panel-default">
   
    <c:if test="${entraineur != null}">
            <div class="panel-heading">Modifier Entraineur</div>
      </c:if>
     <c:if test="${entraineur == null}">
               <div class="panel-heading">Ajouter Entraineur</div>
     </c:if>
    
    <div class="panel-body">
   <c:if test="${entraineur != null}">
      <form action="<c:url value='/api/entraineurs/update?id=${entraineur.idEntraineur}'/>" method="post">
    </c:if>
    <c:if test="${entraineur == null}">
       <form action="<c:url value='/api/entraineurs/insert'/>" method="post">
    </c:if>


     <c:if test="${entraineur != null}">
	   <div class="form-group row">
		  <label for="idEnt" class="col-sm-2 col-form-label">Identifiant</label>
		  <div class="col-sm-10">
             <input  type="text" class="form-control" id="idEnt" name="id" value="<c:out value='${entraineur.idEntraineur}' />" />
           </div>
	  	</div>
      </c:if>

	  <div class="form-group row">
	    <label for="nomEntraineur" class="col-sm-2 col-form-label">Nom</label>
	    <div class="col-sm-10">
	      <input type="text" class="form-control" id="nomEntraineur" name="nom" placeholder="Nom Entraineur" value="<c:out value='${entraineur.nom}' />" />
	    </div>
	  </div>
	  <div class="form-group row">
	    <label for="prenomEntraineur" class="col-sm-2 col-form-label">Prénom</label>
	    <div class="col-sm-10">
	      <input type="text" name="prenom" class="form-control" id="prenomEntraineur" placeholder="Prénom Entraineur" value="<c:out value='${entraineur.prenom}' />" />
	    </div>
	  </div>
<div class="form-group row">
	    <label for="dateNaissance" class="col-sm-2 col-form-label">Date De Naissance</label>
	    <div class="col-sm-10">
	    	<input type="date" name="dateNaissance" class="form-control" id="dateNaissance" value="<c:out value='${entraineur.dateNaissance}' />" required />
	    </div>
	  </div>

 	<div class="form-group row">
	    <label for="nationalite" class="col-sm-2 col-form-label">Nationalité</label>
	    <div class="col-sm-10">
	    	<input type="text" name="nationalite" class="form-control" id="nationalite" placeholder="Nationalité" value="<c:out value='${entraineur.nationalite}' />" required/>
	    </div>
	  </div>
<div class="form-group row">
    <label for="equipe" class="col-sm-2 col-form-label">Équipe</label>
    <div class="col-sm-10">
        <select class="form-control" id="equipe" name="idEquipe">
            <c:if test="${entraineur == null}">
                <option value="">Sélectionner une équipe</option>
            </c:if>
            <c:if test="${entraineur != null && entraineur.equipe != null}">
                <option value="${entraineur.equipe.idEquipe}">${entraineur.equipe.nom}</option>
            </c:if>
            <c:forEach items="${listEquipe}" var="equipe">
                <c:if test="${entraineur == null || entraineur.equipe.idEquipe != equipe.idEquipe}">
                    <option value="${equipe.idEquipe}">${equipe.nom}</option>
                </c:if>
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
