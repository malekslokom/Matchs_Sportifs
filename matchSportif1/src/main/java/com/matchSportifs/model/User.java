package com.matchSportifs.model;

import javax.persistence.*;

import com.matchSportifs.dao.UserDAO;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="role")
    private String role; 
    
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	
	public static void main(String[] args) {
        // Code pour créer un utilisateur admin lorsqu'on exécute cette classe

        UserDAO userDAO = new UserDAO();

        // Vérifier si l'admin existe déjà pour éviter la duplication
        User existingAdmin = userDAO.findUserByEmail("admin@example.com");
        if (existingAdmin == null) {
            // Créer un nouvel utilisateur admin
            User adminUser = new User();
            adminUser.setEmail("admin@example.com");
            adminUser.setPassword("adminPassword"); // Vous devriez stocker les mots de passe de manière sécurisée
            adminUser.setRole("ADMIN");

            // Sauvegarder l'utilisateur admin dans la base de données
            userDAO.saveUser(adminUser);

            System.out.println("Admin user created successfully!");
        } else {
            System.out.println("Admin user already exists.");
        }
    }
    
}