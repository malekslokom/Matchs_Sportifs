<%@ include file="../shared/header.jspf"%>
<%@ include file="../shared/navigation.jspf"%>
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3 ">
            <div class="panel panel panel-default">
            
                <c:if test="${lieuAcceuil != null}">
                    <div class="panel-heading">Modifier Lieu d'Acceuil</div>
                </c:if>
                <c:if test="${lieuAcceuil == null}">
                    <div class="panel-heading">Ajouter Lieu d'Acceuil</div>
                </c:if>
                
                <div class="panel-body">
                    <c:if test="${lieuAcceuil != null}">
                        <form action="<c:url value='/api/lieuxAcceuil/update'/>" method="post">
                            <input type="hidden" name="id" value="<c:out value='${lieuAcceuil.idlieuAcceuil}' />" />
                    </c:if>
                    <c:if test="${lieuAcceuil == null}">
                        <form action="<c:url value='/api/lieuxAcceuil/insert'/>" method="post">
                    </c:if>

                    <div class="form-group row">
                        <label for="type" class="col-sm-2 col-form-label">Type</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="type" name="type" placeholder="Type" value="<c:out value='${lieuAcceuil.type}' />" />
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="nom" class="col-sm-2 col-form-label">Nom</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="nom" name="nom" placeholder="Nom" value="<c:out value='${lieuAcceuil.nom}' />" />
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="adresse" class="col-sm-2 col-form-label">Adresse</label>
                        <div class="col-sm-10">
                            <!-- Add dropdown/select for selecting an existing Adresse or input fields for a new one -->
                            <!-- For example, if selecting an existing Adresse, you can use a dropdown like this: -->
                            <!-- <select name="idAdresse" class="form-control">
                                    <c:forEach var="adresse" items="${listOfAdresses}">
                                        <option value="<c:out value='${adresse.idAdresse}' />"><c:out value='${adresse.rue}' />, <c:out value='${adresse.ville}' /></option>
                                    </c:forEach>
                                </select> -->

                            <!-- If creating a new Adresse, provide input fields like below -->
                            <c:if test="${lieuAcceuil != null}">
                            <input type="text" class="form-control" id="idAdresse" name="idAdresse" placeholder="id Adresse" value="<c:out value='${lieuAcceuil.adresse.idAdresse}' />" />
                            </c:if>
                            <input type="text" class="form-control" id="rue" name="rue" placeholder="Rue" value="<c:out value='${lieuAcceuil.adresse.rue}' />" />
                             <input type="text" class="form-control" id="codePostal" name="codePostal" placeholder="Code Postal"  value="<c:out value='${lieuAcceuil.adresse.codePostal}' />" />
                             <input type="text" class="form-control" id="ville" name="ville" placeholder="Ville"  value="<c:out value='${lieuAcceuil.adresse.ville}' />" />
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="courriel" class="col-sm-2 col-form-label">Courriel</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="courriel" name="courriel" placeholder="Courriel" value="<c:out value='${lieuAcceuil.courriel}' />" />
                        </div>
                    </div>

					<div class="form-group row">
                        <label for="infosPMR" class="col-sm-2 col-form-label">infosPMR</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="infosPMR" name="infosPMR" placeholder="Infos PMR" value="<c:out value='${lieuAcceuil.infosPMR}' />" />
                        </div>
                    </div>
                    
                    <div class="form-group row">
                        <label for="commentaires" class="col-sm-2 col-form-label">Commentaires</label>
                        <div class="col-sm-10">
                        	<textarea class="form-control" id="commentaires" rows="3" placeholder="Commentaires" value="<c:out value='${lieuAcceuil.courriel}' />"></textarea>
                        </div>
                    </div>
                    
                    <div class="form-group row">
                        <label for="telephone" class="col-sm-2 col-form-label">Telephone</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="telephone" name="telephone" placeholder="Telephone" value="<c:out value='${lieuAcceuil.telephone}' />" />
                        </div>
                    </div>
                    
                     <div class="form-group row">
                        <label for="siteInternet" class="col-sm-2 col-form-label">Site Internet</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="siteInternet" name="siteInternet" placeholder="Site Internet" value="<c:out value='${lieuAcceuil.siteInternet}' />" />
                        </div>
                    </div>
                    
                     <div class="form-group row">
                        <label for="capacite" class="col-sm-2 col-form-label">Capacite</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="capacite" name="capacite" placeholder="Capacite" value="<c:out value='${lieuAcceuil.capacite}' />" />
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
