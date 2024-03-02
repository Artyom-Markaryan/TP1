/* INSCRIVEZ VOTRE (OU VOS) PRÉNOM ET NOM CI-DESSOUS
1: Artyom Markaryan
2: -
NE MODIFIEZ PAS LE RESTE DE CE FICHIER! Il ne sera pas utilisé lors de la correction.
(sauf si c'est pour mettre certaines sections en commentaire le temps de tester votre code)
*/

public class Main {
    public static void main(String[] args) {
        // Initialisation des composants
        Composant core13600k = new Composant("CPU", "Intel", "Core i5-13600K", 330);
        Composant ryzen5700x = new Composant("CPU", "AMD", "Ryzen 5 5700X", 260);
        Composant ryzen7800x3d = new Composant("CPU", "AMD", "Ryzen 7 7800X3D", 500);

        Composant asusB760 = new Composant("Carte mère", "Asus", "ROG Strix B760", 200);
        Composant msiB550 = new Composant("Carte mère", "MSI", "B550 Gaming Wifi", 150);

        Composant tridentzDDR4 = new Composant("Ram", "GSkill", "Trident-Z DDR4 32GB", 135);
        Composant tridentzDDR5 = new Composant("Ram", "GSkill", "Trident-Z DDR5 16GB", 90);

        Composant samsung980 = new Composant(" ssd", "Samsung", "980 Pro 2TB", 250);
        Composant wdSN850x = new Composant("ssd ", "Western Digital", "SN850X 1TB", 100);

        Composant asusRTX4060 = new Composant("gpu", "Asus", "RTX 4060", 460);
        Composant gbRTX4060 = new Composant("GPU", "Gigabyte", "RTX 4060", 400);

        commencerTest("Affichage des configurations initiales");

        // Initialisation des configurations
        Configuration config0 = new Configuration("src.Configuration vide", 0, new Composant[]{});
        config0.afficherTout();
        afficherTotal(config0);

        Configuration config1 = new Configuration("Build Intel Gen13", 1250, new Composant[]{core13600k, asusB760, tridentzDDR5, asusRTX4060});
        config1.afficherTout();
        afficherTotal(config1);

        Configuration config2 = new Configuration("Build AMD Gen5", 1000, new Composant[]{ryzen5700x, msiB550});
        config2.afficherTout();
        afficherTotal(config2);

        commencerTest("Recherche et manipulation de composants");

        Composant cpu = config1.rechercher("CPU").copier(); // Remarquez l'appel à copier()
        System.out.println(cpu);
        System.out.println(cpu.categorie);
        System.out.println(cpu.estPareilQue(core13600k));

        Composant mobo = config2.rechercher("carte mère").copier();
        System.out.println(mobo);
        System.out.println(mobo.getPrix());
        System.out.println(mobo.getRabais());

        Composant ram = config1.rechercher("ram").copier();
        System.out.println(ram);
        ram.setPrix(110);
        ram.setRabais(0.25);
        System.out.println(ram.categorie);
        System.out.println(ram.getPrix());
        System.out.println(ram.getRabais());
        System.out.println(ram.estPareilQue(tridentzDDR5));

        Composant ssd = config2.rechercher("ssd"); // Devrait retourner null
        System.out.println(ssd);

        commencerTest("Ajouts de composants");

        // Ajouts acceptés
        config2.ajouter(tridentzDDR4);
        config2.ajouter(wdSN850x);

        gbRTX4060.setRabais(0.20);
        System.out.printf("# Rabais de %.0f%% sur %s (nouveau prix: %.2f$)\n", gbRTX4060.getRabais() * 100, gbRTX4060, gbRTX4060.getPrix());
        config2.ajouter(gbRTX4060);

        System.out.println();
        config2.afficherTout();
        afficherTotal(config2);

        // Ajouts refusés
        config2.ajouter(ryzen7800x3d); // CPU différent déjà présent
        config2.ajouter(gbRTX4060); // GPU identique déjà présent (ajouté précédemment)
        config1.ajouter(samsung980); // Prix maximal dépassé

        commencerTest("Retraits de composants");

        // Retraits acceptés
        config2.retirer(tridentzDDR4);
        config2.retirer(gbRTX4060.copier()); // Retrait d'un composant à partir d'une copie

        Composant cpu2 = ryzen5700x.copier();
        cpu2.setPrix(220);
        config2.retirer(cpu2); // Retrait possible malgré un prix différent sur la copie

        System.out.println();
        config2.afficherTout();
        afficherTotal(config2);

        // Retraits refusés
        config1.retirer(tridentzDDR4); // src.Composant absent
        config1.retirer(gbRTX4060); // src.Composant absent
        config2.retirer(ryzen5700x); // src.Composant absent (déjà retiré précédemment)

        commencerTest("Copies de configurations");

        Configuration config3 = config2.copier();
        config3.afficherTout();
        afficherTotal(config3);

        config3.setDescription("Build AMD Gen7");
        config3.ajouter(ryzen7800x3d);
        config3.retirer(msiB550);

        Composant ram2 = config1.rechercher("RAM").copier();
        ram2.setRabais(0.15); // Ne devrait pas affecter la config 1 (remarquez l'appel à copier() ci-dessus)
        config3.ajouter(ram2);

        Composant ssd2 = config3.rechercher("SSD"); // Pas de copie ici, ssd2 est donc l'objet original
        ssd2.setPrix(87.00); // Devrait modifier l'objet ssd de la config 3 (mais pas celui dans la config 2)

        System.out.println();
        config3.afficherTout();
        afficherTotal(config3);

        commencerTest("Remplacement de composants");

        Configuration config4 = config3.copier();
        config4.setDescription("Build final");

        // Remplacements acceptés
        config4.remplacer(samsung980);

        Composant cpu3 = ryzen7800x3d.copier();
        cpu3.setRabais(0.30);
        config4.remplacer(cpu3);

        // Remplacements refusés
        config4.remplacer(msiB550); // Pas de composant de cette catégorie (Carte mère)
        config4.ajouter(gbRTX4060);
        config4.remplacer(tridentzDDR4); // Le prix maximal serait dépassé après ce remplacement

        System.out.println();
        config4.afficherTout();
        afficherTotal(config4);

        commencerTest("Validation finale");

        config1.afficherTout(); // Ne devrait pas avoir changé depuis le test #1
        afficherTotal(config1);

        config2.afficherTout(); // Ne devrait pas avoir changé depuis le test #4
        afficherTotal(config2);

        config3.afficherTout(); // Ne devrait pas avoir changé depuis le test #5
        afficherTotal(config3);

        config4.setPrixMax(0); // Définir le prix maximum comme illimité
        config4.remplacer(asusRTX4060);
        config4.afficherTout();
        afficherTotal(config4);
    }

    private static void afficherTotal(Configuration config) {
        System.out.printf("Total: %d composants pour %.2f$ (taxes incluses)\n\n", config.getNbComposants(), config.calculerTotal(0.15));
    }

    private static int noTest = 0;

    private static void commencerTest(String description) {
        System.out.printf("\n=== Test #%d : %s ===\n\n", ++noTest, description);
    }
}