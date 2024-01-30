<%@ include file="../shared/header.jspf"%>
<%@ include file="../shared/navigation.jspf"%>
<div class="container">
 <div class="row">
  <div class="col-md-6 col-md-offset-3 ">
   <div class="panel panel panel-default">
   
    <c:if test="${arbitre != null}">
            <div class="panel-heading">Modifier Arbitre</div>
      </c:if>
     <c:if test="${arbitre == null}">
               <div class="panel-heading">Ajouter Arbitre</div>
     </c:if>
    
    <div class="panel-body">
   <c:if test="${arbitre != null}">
      <form action="<c:url value='/api/arbitres/update?id=${arbitre.id}'/>" method="post">
    </c:if>
    <c:if test="${arbitre == null}">
       <form action="<c:url value='/api/arbitres/insert'/>" method="post">
    </c:if>


     <c:if test="${arbitre != null}">
	   <div class="form-group row">
		  <label for="idArb" class="col-sm-2 col-form-label">Identifiant</label>
		  <div class="col-sm-10">
             <input  type="text" class="form-control" id="idArb" name="id" value="<c:out value='${arbitre.id}' />" />
           </div>
	  	</div>
      </c:if>

	  <div class="form-group row">
	    <label for="nomArbitre" class="col-sm-2 col-form-label">Nom</label>
	    <div class="col-sm-10">
	      <input type="text" class="form-control" id="nomArbitre" name="nom" placeholder="Nom Arbitre" value="<c:out value='${arbitre.nom}' />" />
	    </div>
	  </div>
	  <div class="form-group row">
	    <label for="prenomArbitre" class="col-sm-2 col-form-label">Prénom</label>
	    <div class="col-sm-10">
	      <input type="text" name="prenom" class="form-control" id="prenomArbitre" placeholder="Prénom Arbitre" value="<c:out value='${arbitre.prenom}' />" />
	    </div>
	  </div>
	   <div class="form-group row">
	    <label for="dateNaissance" class="col-sm-2 col-form-label">Date De Naissance</label>
	    <div class="col-sm-10">
	    	<input type="text" name="dateNaissance" class="form-control" id="dateNaissance" placeholder="YYYY-MM-AA" value="<c:out value='${arbitre.dateNaissance}' />" />
	    </div>
	  </div>

 	<div class="form-group row">
	    <label for="nationalite" class="col-sm-2 col-form-label">Nationalité</label>
	    <div class="col-sm-10">
	    	<input type="text" name="nationalite" class="form-control" id="nationalite" placeholder="Nationalité" value="<c:out value='${arbitre.nationalite}' />" />
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