package fr.stvenchg.bleatz.api.burger;

import java.util.List;

public class BurgerResponse {
    private boolean success;
    private String message;
    private List<Burger> burgers;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Burger> getBurgers() {
        return burgers;
    }

    public void setBurgers(List<Burger> burgers) {
        this.burgers = burgers;
    }

    public static class Burger {
        private int idBurger;
        private String nom;
        private String prix;
        private String description;
        private String image;

        public int getIdBurger() {
            return idBurger;
        }

        public void setIdBurger(int idBurger) {
            this.idBurger = idBurger;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getPrix() {
            return prix;
        }

        public void setPrix(String prix) {
            this.prix = prix;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
