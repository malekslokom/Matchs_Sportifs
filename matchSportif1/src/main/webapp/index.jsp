<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>Match Sportif</title>
  <meta content="" name="description">
  <meta content="" name="keywords">

  <!-- Favicons -->
  <link href="assets/img/sport.png" rel="icon">
  <link href="assets/img/apple-touch-icon.png" rel="apple-touch-icon">

  <!-- Google Fonts -->
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Raleway:300,300i,400,400i,500,500i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

  <!-- Vendor CSS Files -->
  <link href="assets/vendor/aos/aos.css" rel="stylesheet">
  <link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
  <link href="assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
  <link href="assets/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
  <link href="assets/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">

  <!-- Template Main CSS File -->
  <link href="assets/css/style.css" rel="stylesheet">

</head>

<body>


  <!-- ======= Header ======= -->
  <header id="header" class="d-flex align-items-center">
    <div class="container d-flex align-items-center justify-content-between">

      <h1 class="logo"><a href="index.html">Match Sportif</a></h1>
      <!-- Uncomment below if you prefer to use an image logo -->
      <!-- <a href="index.html" class="logo"><img src="assets/img/logo.png" alt="" class="img-fluid"></a>-->

      <nav id="navbar" class="navbar">
        <ul>
          <li class="nav-link scrollto active"><a href="<c:url value='/accueil'/>">Accueil</a></li>
                <li><a class="nav-link scrollto "  href="<c:url value='/api/joueurs'/>">Joueurs</a></li>
                <li><a class="nav-link scrollto" href="<c:url value='/api/arbitres'/>">Arbitres</a></li>
                <li><a class="nav-link scrollto" href="<c:url value='/api/entraineurs'/>">Entraineurs</a></li>
                <li><a class="nav-link scrollto" href="<c:url value='/api/equipes'/>">Equipes</a></li>
                <li><a class="nav-link scrollto"  href="<c:url value='/api/lieuxAcceuil'/>">Lieu d'acceuil</a></li>
                <li><a class="nav-link scrollto" href="<c:url value='/api/classements'/>">Classements</a></li>
               <li><a class="nav-link scrollto" href="<c:url value='/api/rencontres'/>">Rencontres</a></li>
         		<li><a class="nav-link" href="#" data-bs-toggle="modal" data-bs-target="#loginModal">Connexion</a></li>
            
          </li>
          <li><a class="nav-link scrollto" href="#contact">Contact</a></li>
        </ul>
        <i class="bi bi-list mobile-nav-toggle"></i>
      </nav><!-- .navbar -->

    </div>
  </header><!-- End Header -->

  <!-- ======= Hero Section ======= -->
  <section id="hero" class="d-flex align-items-center">
    <div class="container position-relative" data-aos="fade-up" data-aos-delay="500">
      <h1>Bienvenue dans Match Sportif</h1>
      <h2>La plateforme pour suivre tous vos matchs et équipes préférés</h2>
      <a href="#about" class="btn-get-started scrollto">Découvrir</a>
    </div>
  </section><!-- End Hero -->
  <main id="main">

    <!-- ======= About Section ======= -->
    <section id="about" class="about">
      <div class="container">

        <div class="row">
          <div class="col-lg-6 order-1 order-lg-2" data-aos="fade-left">
            <img src="assets/img/sport.jpg" class="img-fluid" alt="">
          </div>
          <div class="col-lg-6 pt-4 pt-lg-0 order-2 order-lg-1 content" data-aos="fade-right">
            <h3>Découvrez Match Sportif</h3>
            <p class="fst-italic">
              Match Sportif est votre destination pour les dernières nouvelles, les résultats en direct et les analyses des matchs sportifs.
            </p>
            <ul>
              <li><i class="bi bi-check-circle"></i> Suivez les résultats en temps réel.</li>
              <li><i class="bi bi-check-circle"></i> Restez informé avec des analyses détaillées.</li>
              <li><i class="bi bi-check-circle"></i> Rejoignez une communauté passionnée de sport.</li>
            </ul>
            <p>
                   Que vous soyez un fan de football, de basketball ou de tout autre sport, Match Sportif vous offre une couverture complète pour rester au cœur de l'action.
            </p>
          </div>
        </div>

      </div>
    </section><!-- End About Section -->

   <!-- ======= Why Us Section ======= -->
<section id="why-us" class="why-us">
  <div class="container">

    <div class="row">

      <div class="col-lg-4" data-aos="fade-up">
        <div class="box">
          <span>01</span>
          <h4>Accès en Temps Réel</h4>
          <p>Obtenez les scores, les statistiques et les mises à jour des matchs en temps réel pour ne jamais manquer un moment important.</p>
        </div>
      </div>

      <div class="col-lg-4 mt-4 mt-lg-0" data-aos="fade-up" data-aos-delay="150">
        <div class="box">
          <span>02</span>
          <h4>Couverture Complète</h4>
          <p>De la ligue locale aux compétitions internationales, suivez vos équipes et sports favoris à travers le monde.</p>
        </div>
      </div>

      <div class="col-lg-4 mt-4 mt-lg-0" data-aos="fade-up" data-aos-delay="300">
        <div class="box">
          <span>03</span>
          <h4>Communauté Passionnée</h4>
          <p>Rejoignez une communauté de fans de sport passionnés, partagez vos opinions et connectez-vous avec d'autres supporters.</p>
        </div>
      </div>

    </div>

  </div>
</section><!-- End Why Us Section -->

     <!-- ======= Section des Services ======= -->
<section id="services" class="services">
  <div class="container">

<div class="section-title">
  <span>Nos Fonctionnalités</span>
  <h2>Fonctionnalités</h2>
  <p>Découvrez les services que notre application de suivi des matchs sportifs propose</p>
</div>

<div class="row">
  <div class="col-lg-4 col-md-6 d-flex align-items-stretch" data-aos="fade-up">
    <div class="icon-box">
      <div class="icon"><i class="bx bx-map"></i></div>
      <h4><a href="#">Lieux d'Accueil</a></h4>
      <p>Explorez et gérez les lieux d'accueil tels que patinoires, gymnases, stades, etc., avec leurs informations détaillées.</p>
    </div>
  </div>

  <div class="col-lg-4 col-md-6 d-flex align-items-stretch mt-4 mt-md-0" data-aos="fade-up" data-aos-delay="150">
    <div class="icon-box">
      <div class="icon"><i class="bx bx-medal"></i></div>
      <h4><a href="#">Joueurs</a></h4>
      <p>Consultez la liste des joueurs, y compris leurs détails personnels, leur équipe actuelle, et leur historique d'équipes précédentes.</p>
    </div>
  </div>

  <div class="col-lg-4 col-md-6 d-flex align-items-stretch mt-4 mt-lg-0" data-aos="fade-up" data-aos-delay="300">
    <div class="icon-box">
      <div class="icon"><i class="bx bx-group"></i></div>
      <h4><a href="#">Équipes</a></h4>
      <p>Gérez les équipes, incluant la liste des joueurs, les entraîneurs, et d'autres détails pertinents.</p>
    </div>
  </div>

  <div class="col-lg-4 col-md-6 d-flex align-items-stretch mt-4" data-aos="fade-up" data-aos-delay="450">
    <div class="icon-box">
      <div class="icon"><i class="bx bx-calendar"></i></div>
      <h4><a href="#">Rencontres</a></h4>
      <p>Consultez les informations sur les rencontres, y compris le lieu, la date, les équipes participantes, les buts marqués, et plus encore.</p>
    </div>
  </div>

  <div class="col-lg-4 col-md-6 d-flex align-items-stretch mt-4" data-aos="fade-up" data-aos-delay="600">
    <div class="icon-box">
      <div class="icon"><i class="bx bx-trophy"></i></div>
      <h4><a href="#">Classement</a></h4>
      <p>Accédez au tableau de classement des équipes, avec des détails sur les points, les points à domicile, les points à l'extérieur, et le nombre de matchs joués.</p>
    </div>
  </div>
<div class="col-lg-4 col-md-6 d-flex align-items-stretch mt-4" data-aos="fade-up" data-aos-delay="750">
  <div class="icon-box">
    <div class="icon"><i class="bx bx-dumbbell"></i></div>
    <h4><a href="#">Entraîneurs</a></h4>
    <p>Gérez les entraîneurs, consultez leurs informations personnelles et leurs contributions à différentes équipes.</p>
  </div>
</div>


</div>

  </div>
</section><!-- Fin de la Section des Services -->
<!-- ======= Section de Contact ======= -->
<section id="contact" class="contact">
  <div class="container">

    <div class="section-title">
      <span>Contact</span>
      <h2>Contact</h2>
      <p>Si vous avez des questions ou des commentaires, n'hésitez pas à nous contacter.</p>
    </div>

    <div class="row" data-aos="fade-up">
      <div class="col-lg-6">
        <div class="info-box mb-4">
          <i class="bx bx-map"></i>
          <h3>Notre Adresse</h3>
          <p>A108 Adam Street, New York, NY 535022</p>
        </div>
      </div>

      <div class="col-lg-3 col-md-6">
        <div class="info-box mb-4">
          <i class="bx bx-envelope"></i>
          <h3>Email</h3>
          <p>contact@example.com</p>
        </div>
      </div>

      <div class="col-lg-3 col-md-6">
        <div class="info-box mb-4">
          <i class="bx bx-phone-call"></i>
          <h3>Nous Appeler</h3>
          <p>+1 5589 55488 55</p>
        </div>
      </div>
    </div>

    <div class="row" data-aos="fade-up">
      <div class="col-lg-6">
        <iframe class="mb-4 mb-lg-0" src="https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d12097.433213460943!2d-74.0062269!3d40.7101282!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0xb89d1fe6bc499443!2sDowntown+Conference+Center!5e0!3m2!1smk!2sbg!4v1539943755621" frameborder="0" style="border:0; width: 100%; height: 384px;" allowfullscreen></iframe>
      </div>

      <div class="col-lg-6">
        <form action="forms/contact.php" method="post" role="form" class="php-email-form">
          <div class="row">
            <div class="col-md-6 form-group">
              <input type="text" name="name" class="form-control" id="name" placeholder="Votre Nom" required>
            </div>
            <div class="col-md-6 form-group mt-3 mt-md-0">
              <input type="email" class="form-control" name="email" id="email" placeholder="Votre Email" required>
            </div>
          </div>
          <div class="form-group mt-3">
            <input type="text" class="form-control" name="subject" id="subject" placeholder="Sujet" required>
          </div>
          <div class="form-group mt-3">
            <textarea class="form-control" name="message" rows="5" placeholder="Message" required></textarea>
          </div>
          <div class="my-3">
            <div class="loading">Chargement</div>
            <div class="error-message"></div>
            <div class="sent-message">Votre message a été envoyé. Merci !</div>
          </div>
          <div class="text-center"><button type="submit">Envoyer le Message</button></div>
        </form>
      </div>
    </div>

  </div>
</section><!-- Fin de la Section de Contact -->


  </main><!-- End #main -->

   <footer id="footer">
    <div class="container">
      <div class="credits">
        Conçu et développé par l'équipe de Match Sportif
      </div>
    </div>
  </footer><!-- End Footer -->
<!-- Modal Connexion -->
<div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="loginModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="loginModalLabel">Connexion</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
<form action="<c:url value='/api/users/login'/>" method="post">
          <div class="mb-3">
            <label for="email" class="form-label">Adresse Email</label>
            <input type="email" class="form-control" id="email"name="email" required>
          </div>
          <div class="mb-3">
            <label for="password" class="form-label">Mot de passe</label>
            <input type="password" class="form-control" id="password" name="password" required>
          </div>
          <button type="submit" class="btn btn-primary">Se connecter</button>
        </form>
      </div>
    </div>
  </div>
</div>
  <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>
  <div id="preloader"></div>

  <!-- Vendor JS Files -->
  <script src="assets/vendor/aos/aos.js"></script>
  <script src="assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="assets/vendor/glightbox/js/glightbox.min.js"></script>
  <script src="assets/vendor/isotope-layout/isotope.pkgd.min.js"></script>
  <script src="assets/vendor/swiper/swiper-bundle.min.js"></script>
  <script src="assets/vendor/php-email-form/validate.js"></script>

  <!-- Template Main JS File -->
  <script src="assets/js/main.js"></script>

</body>

</html>