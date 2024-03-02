public class Composant {
    public final String categorie; // Variable public car elle est utilisée directement dans Main.java, mais final empêche la modification
    private String marque;
    private String nom;
    private double prix;
    private double rabais;

    public Composant(String categorie, String marque, String nom, double prix) { // Constructeur
        this.categorie = getCategorie(categorie);
        this.marque = marque;
        this.nom = nom;
        this.prix = prix;
    }

    public Composant(Composant original) { // Constructeur de copie
        this.categorie = getCategorie(original.categorie);
        this.marque = original.marque;
        this.nom = original.nom;
        this.prix = original.getPrix();
    }

    public Composant copier() { // Méthode pour copier cet objet
        return new Composant(this);
    }

    public String getCategorie(String categorie) { // Méthode pour formater la catégorie
        String categorieFormatee = "";
        if (categorie.trim().length() <= 3) {
            categorieFormatee = categorie.toUpperCase(); // Mettre en majuscule si catégorie est plus petit que 3 lettres (Ex. CPU et SSD)
        }
        else {
            categorieFormatee = categorie.substring(0, 1).toUpperCase() + categorie.substring(1, categorie.length()); // Mettre la première lettre en majuscule
        }
        categorieFormatee = categorieFormatee.trim();
        return categorieFormatee; // Catégorie formatée
    }

    public double getPrix() {
        return prix - (prix * rabais); // Retourner le prix en appliquant le rabais
    }

    public double getRabais() {
        return rabais;
    }

    public void setPrix(double prix) {
        double nouveauPrix = prix;
        if (nouveauPrix < 0) {
            nouveauPrix = 0; // prix minimum 0
        }
        this.prix = nouveauPrix;
    }

    public void setRabais(double rabais) {
        double nouveauRabais = rabais;
        if (nouveauRabais < 0) {
            nouveauRabais = 0; // rabais minimum 0 (0%)
        }
        else if (nouveauRabais > 1.00) {
            nouveauRabais = 1; // rabais maximum 1.00 (100%)
        }
        this.rabais = nouveauRabais;
    }

    public boolean estPareilQue(Composant composant) { // Méthode pour comparer cet composant avec un autre
        if (this.categorie.equals(composant.categorie) && this.marque.equals(composant.marque) && this.nom.equals(composant.nom)) {
            return true;
        }
        else {
            return false;
        }
    }

    public String toString() { // Méthode toString !
        return "[" + categorie + "] " + marque + " " + nom;
    }
}