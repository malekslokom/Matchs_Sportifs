<%@ include file="../shared/header.jspf"%>
<%@ include file="../shared/navigation.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">
     
        <br>
        <div class="panel panel panel-default">
            <div class="panel-heading">
                <h3>Détails Participant</h3>
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
                        </tr>
                    </thead>
                    <tbody>
                        
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
                            </tr>
                        
                    </tbody>
                </table>
            </div>
        </div>
 
 <c:if test="${showButParticipants}">
    <%-- <div class="container">
       <c:forEach var="equipeRencontre" items="${rencontre.equipeRencontres}">
        <div class="text-right">
             <a type="button" class="btn btn-primary btn-md" href="/matchSportif/api/rencontres/${idRencontre}/details/equipe/${idEquipe}/participants/${idJoueur}/details/butRencontre/new">Ajouter But</a>
       </div>
        <br>
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3>Liste des Buts</h3>
            </div>
            <div class="panel-body">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th width="20%">ID Joueur</th>
                            <th width="40%">Heure</th>
                            <th width="40%">Type d'Action</th>
                            <th width="20%">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="butRencontre" items="${rencontre.butRencontres}">
                            <c:choose>
                                <c:when test="${butRencontre.joueur.equipeActuelle ne null and butRencontre.joueur.equipeActuelle.idEquipe eq equipeRencontre.equipe.idEquipe}">
                                    <tr>
                                        <td>
                                            <a href="<c:url value='/api/rencontres/${rencontre.idRencontre}/details/equipe/${equipeRencontre.equipe.idEquipe}/participants/${butRencontre.joueur.id}/details'/>">
                                                <c:out value="${butRencontre.joueur.id}" />
                                            </a>
                                        </td> 
                                        <td><c:out value="${butRencontre.heure}" /></td>
                                        <td><c:out value="${butRencontre.typeAction}" /></td>
                                        <td>
                                            <div style="white-space: nowrap;">
                                                <a type="button" class="btn btn-success" href="<c:url value='/api/rencontres/${rencontre.idRencontre}/details/equipe/${equipeRencontre.equipe.idEquipe}/participants/edit'/>?idJoueur=${butRencontre.joueur.id}">Modifier</a>
                                                <a type="button" class="btn btn-warning" href="<c:url value='/api/rencontres/${rencontre.idRencontre}/details/equipe/${equipeRencontre.equipe.idEquipe}/participants/delete'/>?idJoueur=${butRencontre.joueur.id}">Supprimer</a>
                                            </div>
                                        </td>
                                    </tr>
                                </c:when>
                            
                            </c:choose>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

</div> --%>

<div class="container">
 <c:if  test="${sessionScope.user != null && 'ADMIN'.equals(sessionScope.role)}">

<div class="text-right"> <!-- Add the text-right class to align the content to the right -->
             <a type="button" class="btn btn-primary btn-md" href="/matchSportif/api/rencontres/${idRencontre}/details/equipe/${idEquipe}/participants/${idJoueur}/details/butRencontre/new">Ajouter But</a>
 </div>
 </c:if>
 <br>
   <div class="panel panel-default">
            <div class="panel-heading">
                <h3>Liste des Buts</h3>
            </div>
  <div class="panel-body">
   <table class="table table-striped">
    <thead>
     <tr>
       <th width="40%">Heure du but Marqué</th>
       <th width="40%">Type d'action</th>
       <th width="60%">Actions</th>
     </tr>
    </thead>
    <tbody>

     
     <c:forEach var="butRencontre" items="${butRencontre}">
                <tr>
                    <td><c:out value="${butRencontre.heure}" /></td>
                    <td><c:out value="${butRencontre.typeAction}" /></td>
                                      <td>
                                 <c:if  test="${sessionScope.user != null && 'ADMIN'.equals(sessionScope.role)}">
                                      
                       <div style="white-space: nowrap;">                                
						<a type="button" class="btn btn-success" href="<c:url value='/api/rencontres/${idRencontre}/details/equipe/${idEquipe}/participants/${idJoueur}/details/butRencontre/edit'/>?idButRencontre=${butRencontre.idButRencontre}">Modifier</a>
                        <a type="button" class="btn btn-warning" href="<c:url value='/api/rencontres/${idRencontre}/details/equipe/${idEquipe}/participants/${idJoueur}/details/butRencontre/delete'/>?idButRencontre=${butRencontre.idButRencontre}">Supprimer</a>
                                            </div>
                                            </c:if>
                    </td>
                </tr>
            </c:forEach>
    </tbody>
   </table>
  </div>
 </div>

</c:if> 
<c:if test="${!showButParticipants}">
    <div class="container">
    
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <div class="panel panel-default">
                    <c:if test="${butRencontre != null}">
                        <div class="panel-heading">Modifier But Rencontre</div>
                    </c:if>
                    <c:if test="${butRencontre == null}">
                        <div class="panel-heading">Ajouter But Rencontre</div>
                    </c:if>

                    <div class="panel-body">
                        <c:if test="${butRencontre != null}">
                            <form action="<c:url value='/api/rencontres/${idRencontre}/details/equipe/${idEquipe}/participants/${idJoueur}/details/butRencontre/${butRencontre.idButRencontre}/update'/>" method="post">
                        </c:if>
                        <c:if test="${butRencontre == null}">
                            <form action="<c:url value='/api/rencontres/${idRencontre}/details/equipe/${idEquipe}/participants/${idJoueur}/details/butRencontre/insert'/>" method="post">
                        </c:if>
                        
                        <div class="form-group row">
                            <label for="heure" class="col-sm-2 col-form-label">Heure</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="heure" name="heure" placeholder="Heure" value="<c:out value='${butRencontre.heure}' />" />
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="typeAction" class="col-sm-2 col-form-label">Type d'Action</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="typeAction" name="typeAction" placeholder="Type d'Action" value="<c:out value='${butRencontre.typeAction}' />" />
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
</div>
</div>
</div>
</c:if>
<c:if test="${showPinalite}">
<div class="container">
 <c:if  test="${sessionScope.user != null && 'ADMIN'.equals(sessionScope.role)}">

    <div class="text-right">
        <a type="button" class="btn btn-primary btn-md"  href="/matchSportif/api/rencontres/${idRencontre}/details/equipe/${idEquipe}/participants/${idJoueur}/details/penalite/new">Ajouter une Pénalité</a>
    </div>
    </c:if>
    <br>
    <div class="panel panel panel-default">
        <div class="panel-heading">
            <h3>Liste des Penalités</h3>
        </div>
        <div class="panel-body">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th width="20%">Libelle</th>
                        <th width="20%">Heure</th>
                        <th width="20%">Définitive</th>
                        <th width="20%">Provisoire</th>
                        <th width="20%">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="penalite" items="${listPenalite}">
                        <tr>
                            <td><c:out value="${penalite.libelle}" /></td>
                            <td><c:out value="${penalite.heure}" /></td>
                            <td><c:out value="${penalite.definitive ? 'Oui' : 'Non'}" /></td>
                            <td><c:out value="${penalite.provisoire ? 'Oui' : 'Non'}" /></td>
                            <td>
                             <c:if  test="${sessionScope.user != null && 'ADMIN'.equals(sessionScope.role)}">
                            
                                <div style="white-space: nowrap;">
                                    <a type="button" class="btn btn-success" href="<c:url value='/api/rencontres/${idRencontre}/details/equipe/${idEquipe}/participants/${idJoueur}/details/butRencontre/edit'/>?idPenalite=${penalite.idPenalite}">Modifier</a>
                        <a type="button" class="btn btn-warning" href="<c:url value='/api/rencontres/${idRencontre}/details/equipe/${idEquipe}/participants/${idJoueur}/details/butRencontre/delete'/>?idPenalite=${penalite.idPenalite}">Supprimer</a>
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
</c:if>


<c:if test="${!showPinalite}">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel panel-default">
                <c:choose>
                    <c:when test="${penalite != null}">
                        <div class="panel-heading">Modifier Pénalité</div>
                    </c:when>
                    <c:otherwise>
                        <div class="panel-heading">Ajouter Pénalité</div>
                    </c:otherwise>
                </c:choose>
                <div class="panel-body">
                  <%--   <c:choose>
                        <c:when test="${penalite != null}">
                            <form action="<c:url value='/api/penalites/update'/>" method="post">
                                <input type="hidden" name="idPenalite" value="${penalite.idPenalite}" />
                        </c:when>
                        <c:otherwise>
                            <form action="<c:url value='/api/penalites/insert'/>" method="post">
                        </c:otherwise>
                    </c:choose> --%>
						<c:if test="${penalite != null}">
                            <form action="<c:url value='/api/rencontres/${idRencontre}/details/equipe/${idEquipe}/participants/${idJoueur}/details/penalite/${penalite.idPenalite}/update'/>" method="post">
                        </c:if>
                        <c:if test="${penalite == null}">
                            <form action="<c:url value='/api/rencontres/${idRencontre}/details/equipe/${idEquipe}/participants/${idJoueur}/details/penalite/insert'/>" method="post">
                        </c:if>


                    <c:if test="${penalite != null}">
                        <div class="form-group row">
                            <label for="idCla" class="col-sm-2 col-form-label">Identifiant</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="idCla" name="id" value="<c:out value='${penalite.idPenalite}' />" />
                            </div>
                        </div>
                    </c:if>

                    <div class="form-group row">
                        <label for="joueur" class="col-sm-2 col-form-label">Joueur</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="joueur" name="joueurName" value="${joueur.maillot} ${joueur.nom} ${joueur.prenom}" readonly />
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="libelle" class="col-sm-2 col-form-label">Libellé</label>
                        <div class="col-sm-10">
                            <input type="text" name="libelle" class="form-control" id="libelle" placeholder="Libellé" value="<c:out value='${penalite.libelle}' />" />
                        </div>
                    </div>
                    
                    <div class="form-group row">
                            <label for="heure" class="col-sm-2 col-form-label">Heure</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="heure" name="heure" placeholder="Heure" value="<c:out value='${penalite.heure}' />" />
                            </div>
                        </div>
                    <div class="form-group row">
                        <label class="col-sm-2 col-form-label">Provisoire</label>
                        <div class="col-sm-10">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="provisoire" id="provisoireTrue" value="true" <c:if test="${penalite != null && penalite.provisoire}">checked</c:if> />
                                <label class="form-check-label" for="provisoireTrue">Vrai</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="provisoire" id="provisoireFalse" value="false" <c:if test="${penalite != null && !penalite.provisoire}">checked</c:if> />
                                <label class="form-check-label" for="provisoireFalse">Faux</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 col-form-label">Définitif</label>
                        <div class="col-sm-10">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="definitive" id="definitiveTrue" value="true" <c:if test="${penalite != null && penalite.definitive}">checked</c:if> />
                                <label class="form-check-label" for="definitiveTrue">Vrai</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="definitive" id="definitiveFalse" value="false" <c:if test="${penalite != null && !penalite.definitive}">checked</c:if> />
                                <label class="form-check-label" for="definitiveFalse">Faux</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="arbitre" class="col-sm-2 col-form-label">Arbitre</label>
                        <div class="col-sm-10">
                            <select class="form-control" id="arbitre" name="id">
                                <c:if test="${penalite == null}">
                                    <option value="">Sélectionner un arbitre</option>
                                </c:if>
                                <c:if test="${penalite != null && penalite.arbitre != null}">
                                    <option value="${penalite.arbitre.id}">${penalite.arbitre.nom}</option>
                                </c:if>
                                <c:forEach items="${listArbitre}" var="arbitre">
                                    <c:if test="${penalite == null || penalite.arbitre.id != arbitre.id}">
                                        <option value="${arbitre.id}">${arbitre.nom}</option>
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
                <script>
                    <c:if test="${not empty errorClassement}">
                        alert('${errorClassement}');
                    </c:if>
                </script>
            </div>
        </div>
    </div>
</div>


</c:if>
</div>

<%@ include file="../shared/footer.jspf"%>
    