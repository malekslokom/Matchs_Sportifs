<%@ include file="../shared/header.jspf"%>
<%@ include file="../shared/navigation.jspf"%>
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3 ">
            <div class="panel panel panel-default">
                <c:if test="${rencontre != null}">
                    <div class="panel-heading">Modifier Rencontre</div>
                </c:if>
                <c:if test="${rencontre == null}">
                    <div class="panel-heading">Ajouter Rencontre</div>
                </c:if>

                <div class="panel-body">
                    <c:if test="${rencontre != null}">
                        <form action="<c:url value='/api/rencontres/update?id=${rencontre.idRencontre}'/>" method="post">
                    </c:if>
                    <c:if test="${rencontre == null}">
                        <form action="<c:url value='/api/rencontres/insert'/>" method="post">
                    </c:if>
					<c:if test="${rencontre != null}">
	                    <div class="form-group row">
	                        <label for="idRencontre" class="col-sm-2 col-form-label">ID</label>
	                        <div class="col-sm-10">
	                            <input type="text" class="form-control" id="idRencontre" name="idRencontre" value="<c:out value='${rencontre.idRencontre}' />" />
	                        </div>
	                    </div>
                     </c:if>

					<%-- <div class="form-group row">
					    <label for="equipe1" class="col-sm-2 col-form-label">Équipe Domicile</label>
					    <div class="col-sm-10">
					        <select class="form-control" id="equipe1" name="equipe1">
					            <option value="">Sélectionnez une équipe</option> 
					            <c:forEach items="${listEquipe}" var="equipe">
					                <c:choose>
					                   
					                    <c:when test="${rencontre != null && rencontre.equipeRencontres != null}">
										    <c:forEach var="equipeRencontre" items="${rencontre.equipeRencontres}">
										        <c:if test="${equipeRencontre.equipe.idEquipe == equipe.idEquipe}">
												    <option value="${equipe.idEquipe}" selected>${equipe.nom}</option>
												</c:if>
										    </c:forEach>
										</c:when>
					                    
					                    <c:otherwise>
					                        <option value="${equipe.idEquipe}">${equipe.nom}</option>
					                    </c:otherwise>
					                </c:choose>
					            </c:forEach>
					        </select>
					    </div>
					</div> --%>
					
					<div class="form-group row">
				    <label for="equipe1" class="col-sm-2 col-form-label">Équipe Domicile</label>
				    <div class="col-sm-10">
				        <select class="form-control" id="equipe1" name="equipe1">
				            <option value="">Sélectionnez une équipe</option> 
				            <c:forEach items="${listEquipe}" var="equipe" varStatus="loop">
				                <c:choose>
				                    <c:when test="${loop.index == 0}">
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
			
					
					
					<%-- <div class="form-group row">
					    <label for="equipe2" class="col-sm-2 col-form-label">Équipe Extérieur</label>
					    <div class="col-sm-10">
					        <select class="form-control" id="equipe2" name="equipe2">
					            <option value="">Sélectionnez une équipe</option> 
					            <c:forEach items="${listEquipe}" var="equipe">
					                <c:choose>
					                    <c:when test="${rencontre != null && rencontre.equipeRencontres != null && rencontre.equipeRencontres.idEquipe == equipe.idEquipe}">
					                        <option value="${equipe.idEquipe}" selected>${equipe.nom}</option>
					                    </c:when>
					                    <c:when test="${rencontre != null && rencontre.equipeRencontres != null}">
										    <c:forEach var="equipeRencontre" items="${rencontre.equipeRencontres}">
										        <c:if test="${equipeRencontre.equipe.idEquipe == equipe.idEquipe}">
												    <option value="${equipe.idEquipe}" selected>${equipe.nom}</option>
												</c:if>
										    </c:forEach>
										</c:when>
					                    
					                    <c:otherwise>
					                        <option value="${equipe.idEquipe}">${equipe.nom}</option>
					                    </c:otherwise>
					                </c:choose>
					            </c:forEach>
					        </select>
					    </div>
					</div> --%>
					
					<div class="form-group row">
				    <label for="equipe2" class="col-sm-2 col-form-label">Équipe Extérieur</label>
				    <div class="col-sm-10">
				        <select class="form-control" id="equipe2" name="equipe2">
				            <option value="">Sélectionnez une équipe</option> 
				            <c:forEach items="${listEquipe}" var="equipe" varStatus="loop">
				                <c:choose>
				                    <c:when test="${loop.index == 1}">
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
                        <label for="dateDebut" class="col-sm-2 col-form-label">Date Début</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="dateDebut" name="dateDebut" placeholder="YYYY-MM-AA" value="<c:out value='${rencontre.dateDebut}' />" />
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="heureDebut" class="col-sm-2 col-form-label">Heure Début</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="heureDebut" name="heureDebut" placeholder="HH:mm" value="<c:out value='${rencontre.heureDebut}' />" />
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="dateFin" class="col-sm-2 col-form-label">Date Fin</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="dateFin" name="dateFin" placeholder="YYYY-MM-AA" value="<c:out value='${rencontre.dateFin}' />" />
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="heureFin" class="col-sm-2 col-form-label">Heure Fin</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="heureFin" name="heureFin" placeholder="HH:mm" value="<c:out value='${rencontre.heureFin}' />" />
                        </div>
                    </div>

                     <div class="form-group row">
                        <label for="lieuAccueil" class="col-sm-2 col-form-label">Lieu Accueil</label>
                        <div class="col-sm-10">
                            <select class="form-control" id="lieuAccueil" name="lieuAccueil">
                                <option value="">Sélectionnez un lieu d'accueil</option>
                                <c:forEach items="${listLieuAcceuil}" var="lieu">
                                    <c:choose>
                                        <c:when test="${rencontre != null && rencontre.lieuAcceuil != null && rencontre.lieuAcceuil.idlieuAcceuil == lieu.idlieuAcceuil}">
                                            <option value="${lieu.idlieuAcceuil}" selected>${lieu.nom}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${lieu.idlieuAcceuil}">${lieu.nom}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
					<%-- <div class="form-group row">
					    <label for="arbitres" class="col-sm-2 col-form-label">Sélectionnez les arbitres</label>
					    <div class="col-sm-10">
					        <c:forEach items="${allArbitres}" var="arbitre">
					            <div class="form-check">
					                <input type="checkbox" class="form-check-input" id="arbitre_${arbitre.id}" name="arbitres" value="${arbitre.id}">
					                <label class="form-check-label" for="arbitre_${arbitre.id}">${arbitre.nom}</label>
					            </div>
					        </c:forEach>
					    </div>
					</div> --%>
					
		<%-- 			<div class="form-group row">
				    <label for="arbitres" class="col-sm-2 col-form-label">Sélectionnez les arbitres</label>
				    <div class="col-sm-10">
				        <c:forEach items="${allArbitres}" var="arbitre">
				            <div class="form-check">
				                <input type="checkbox" class="form-check-input" id="arbitre_${arbitre.id}" name="arbitres" value="${arbitre.id}"
				                       <c:if test="${rencontre.arbitres.contains(arbitre)}">checked</c:if>>
				                <label class="form-check-label" for="arbitre_${arbitre.id}">${arbitre.nom}</label>
				            </div>
				        </c:forEach>
				    </div>
				</div> --%>


<div class="form-group row">
    <label for="arbitres" class="col-sm-2 col-form-label">Sélectionnez les arbitres</label>
    <div class="col-sm-10">
        <c:forEach items="${allArbitres}" var="arbitre">
        
            <div class="form-check">
                <input type="checkbox" class="form-check-input" id="arbitre_${arbitre.id}" name="arbitres" value="${arbitre.id}"
                     <c:if test="${rencontre.arbitres != null and rencontre.arbitres.contains(arbitre)}">checked</c:if>>
                <label class="form-check-label" for="arbitre_${arbitre.id}">${arbitre.nom} ${arbitre.prenom}</label>
            </div>
        </c:forEach>
    </div>
</div>

                    <div class="form-group row">
                        <label for="urlBilletrie" class="col-sm-2 col-form-label">URL Billetrie</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="urlBilletrie" name="urlBilletrie" placeholder="URL Billetrie" value="<c:out value='${rencontre.urlBilletrie}' />" />
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
<%@ include file="../shared/footer.jspf"%>
