package fr.stvenchg.bleatz.api.menu;

import fr.stvenchg.bleatz.api.burger.BurgerResponse;

public class MenuResponse {

    private boolean success;


    private int idMenu;
    private String prix;
    private int idBoisson;
    private int idBurger;
    private BurgerResponse burger;

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public int getIdBoisson() {
        return idBoisson;
    }

    public void setIdBoisson(int idBoisson) {
        this.idBoisson = idBoisson;
    }

    public int getIdBurger() {
        return idBurger;
    }

    public void setIdBurger(int idBurger) {
        this.idBurger = idBurger;
    }
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public BurgerResponse getBurger() {
        return burger;
    }

    public void setBurger(BurgerResponse burger) {
        this.burger = burger;
    }
    public class Menu {
        private int id;
        private double price;
        private BurgerResponse.Burger burger;

        private String nomBoisson;

        public Menu(int id, double price, BurgerResponse.Burger burger, String drink) {
            this.id++;
            this.price = price;
            this.burger = burger;
            this.nomBoisson = drink;
        }

        public int getId() {
            return id;
        }

        public double getPrice() {
            return price;
        }

        public BurgerResponse.Burger getBurger() {
            return burger;
        }

        public String getDrink() {
            return nomBoisson;
        }
    }

}
