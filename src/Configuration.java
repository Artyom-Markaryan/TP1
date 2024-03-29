public class Configuration {
    private String description;
    private double prixMaximum;
    private int nbComposants;
    private int nbMaxComposants = 20;
    private Composant[] tableauComposants = new Composant[nbMaxComposants];

    public Configuration(String description, double prixMaximum, Composant[] composants) { // Constructeur
        this.description = description;
        this.prixMaximum = prixMaximum;
        if (prixMaximum < 0) {
            this.prixMaximum = 0; // Mettre le prix maximum à 0 si le nombre fourni dans le paramètre est négative
        }
        for (int i = 0; i < tableauComposants.length; i++) {
            if (i <= composants.length - 1) {
                tableauComposants[i] = composants[i];
                nbComposants++;
            }
        }
    }

    public Configuration(Configuration original) { // Constructeur de copie
        this.description = original.description;
        this.prixMaximum = original.prixMaximum;
        this.tableauComposants = new Composant[nbMaxComposants];
        for (int i = 0; i < tableauComposants.length; i++) {
            if (original.tableauComposants[i] != null) {
                tableauComposants[i] = new Composant(original.tableauComposants[i]);
                nbComposants++;
            }
        }
    }

    public Configuration copier() { // Méthode pour copier cet objet
        return new Configuration(this);
    }

    public String getDescription() {
        return description;
    }

    public double getPrixMaximum() {
        return prixMaximum;
    }

    public int getNbComposants() {
        return nbComposants;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrixMax(double prixMaximum) {
        if (prixMaximum < 0) {
            prixMaximum = 0; // Empêcher un nombre négative
        }
        this.prixMaximum = prixMaximum;
    }

    public double calculerTotal(double taxe) { // Calculer le prix total de la configuration avec taxe
        double prixSansTaxe = 0;
        for (int i = 0; i < tableauComposants.length; i++) {
            if (tableauComposants[i] != null) {
                prixSansTaxe += tableauComposants[i].getPrix();
            }
        }
        double prixAvecTaxe = prixSansTaxe + (prixSansTaxe * taxe);
        return prixAvecTaxe;
    }

    public Composant rechercher(String categorie) { // Rechercher un composant dans la configuration par catégorie
        for (int i = 0; i < tableauComposants.length; i++) {
            if (tableauComposants[i] != null) {
                categorie = tableauComposants[i].getCategorie(categorie);
                if (tableauComposants[i].categorie.equals(categorie)) {
                    return tableauComposants[i]; // Retourne le composant trouvé
                }
            }
        }
        return null; // Retourne null si rien a été trouvé
    }

    private int positionLibre() { // Méthode pour trouver une position libre dans le tableau de composants
        for (int i = 0; i < tableauComposants.length; i++) {
            if (tableauComposants[i] == null) {
                return i;
            }
        }
        return -1; // Retourne -1 si aucune position libre
    }

    public boolean ajouter(Composant composant) { // Ajouter un composant dans la configuration
        if (nbMaxComposants >= nbComposants && rechercher(composant.categorie) == null && (prixMaximum == 0 || prixMaximum >= calculerTotal(0) + composant.getPrix())) {
            tableauComposants[positionLibre()] = new Composant(composant);
            nbComposants++;
            return true;
        }
        return false;
    }

    public boolean retirer(Composant composant) { // Retirer un composant dans la configuration
        for (int i = 0; i < tableauComposants.length; i++) {
            if (tableauComposants[i] != null && tableauComposants[i].estPareilQue(composant)) {
                tableauComposants[i] = null;
                nbComposants--;
                return true;
            }
        }
        return false;
    }

    public boolean remplacer(Composant composant) { // Remplacer un composant dans la configuration
        Composant vieuxComposant = rechercher(composant.categorie);
        if (vieuxComposant != null && vieuxComposant.categorie.equals(composant.categorie)) {
            retirer(vieuxComposant); // Rétirer le vieux composant
            if (ajouter(composant) != false) // Vérifier si l'ajout du nouveau composant est possible
                ajouter(composant); // Ajouter le nouveau composant
            else {
                ajouter(vieuxComposant); // Réajouter le vieux composant
                return false; // Refuser le remplacement
            }
            return true;
        }
        return false;
    }

    public void afficherTout() { // Afficher toute la configuration
        String premiereLigne = "";
        if (prixMaximum != 0) {
            premiereLigne = description + " (max " + prixMaximum + "$) :";
        }
        else {
            premiereLigne = description + " (montant illimité) :";
        }
        System.out.println(premiereLigne);
        int j = 1;
        for (int i = 0; i < tableauComposants.length; i++) {
            if (tableauComposants[i] != null) {
                System.out.println(j++ + ": " + tableauComposants[i].toString() + " (" + tableauComposants[i].getPrix() + "$)");
            }
        }
    }
}