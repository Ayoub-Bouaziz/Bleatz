package fr.stvenchg.bleatz.api.panier;

import java.util.List;



public class CartResponse {
    private boolean success;
    private String message;
    private int idPanier;
    private double total_price;
    private int menu_count;
    private List<MenuContent> content;

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

    public int getIdPanier() {
        return idPanier;
    }

    public void setIdPanier(int idPanier) {
        this.idPanier = idPanier;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public int getMenu_count() {
        return menu_count;
    }

    public void setMenu_count(int menu_count) {
        this.menu_count = menu_count;
    }

    public List<MenuContent> getContent() {
        return content;
    }

    public void setContent(List<MenuContent> content) {
        this.content = content;
    }

    public class MenuContent {
        private int idMenu;
        private List<MenuItem> content;

        public int getIdMenu() {
            return idMenu;
        }

        public void setIdMenu(int idMenu) {
            this.idMenu = idMenu;
        }

        public List<MenuItem> getContent() {
            return content;
        }

        public void setContent(List<MenuItem> content) {
            this.content = content;
        }
    }

    public class MenuItem {
        private int idBurger;
        private int idBoisson;
        private double prix;
        private Burger burger;
        private Boisson boisson;

        public int getIdBurger() {
            return idBurger;
        }

        public void setIdBurger(int idBurger) {
            this.idBurger = idBurger;
        }

        public int getIdBoisson() {
            return idBoisson;
        }

        public void setIdBoisson(int idBoisson) {
            this.idBoisson = idBoisson;
        }

        public double getPrix() {
            return prix;
        }

        public void setPrix(double prix) {
            this.prix = prix;
        }

        public Burger getBurger() {
            return burger;
        }

        public void setBurger(Burger burger) {
            this.burger = burger;
        }

        public Boisson getBoisson() {
            return boisson;
        }

        public void setBoisson(Boisson boisson) {
            this.boisson = boisson;
        }
    }

    public class Burger {
        private int idBurger;
        private String nom;
        private double prix;
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

        public double getPrix() {
            return prix;
        }

        public void setPrix(double prix) {
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

    public class Boisson {
        private int idBoisson;
        private String nom;
        private double prix;
        private String taille;

        // Getters
        public int getIdBoisson() {
            return idBoisson;
        }

        public String getNom() {
            return nom;
        }

        public double getPrix() {
            return prix;
        }

        public String getTaille() {
            return taille;
        }
    }
}

