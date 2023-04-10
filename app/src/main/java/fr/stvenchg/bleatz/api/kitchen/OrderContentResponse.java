package fr.stvenchg.bleatz.api.kitchen;

import java.util.List;

public class OrderContentResponse {
    private boolean success;
    private String message;
    private String idCommande;
    private List<CommandeContent> content;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getIdCommande() {
        return idCommande;
    }

    public List<CommandeContent> getContent() {
        return content;
    }

    public class CommandeContent {
        private int idMenu;
        private List<Menu> menus;
        private List<Burger> burgers;
        private List<Boisson> boissons;

        public int getIdMenu() {
            return idMenu;
        }

        public List<Menu> getMenus() {
            return menus;
        }

        public List<Burger> getBurgers() {
            return burgers;
        }

        public List<Boisson> getBoissons() {
            return boissons;
        }

        public class Menu {
            private int idMenu;
            private String prix;
            private int idBoisson;
            private int idBurger;

            public int getIdMenu() {
                return idMenu;
            }

            public String getPrix() {
                return prix;
            }

            public int getIdBoisson() {
                return idBoisson;
            }

            public int getIdBurger() {
                return idBurger;
            }
        }

        public class Burger {
            private int idBurger;
            private String nom;
            private String prix;
            private String description;
            private String image;
            private List<Ingredient> ingredient;

            public int getIdBurger() {
                return idBurger;
            }

            public String getNom() {
                return nom;
            }

            public String getPrix() {
                return prix;
            }

            public String getDescription() {
                return description;
            }

            public String getImage() {
                return image;
            }

            public List<Ingredient> getIngredient() {
                return ingredient;
            }

            public class Ingredient {
                private int idIngredient;
                private String prix;
                private String nom;

                public int getIdIngredient() {
                    return idIngredient;
                }

                public String getPrix() {
                    return prix;
                }

                public String getNom() {
                    return nom;
                }
            }
        }

        public class Boisson {
            private int idBoisson;
            private String nom;
            private String prix;
            private String taille;

            public int getIdBoisson() {
                return idBoisson;
            }

            public String getNom() {
                return nom;
            }

            public String getPrix() {
                return prix;
            }

            public String getTaille() {
                return taille;
            }
        }
    }
}
