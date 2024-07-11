
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author : Sylvie Huet
 * @version : juin 2005 Cette classe lance le mod�le individu centr� d'�tude de
 * la transmission d'information
 */
    class MonModel2 {

    PrintStream console = System.out;
    PrintStream ps;
    FileOutputStream fic;
    static String nomFichier;
    double[] params;
    String nameExpe;

    /**
     * population ayant � d�cider sur l'objet
     */
    public Dynamics2 myDyn;

    /*
     * public static void main(String[] args) { nomFichier = args[0]; new
     * ModeleInfoTrans(nomFichier); }
     */
    public MonModel2(String param, int ligne, int fs, boolean entete) {
        ecritureResultats("fichierResultat.txt");
        console.println("lancons la simulation avec le fichier " + param);
        lectureEntree(param, ligne, fs, entete);
        System.err.println("La simulation est finie.");
        try {
            fic.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("probl�me de fermeture du fichier");
        }
    }

    public MonModel2(String param, String nomFichR, int ligne, int fs, boolean entete) {
        ecritureResultats(nomFichR);
        console.println("lancons la simulation avec le fichier " + param + " le fichier resultat est " + nomFichR);
        lectureEntree(param, ligne, fs, entete);
        try {
            fic.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("probl�me de fermeture du fichier");
        }
    }

    /**
     * Lecture des caract�ristiques de la population
     */
    public void lectureEntree(String fileName, int ligne, int frequenceSauvegarde, boolean entete) {
        try {
            // ouverture du fichier.
            FileReader file = new FileReader(fileName);
            StreamTokenizer st = new StreamTokenizer(file);
            while (st.lineno() < ligne) {
                st.nextToken();
            }
            params = new double[17];
            Arrays.fill(params, 0.0);
            nameExpe = st.sval;
            st.nextToken();
            params[0] = (double) st.nval; //ti anti-gaspillage
            st.nextToken();
            params[1] = (double) st.nval; //tiLogistLocalCompost
            st.nextToken();
            params[2] = (double) st.nval; //tiLogisticCollect
            st.nextToken();
            params[3] = (double) st.nval; //tiPracticCompostLocal
            st.nextToken();
            params[4] = (double) st.nval; //tiPracticTriOrdure
            st.nextToken();
            params[5] = (double) st.nval; //b
            st.nextToken();
            params[6] = (double) st.nval; //qv
            st.nextToken();
            params[7] = (double) st.nval; //qa
            st.nextToken();
            params[8] = (double) st.nval; //alpha_1base
            st.nextToken();
            params[9] = (double) st.nval;//g
            st.nextToken();
            params[10] = (double) st.nval; //objGaspi
            st.nextToken();
            params[11] = (double) st.nval; //theta1
            st.nextToken();
            params[12] = (double) st.nval; //theta2
            st.nextToken();
            params[13] = (double) st.nval; //seuil
            st.nextToken();
            params[14] = (double) st.nval; //nbYears of simulation
            //System.err.println(fileName+" nbYears simulation "+params[0]+" "+params[1]+" "+params[2]+" "+params[3]+" "+params[4]+" "+params[5]+" "+params[6]+" "+params[7]+" "+params[8]+" "+params[9]+" ") ;
            st.nextToken();
            params[15] = (double) st.nval; //KA
            st.nextToken();
            params[16] = (double) st.nval; // accroissement annuel de la population
            st.nextToken();
            params[17] = (double) st.nval; // part du flux allant en déchetterie
            /* pas d'équipement de valo local pour l'instant développement à revoir
            st.nextToken();
            params[18] = (double) st.nval; // capaciteCompostLocal
            st.nextToken();
            params[19] = (double) st.nval; // capaciteMethaniseur
            st.nextToken();
            params[20] = (double) st.nval; // capaciteCompostPro 
            st.nextToken();
            params[21] = (double) st.nval; // size of the population 
            */
            // Ecriture de l'entete du fichier résultat
            if (entete) {
                System.out.print("nameExpe" + ";");
                System.out.print("tiAntiGaspillage" + ";");
                System.out.print("tiLogistLocalCompost" + ";");
                System.out.print("tiLogisticCollect" + ";");
                System.out.print("tiPracticCompostLocal" + ";");
                System.out.print("tiPracticTriOrdure" + ";");
                System.out.print("b" + ";");
                System.out.print("qv" + ";");
                System.out.print("qa" + ";");
                System.out.print("alpha1base" + ";");
                System.out.print("g" + ";");
                System.out.print("objectifAntiGaspi" + ";");
                System.out.print("Theta1" + ";");
                System.out.print("Theta2" + ";");
                System.out.print("Seuil" + ";");
                System.out.print("NbYears" + ";");
                System.out.print("KA" + ";");
                System.out.print("accroissementAnnuelPop" + ";");
                System.out.print("alpha3t0" + ";"); // 
                System.out.print("K1" + ";"); // 
                System.out.print("K2" + ";");//
                System.out.print("K3" + ";"); //    
                System.out.print("InitialSizePopulation" + ";"); // 
                System.out.print("t" + ";"); // 
                System.out.print("sizePop" + ";");
                System.out.print("DiminutionGaspillageAlimentaire" + ";");
                System.out.print("ProducedBioWaste" + ";");
                System.out.print("OMR" + ";");
                System.out.print("compostage local" + ";"); // quantité dans compostage local
                System.out.print("qteEntrantMethaniseur" + ";"); // quantité dans méthaniseur
                System.out.print("qteTraiteesParMethaniseur" + ";"); // quantité dans méthaniseur
                System.out.print("compostage professionnel" + ";"); // quantité dans compostage professionnel
                System.out.print("incinerateur" + ";"); // quantité dans l'incinérateur
                System.out.print("tauxValorisationbiowaste" + ";");
                System.out.print("nbSolutionsForFoodPerHab" + ";");
                System.out.print("nbSolutionsForGreenPerHab" + ";");
                System.out.print("valorizationRateSufficientAt12" + ";");
                System.out.print("oneSolutionAtLeastPerHabForFoodAt6" + ";");
                System.out.print("oneSolutionAtLeastPerHabForGreen" + ";");
                System.out.print("checkCoherenceFluxStage1" + ";"); //
                System.out.print("checkCoherenceFluxStage2" + ";"); //
                System.out.println();
                entete = false;
            }
            myDyn = null;
            myDyn = new Dynamics2(params);
            indicatorsObjectives((int) params[14]);
            ecritureResultatsComputed((int) params[14]);
            // faire varier b et qa et qv pour définir différents territoires 
            //for (double z = 0.0; z < 0.45; z = z + 0.1) {// variation de alpha1base, valeur de base 0.347
            //params[4] = z;
            /*
            for (int a = 10000; a < 70000; a = a + 2500) { // production biowaste totale varie de 55100 à 88000 dans le temps
                params[18] = a; //K1
                for (int b = 10000; b <= 35000; b = b + 2500) { //30000 pas 5000
                    params[19] = b; //K2
                    for (int c = 5000; c <= 35000; c = c + 2500) { //30000 pas 5000
                        params[20] = c; //K3
                        for (int d = 0; d <= 35000; d = d + 2500) { //b
                            params[15] = d; //KA POUR TOUT KA INFERIEUR OU EGAL A CAPACITE METHANISATEUR
                            myDyn = null;
                            myDyn = new Dynamics2(params);
                            indicatorsObjectives((int) params[14]);
                            ecritureResultatsComputed((int) params[14]);
                        }
                    }
                }
            }
            */
            //}
        } catch (Exception e) {
            System.err.println("erreur lecture : " + e.toString());
            e.printStackTrace();
            System.out.println("erreur lecture : " + e.toString());
        }
    }

    /**
     * m�thode d'�criture des r�sultats de la simulation, on �crit la valeur des
     * objets du modele
             */
    public void ecritureResultats(String nameResult) {
        try {
            fic = new FileOutputStream(nameResult, true);
            ps = new PrintStream(fic);
            System.setOut(ps);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println();
            System.out.println("j'ai un probl�me d'�criture des r�sultats");
        }

    }

    /**
     * Ecriture des résultats
     */
    public void ecritureResultatsComputed(int nbYears) {
        try {
            double fq = 0.0;
            int bound = nbYears + 1;
            //for (int i = 1; i < bound; i++) {
            for (int i = 1; i < bound; i++) { // annee 2030 est 13, 23 est 2040
                fq = i % 2; //
                //System.err.println(nbYears + " " + fq);
                //if (fq == 0.0 || i == 1) {
                //if (i == 13 || i == 15 || i == 17 || i == 19 || i == 21 || i == 23) {
                if (i == 3 || i == 6 || i == 9 || i == 12 || i == 15 || i == 18 || i == 21) {
                    System.out.print(nameExpe + ";");
                    for (int v = 0; v < params.length; v++) {
                        System.out.print(Double.toString(params[v]).replace(".", ",") + ";");
                    }
                    System.out.print(i + ";");
                    System.out.print(Double.toString(myDyn.P[i]).replace(".", ",") + ";");
                    System.out.print(Double.toString(myDyn.Rtot[i]).replace(".", ",") + ";");
                    System.out.print(Double.toString(myDyn.B[i]).replace(".", ",") + ";");
                    System.out.print(Double.toString(myDyn.O[i]).replace(".", ",") + ";");
                    System.out.print(Double.toString(myDyn.Lfinal[i]).replace(".", ",") + ";");
                    double qteArrivantMethaniseur = myDyn.Ma[i] + myDyn.Mv[i];
                    System.out.print(Double.toString(qteArrivantMethaniseur).replace(".", ",") + ";");
                    System.out.print(Double.toString(myDyn.Mfinal[i]).replace(".", ",") + ";");
                    System.out.print(Double.toString(myDyn.Fv[i]).replace(".", ",") + ";");
                    System.out.print(Double.toString(myDyn.I[i]).replace(".", ",") + ";");
                    System.out.print(Double.toString(valorizationRate[i]).replace(".", ",") + ";");
                    System.out.print(Double.toString(nbSolutionsForAllForFood[i]).replace(".", ",") + ";");
                    System.out.print(Double.toString(nbSolutionsForAllForGreen[i]).replace(".", ",") + ";");
                    System.out.print(goodValorRate[i] + ";");
                    System.out.print(solutionForAllForFood[i] + ";");
                    System.out.print(solutionForAllForGreen[i] + ";");
                    System.out.print(myDyn.checkFluxStage1[i] + ";");
                    System.out.print(myDyn.checkFluxStage2[i] + ";");
                    System.out.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println();
            System.out.println("j'ai un probl�me d'�criture des r�sultats");
        }

    }

    double[] valorizationRate;
    boolean[] goodValorRate;
    double[] nbSolutionsForAllForGreen;
    double[] nbSolutionsForAllForFood;
    boolean[] solutionForAllForGreen;
    boolean[] solutionForAllForFood;

    public void indicatorsObjectives(int nbYears) { // pour évaluer atteinte des objectifs données par gouvernement français
        int bound = nbYears + 1;
        // Volume of hsh waste = B - compostage local (réduction de 15 % par rapport à 2018 attendue pour 2030
        valorizationRate = new double[bound];
        Arrays.fill(valorizationRate, 0.0);
        goodValorRate = new boolean[bound];
        Arrays.fill(goodValorRate, true);
        nbSolutionsForAllForGreen = new double[bound];
        nbSolutionsForAllForFood = new double[bound];
        Arrays.fill(nbSolutionsForAllForFood, 0.0);
        Arrays.fill(nbSolutionsForAllForGreen, 0.0);
        solutionForAllForGreen = new boolean[bound];
        solutionForAllForFood = new boolean[bound];
        Arrays.fill(solutionForAllForFood, true);
        Arrays.fill(solutionForAllForGreen, true);
        for (int i = 1; i < bound; i++) {
            valorizationRate[i] = (myDyn.Mfinal[i] + myDyn.Fv[i]) / (myDyn.B[i] - myDyn.Lfinal[i]);
            //nbSolutionsForAllForFood[i] = (myDyn.La[i] + myDyn.Aa[i]) / myDyn.Ba[i];
            //nbSolutionsForAllForGreen[i] = (myDyn.Lv[i] + myDyn.Av[i] + myDyn.Dv[i]) / myDyn.Ba[i];

            // DEVRAIT ETRE SIGMOIDE LOG K1 ET SIGMOIDE LOG KA
            nbSolutionsForAllForFood[i] = (myDyn.K1 + myDyn.KA) / myDyn.Ba[i]; // pas de problème pour Green tant que Dechetterie a capacité infinie
        }
        for (int i = 1; i < bound; i++) {
            if (nbSolutionsForAllForFood[6] < 1.0) { // doit être vrai en iter 6
                solutionForAllForFood[i] = false;
            }
            if (valorizationRate[12] < 0.65) { // doit être vrai en iter 12
                goodValorRate[i] = false;
            }
        }

    }
}
