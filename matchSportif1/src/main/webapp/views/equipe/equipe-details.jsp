<%@ include file="../shared/header.jspf"%>
<%@ include file="../shared/navigation.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="container mt-5">
    <h1 class="mb-5 text-center">Détails de l'équipe: <c:out value="${equipe.nom}" /></h1>
    
    <div class="row mb-4">
        <div class="col-lg-12">
            <div class="card">
                <div class="card-header bg-primary text-white">
                    <h3 class="mb-0">Informations de l'équipe</h3>
                </div>
                <div class="card-body">
                    <p class="card-text fs-4">Date Debut: <c:out value="${equipe.anneeNaissance}" /></p>
                    <!-- Ajoutez d'autres informations sur l'équipe ici si nécessaire -->
                </div>
            </div>
        </div>
    </div>
    
    <div class="row mb-4">
        <div class="col-lg-12">
            <div class="card">
                <div class="card-header bg-success text-white">
                    <h3 class="mb-0">Classement de l'équipe</h3>
                </div>
                <div class="card-body">
                    <table class="table table-hover fs-4">
                        <thead>
                            <tr>
                                <th>Points</th>
                                <th>Points à domicile</th>
                                <th>Points à l'extérieur</th>
                                <th>Matchs joués</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><c:out value="${classement.points}" /></td>
                                <td><c:out value="${classement.pointsDom}" /></td>
                                <td><c:out value="${classement.pointsExt}" /></td>
                                <td><c:out value="${classement.matchesPlayed}" /></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    
    <div class="row mb-4">
        <div class="col-lg-12">
            <div class="card">
                <div class="card-header bg-info text-white">
                    <h3 class="mb-0">Liste des joueurs</h3>
                </div>
                <div class="card-body">
                    <table class="table table-striped table-hover fs-4">
                        <thead>
                            <tr>
                                <th>Nom</th>
                                <th>Prénom</th>
                                <th>Date de naissance</th>
                                <th>Nationalité</th>
                                <th>Numéro de maillot</th>
                                <th>Poste</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="joueur" items="${equipe.joueursActuels}">
                                <tr>
                                    <td><c:out value="${joueur.nom}" /></td>
                                    <td><c:out value="${joueur.prenom}" /></td>
                                    <td><c:out value="${joueur.dateNaissance}" /></td>
                                    <td><c:out value="${joueur.nationalite}" /></td>
                                    <td><c:out value="${joueur.maillot}" /></td>
                                    <td><c:out value="${joueur.post}" /></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <div class="card">
                <div class="card-header bg-warning text-dark">
                    <h3 class="mb-0">Liste des entraîneurs</h3>
                </div>
                <div class="card-body">
                    <table class="table table-striped table-hover fs-4">
                        <thead>
                            <tr>
                                <th>Nom</th>
                                <th>Prénom</th>
                                <th>Nationalité</th>
                                <!-- Ajoutez d'autres en-têtes de colonnes si nécessaire -->
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="entraineur" items="${equipe.entraineurs}">
                                <tr>
                                    <td><c:out value="${entraineur.nom}" /></td>
                                    <td><c:out value="${entraineur.prenom}" /></td>
                                    <td><c:out value="${entraineur.nationalite}" /></td>
                                    <!-- Ajoutez d'autres cellules de données si nécessaire -->
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../shared/footer.jspf"%>