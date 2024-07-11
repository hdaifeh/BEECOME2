
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
class MyModel2 {

    boolean printTrajectory = true; // pour écrire le détail d'une trajectoire détaillée
    PrintStream console = System.out;
    PrintStream ps;
    FileOutputStream fic;
    static String nomFichier;
    double[] paramsTerritory;
    int nameExpe;

    int nbYearsSimu;
    int nbSubterritories;
    double[][] paramsSubTerritories;

    /**
     * population ayant � d�cider sur l'objet
     */
    public Myterritory2 myDyn;

    /*
     * public static void main(String[] args) { nomFichier = args[0]; new
     * ModeleInfoTrans(nomFichier); }
     */
    public MyModel2(String param, int ligne, int fs, boolean entete) {
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

    public MyModel2(String param, String nomFichR, int ligne, int fs, boolean entete) {
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

    String paramFileName;

    /**
     * Lecture des caract�ristiques de la population
     */
    public void lectureEntree(String fileName, int ligne, int frequenceSauvegarde, boolean entete) {
        try {
            paramFileName = fileName;
            // ouverture du fichier.
            FileReader file = new FileReader(fileName);
            StreamTokenizer st = new StreamTokenizer(file);
            while (st.lineno() < ligne) {
                st.nextToken();
            }
            int nbParamTerritory = 8;
            paramsTerritory = new double[nbParamTerritory];
            Arrays.fill(paramsTerritory, 0.0);
            nameExpe = (int) st.nval;
            paramsTerritory[0] = (double) nameExpe;//
            st.nextToken();
            paramsTerritory[1] = (double) st.nval;//g ti anti-gaspillage
            st.nextToken();
            paramsTerritory[2] = (double) st.nval; //g
            st.nextToken();
            //paramsTerritory[3] = (double) st.nval; //objGaspi
            //st.nextToken();
            nbYearsSimu = (int) st.nval; //nbYears of simulation
            paramsTerritory[3] = (double) nbYearsSimu;
            st.nextToken();
            nbSubterritories = (int) st.nval; //nbYears of simulation
            paramsTerritory[4] = (double) nbSubterritories;
            st.nextToken();
            paramsTerritory[5] = (double) st.nval; //capacite methaniseur commun pour tout le territoire
            st.nextToken();
            paramsTerritory[6] = (double) st.nval; //capacite incinerateur commun pour tout le territoire
            st.nextToken();
            paramsTerritory[7] = (double) st.nval; //capacité platform compost professionnel commune pour tout le territoire
            st.nextToken();
            int nbParamToDescribeSubTerritory = 24;
            paramsSubTerritories = new double[nbSubterritories][nbParamToDescribeSubTerritory];
            double[] paramSubT;
            for (int a = 0; a < nbSubterritories; a++) {
                paramSubT = new double[nbParamToDescribeSubTerritory];
                Arrays.fill(paramSubT, 0.0);
                paramSubT[0] = (double) st.nval; //nom numérique du sous-territoire
                st.nextToken();
                paramSubT[1] = (double) st.nval; //tiLogistLocalCompost
                st.nextToken();
                paramSubT[2] = (double) st.nval; //tiLogisticCollect
                st.nextToken();
                paramSubT[3] = (double) st.nval; //tiPracticCompostLocal
                st.nextToken();
                paramSubT[4] = (double) st.nval; //tiPracticTriOrdure
                st.nextToken();
                paramSubT[5] = (double) st.nval; //Ba
                st.nextToken();
                paramSubT[6] = (double) st.nval; //Bv
                st.nextToken();
                paramSubT[7] = (double) st.nval; //alpha_1base déchets alimentaires
                st.nextToken();
                paramSubT[8] = (double) st.nval; //alpha_1base déchets verts
                st.nextToken();
                paramSubT[9] = (double) st.nval; //alpha2base déchets alimentaires // taux de pratique du tri pour la collecte porte à porte des déchets alimentaires
                st.nextToken();
                paramSubT[10] = (double) st.nval; //alpha2objectifs déchets alimentaires
                //System.err.println(paramSubT[10]);
                st.nextToken();
                paramSubT[11] = (double) st.nval; //accroissement alpha1 déchets alimentaires
                st.nextToken();
                paramSubT[12] = (double) st.nval; //accroissement alpha1 déchets verts
                st.nextToken();
                paramSubT[13] = (double) st.nval; //alpha2base déchets verts // taux de pratique du tri pour la collecte porte à porte des déchets verts
                st.nextToken();
                paramSubT[14] = (double) st.nval; //alpha2base déchets verts // taux de pratique du tri pour la collecte porte à porte des déchets verts
                st.nextToken();
                paramSubT[15] = (double) st.nval; // K1 capacité compostage local init
                st.nextToken();
                paramSubT[16] = (double) st.nval; // K1 capacité compostage local cible 2025
                st.nextToken();
                paramSubT[17] = (double) st.nval; //KA capacité collecte porte à porte init
                st.nextToken();
                paramSubT[18] = (double) st.nval; //KA capacité collecte porte à porte 2025
                st.nextToken();
                paramSubT[19] = (double) st.nval; //taille de la sous-population
                st.nextToken();
                paramSubT[20] = (double) st.nval; // accroissement annuel de la population
                st.nextToken();
                paramSubT[21] = (double) st.nval; //tiActionsEvitementDechetsVerts
                st.nextToken();
                paramSubT[22] = (double) st.nval; //tauxEvitementDechetsVertsHorizon2024
                st.nextToken();
                paramSubT[23] = (double) st.nval; //anti-gaspillage du sous-territoire
                st.nextToken();
                // 23
                
                //System.err.println(paramSubT[0] +" "+paramSubT[1]+" "+paramSubT[9] +" "+paramSubT[10]+" "+paramSubT[11] +" "+paramSubT[12]+" "+paramSubT[13] +" "+paramSubT[14]+" "+paramSubT[15] +" "+paramSubT[16]) ;
                for (int b = 0; b < nbParamToDescribeSubTerritory; b++) {
                    paramsSubTerritories[a][b] = paramSubT[b];
                }
                paramSubT = null;
            }
            // Ecriture de l'entete du fichier résultat
            if (entete & !printTrajectory) {

                System.out.print("nameOftheParametersFile" + ";");
                for (int i = 0; i < nbSubterritories; i++) {
                    System.out.print("NameSubTerritory" + ";");
                    System.out.print("alpha1ObjDA" + i + ";");
                    System.out.print("alpha1ObjDV" + i + ";");
                    System.out.print("tiPracticCompostLocal" + i + ";");
                    System.out.print("alpha2ObjDA" + i + ";");
                    System.out.print("alpha2ObjDV" + i + ";"); //
                    System.out.print("tiPracticTri" + i + ";");
                }
                System.out.print("t" + ";"); //
                for (int i = 0; i < nbSubterritories; i++) {
                    System.out.print("nbKgCollectHabDesserviSubTerrit" + i + ";"); // print nbKgCollectHabDesserviSubTerrit
                }
                for (int i = 0; i < nbSubterritories; i++) {
                    System.out.print("partPopDesserviCollDAlim" + i + ";");
                }
                for (int i = 0; i < nbSubterritories; i++) {
                    System.out.print("nbKgDechetAlimOMRByHabitant" + i + ";");
                }
                for (int i = 0; i < nbSubterritories; i++) {
                    System.out.print("alpha1Da" + i + ";");
                }
                for (int i = 0; i < nbSubterritories; i++) {
                    System.out.print("alpha1Dv" + i + ";");
                }
                for (int i = 0; i < nbSubterritories; i++) {
                    System.out.print("alpha2Da" + i + ";");
                }
                for (int i = 0; i < nbSubterritories; i++) {
                    System.out.print("alpha2Dv" + i + ";");
                }
                for (int i = 0; i < nbSubterritories; i++) {
                    System.out.print("K1courant" + i + ";");
                }
                for (int i = 0; i < nbSubterritories; i++) {
                    System.out.print("Kacourant" + i + ";");
                }
                for (int i = 0; i < nbSubterritories; i++) {
                    System.out.print("TauxEvolDechetsVertsDansDechetterie" + i + ";");
                }
                for (int i = 0; i < nbSubterritories; i++) {
                    System.out.print("Bv" + i + ";");
                }
                for (int i = 0; i < nbSubterritories; i++) {
                    System.out.print("Av" + i + ";");
                }
                for (int i = 0; i < nbSubterritories; i++) {
                    System.out.print("sAv" + i + ";");
                }
                for (int i = 0; i < nbSubterritories; i++) {
                    System.out.print("Dv" + i + ";");
                }
                for (int i = 0; i < nbSubterritories; i++) {
                    System.out.print("Lv" + i + ";");
                }
                for (int i = 0; i < nbSubterritories; i++) {
                    System.out.print("sLv" + i + ";");
                }
                for (int i = 0; i < nbSubterritories; i++) {
                    System.out.print("Ba" + i + ";");
                }
                for (int i = 0; i < nbSubterritories; i++) {
                    System.out.print("Aa" + i + ";");
                }
                for (int i = 0; i < nbSubterritories; i++) {
                    System.out.print("sAa" + i + ";");
                }
                for (int i = 0; i < nbSubterritories; i++) {
                    System.out.print("La" + i + ";");
                }
                for (int i = 0; i < nbSubterritories; i++) {
                    System.out.print("sLa" + i + ";");
                }

                System.out.print("nbKgDechetAlimOMRByHabitantGlobal" + "" + ";");
                System.out.print("sizePop" + ";");
                System.out.print("DiminutionGaspillageAlimentaire" + ";"); // the reduction of food waste 
                System.out.print("ProducedBioWasteTotale" + ";");
                System.out.print("ProducedBioWasteAlimentaire" + ";");
                System.out.print("ProducedBioWasteVerts" + ";");
                System.out.print("OMR" + ";");
                System.out.print("compostage local" + ";"); // quantité dans compostage local
                System.out.print("déchetterie" + ";"); // quantité dans compostage local ??? no dans dechetterie
                System.out.print("qteEntrantMethaniseur" + ";"); // quantité dans méthaniseur
                System.out.print("qteTraiteesParMethaniseur" + ";"); // quantité dans méthaniseur
                System.out.print("compostage professionnel" + ";"); // quantité dans compostage professionnel
                System.out.print("incinerateur" + ";"); // quantité dans l'incinérateur
                System.out.print("tauxValorisationbiowaste" + ";");
                System.out.print("nbSolutionsForFoodPerHab" + ";");
                System.out.print("nbSolutionsForGreenPerHab" + ";");
                System.out.print("tauxReductionDechetsVerts" + ";");
                System.out.print("increaseOfMethaniseFoodwaste" + ";");// "objective" increasing food waste in methanisation unit
                System.out.print("multiplicateurVolumeBiodechetOMR" + ";");
                System.out.print("totalIntentionForFoodWaste" + ";");
                System.out.print("totalIntentionForGreenWasteWithoutDechetterie" + ";");
                System.out.print("valorizationRateSufficientAt12" + ";");
                System.out.print("oneSolutionAtLeastPerHabForFoodAt6" + ";");
                System.out.print("oneSolutionAtLeastPerHabForGreen" + ";");
                System.out.print("tauxDiminutionDechetsVertsCorrect" + ";");
                System.out.print("correctIncreaseMethaniseFoodwasteCorrect" + ";");
                System.out.print("diminutionVolumeBiodechetOMR" + ";");
                System.out.print("checkCoherenceFluxStage1" + ";"); //
                System.out.print("checkCoherenceFluxStage2" + ";"); //
                System.out.print("year"+ ";");
                System.out.print("EvolVolumeDADansOMR" + ";");
                System.out.print("NombreObjectifsRespectes" + ";");
                System.out.print("4ObjectifsAtteints" + ";");
                System.out.println();
                entete = false;
            }
            if (entete & printTrajectory ) {
                
                for (int i = 1; i <= nbSubterritories; i++) { // the title of the results output file when printtrajectory is true
                    System.out.print("ident" + i + ";"); // it gave ident 0 , ident 1, ident 2,....ident 8 ( the subterritories number)
                    System.out.print("population" + i + ";");
                    System.out.print("Ba" + i + ";");
                    System.out.print("Bv" + i + ";");
                    System.out.print("alpha1Da" + i + ";");
                    System.out.print("alpha1Dv" + i + ";");
                    System.out.print("La" + i + ";");
                    System.out.print("sLa" + i + ";");
                    System.out.print("Lv" + i + ";");
                    System.out.print("sLv" + i + ";");
                    System.out.print("K1courant" + i + ";");
                    System.out.print("alpha2Da" + i + ";");
                    System.out.print("alpha2Dv" + i + ";");
                    System.out.print("Aa" + i + ";");
                    System.out.print("sAa" + i + ";");
                    System.out.print("Av" + i + ";");
                    System.out.print("sAv" + i + ";");
                    System.out.print("Kacourant" + i + ";");
                    System.out.print("O" + i + ";");
                    System.out.print("alpha3" + i + ";");
                    System.out.print("Dv" + i + ";");
                }
                
                System.out.print("year" + ";");
                System.out.print("Fv" + ";");
                System.out.print("Ma" + ";");
                System.out.print("Ma_bis" + ";");
                System.out.print("Mv" + ";");
                System.out.print("Mv_bis" + ";");
                System.out.print("I" + ";");
                System.out.println();
                entete = false;
            }
            // faire varier b et qa et qv pour définir différents territoires
            //int posAlpha3 = 0 ;
            //for (double z = 0.0; z < 0.45; z = z + 0.05) {// variation de alpha1base, valeur de base 0.347
            //params[4] = z;

            ///* Calibration Ba (et implicitement alpha1Base, en faisant hypothèse anti gaspillage alimentaire = 0 [au moins au départ en 2018])
            ///*
            if (!printTrajectory) { // that is to make a large exploration:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                double stepToExplore = 0.002;
                
                
                double[] testObjectifCompostLocalDa = {1.0};// O ... ofc , O einit c 
                
                
                double[] testObjectifCompostLocalDv = { 1.0};
                
                //double[] testObjectifTriCollecteDa = {0, 0.05, 0.1, 0.15, 0.2, 0.3, 0.5, 0.7, 0.9, 1.0};
                
                double[] testObjectifTriCollecteDa = { 1.0};
                
                //double[] testObjectifTriCollecteDv = {0, 0.05, 0.1, 0.15, 0.2, 0.3, 0.5, 0.7, 0.9, 1.0}; // valeur initiale 0.3600 pour la CAM uniquement
                
                double[] testObjectifTriCollecteDv = { 1.0};; // valeur initiale 0.3600 pour la CAM uniquement
                
               
                //double[] vitesseAdoptPraticCompost = {2, 4, 6, 8}; //reference ti évolution pratique 5, , 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14
                double[] vitesseAdoptPraticCompost = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14}; //reference ti évolution pratique 5  m^c
                
                //double[] vitesseAdoptPracticCollecte = {2, 4, 6, 8}; //reference ti évolution pratique 5
                double[] vitesseAdoptPracticCollecte = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14}; //reference ti évolution pratique 5  m^s
                
                 

// paramètres objectifs comportement compostage local 11 et 12, tri pour collecte porte à porte 10 et 14
                ///*
                for (int i = 0; i < testObjectifCompostLocalDa.length; i = i + 1) { // territoire 0 SBA
                    for (int z = 0; z < nbSubterritories; z++) {
                        paramsSubTerritories[z][11] = testObjectifCompostLocalDa[i]; // déchet alimentaire compostage local base accroissement de 0.3
                    }
                    for (int x = 0; x < testObjectifCompostLocalDv.length; x = x + 1) {
                        for (int z = 0; z < nbSubterritories; z++) {
                            paramsSubTerritories[z][12] = testObjectifCompostLocalDv[x];
                        }
                        for (int k = 0; k < vitesseAdoptPraticCompost.length; k = k + 1) { // variations vitesse mise en place logistique déchets alimentaires du SBA
                            for (int z = 0; z < nbSubterritories; z++) {
                                paramsSubTerritories[z][3] = vitesseAdoptPraticCompost[k]; // 
                            }
                            for (int y = 0; y < testObjectifTriCollecteDa.length; y = y + 1) {
                                // pour seules regions avec collecte porte a porte (SBA, CAM) 
                                paramsSubTerritories[0][10] = testObjectifTriCollecteDa[y];
                                paramsSubTerritories[1][10] = testObjectifTriCollecteDa[y];
                                for (int j = 0; j < testObjectifTriCollecteDv.length; j = j + 1) {
                                    // Seule la CAM, territoire 1 pratique la collecte porte a porte des dechets verts
                                    paramsSubTerritories[1][14] = testObjectifTriCollecteDv[j]; // déchet verts valeur à temps 0 0.36
                                    for (int l = 0; l < vitesseAdoptPracticCollecte.length; l++) {
                                        // pour seules regions avec collecte porte a porte (SBA, CAM) 
                                        paramsSubTerritories[0][4] = vitesseAdoptPracticCollecte[l]; //
                                        paramsSubTerritories[1][4] = vitesseAdoptPracticCollecte[l];
                                        //}
                                        myDyn = null;
                                        myDyn = new Myterritory2(nbYearsSimu, nbSubterritories, paramsTerritory, paramsSubTerritories, printTrajectory);
                                        indicatorsObjectives(nbYearsSimu);
                                        ecritureResultatsComputed(nbYearsSimu);
                                    }
                                    

                                }
                            }
                        }
                    }
                }
            } else { // this is just to print a trajectory (ie printTrajectory = true ;
                myDyn = null;
                myDyn = new Myterritory2(nbYearsSimu, nbSubterritories, paramsTerritory, paramsSubTerritories, printTrajectory);
                indicatorsObjectives(nbYearsSimu);
                ecritureTrajectoire(nbYearsSimu);
            }
        } catch (Exception e) {
            System.err.println("erreur lecture : " + e.toString());
            e.printStackTrace();
            System.out.println("erreur lecture : " + e.toString());
        }
    }

   
    
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
            int a ;
            int b ;
            if (this.printTrajectory) { a=1; b=bound ; }
            else { a=7 ; b =8 ; } // année 2024:::{ a=7 ; b =8 ; }:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::2024!!!!!!!
            for (int i = a; i < b; i++) { // annee 2030 est 13, 23 est 2040
                
                System.out.print(paramFileName + ";");
                for (int v = 0; v < nbSubterritories; v++) {
                    System.out.print(v + ";");
                    System.out.print(Double.toString(paramsSubTerritories[v][11]).replace(".", ",") + ";");
                    System.out.print(Double.toString(paramsSubTerritories[v][12]).replace(".", ",") + ";");
                    System.out.print(Double.toString(paramsSubTerritories[v][3]).replace(".", ",") + ";");
                    System.out.print(Double.toString(paramsSubTerritories[v][10]).replace(".", ",") + ";");
                    System.out.print(Double.toString(paramsSubTerritories[v][14]).replace(".", ",") + ";");
                    System.out.print(Double.toString(paramsSubTerritories[v][4]).replace(".", ",") + ";");
                }
                System.out.print(i + ";");
                int v = 0;
                for (v = 0; v < nbSubterritories; v++) {
                    System.out.print(Double.toString(myDyn.myTerrit[v].nbKgCollectHabDesservi[i]).replace(".", ",") + ";");
                }
                for (v = 0; v < nbSubterritories; v++) {
                    System.out.print(Double.toString(myDyn.myTerrit[v].propPopDesserviCollDA[i]).replace(".", ",") + ";");
                }
                for (v = 0; v < nbSubterritories; v++) {
                    System.out.print(Double.toString(myDyn.myTerrit[v].nbKgOMRHab[i]).replace(".", ",") + ";");
                }
                for (v = 0; v < nbSubterritories; v++) {
                    System.out.print(Double.toString(myDyn.myTerrit[v].alpha1Da[i]).replace(".", ",") + ";");
                }
                for (v = 0; v < nbSubterritories; v++) {
                    System.out.print(Double.toString(myDyn.myTerrit[v].alpha1Dv[i]).replace(".", ",") + ";");
                }
                for (v = 0; v < nbSubterritories; v++) {
                    System.out.print(Double.toString(myDyn.myTerrit[v].alpha2Da[i]).replace(".", ",") + ";");
                }
                for (v = 0; v < nbSubterritories; v++) {
                    System.out.print(Double.toString(myDyn.myTerrit[v].alpha2Dv[i]).replace(".", ",") + ";");
                }
                for (v = 0; v < nbSubterritories; v++) {
                    System.out.print(Double.toString(myDyn.myTerrit[v].K1courant[i]).replace(".", ",") + ";");
                }
                for (v = 0; v < nbSubterritories; v++) {
                    System.out.print(Double.toString(myDyn.myTerrit[v].KAcourant[i]).replace(".", ",") + ";");
                }
                for (v = 0; v < nbSubterritories; v++) {
                    System.out.print(Double.toString(myDyn.myTerrit[v].tauxReductionDechetVert[i]).replace(".", ",") + ";");
                }
                ///*
                for (v = 0; v < nbSubterritories; v++) {
                    System.out.print(Double.toString(myDyn.myTerrit[v].Bv[i]).replace(".", ",") + ";");
                }
                for (v = 0; v < nbSubterritories; v++) {
                    System.out.print(Double.toString(myDyn.myTerrit[v].Av[i]).replace(".", ",") + ";");
                }
                for (v = 0; v < nbSubterritories; v++) {
                    System.out.print(Double.toString(myDyn.myTerrit[v].sAv[i]).replace(".", ",") + ";");
                }
                for (v = 0; v < nbSubterritories; v++) {
                    System.out.print(Double.toString(myDyn.myTerrit[v].Dv[i]).replace(".", ",") + ";");
                }
                for (v = 0; v < nbSubterritories; v++) {
                    System.out.print(Double.toString(myDyn.myTerrit[v].Lv[i]).replace(".", ",") + ";");
                }
                for (v = 0; v < nbSubterritories; v++) {
                    System.out.print(Double.toString(myDyn.myTerrit[v].sLv[i]).replace(".", ",") + ";");
                }
                for (v = 0; v < nbSubterritories; v++) {
                    System.out.print(Double.toString(myDyn.myTerrit[v].Ba[i]).replace(".", ",") + ";");
                }
                for (v = 0; v < nbSubterritories; v++) {
                    System.out.print(Double.toString(myDyn.myTerrit[v].Aa[i]).replace(".", ",") + ";");
                }
                for (v = 0; v < nbSubterritories; v++) {
                    System.out.print(Double.toString(myDyn.myTerrit[v].sAa[i]).replace(".", ",") + ";");
                }
                for (v = 0; v < nbSubterritories; v++) {
                    System.out.print(Double.toString(myDyn.myTerrit[v].La[i]).replace(".", ",") + ";");
                }
                for (v = 0; v < nbSubterritories; v++) {
                    System.out.print(Double.toString(myDyn.myTerrit[v].sLa[i]).replace(".", ",") + ";");
                }
                //*/
                System.out.print(Double.toString(kgDechetAlimDansOMRByHab[i]).replace(".", ",") + ";");
                System.out.print(Double.toString(myDyn.Ptot[i]).replace(".", ",") + ";");
                System.out.print(Double.toString(myDyn.RGlobtot[i]).replace(".", ",") + ";");
                System.out.print(Double.toString(myDyn.Btot[i]).replace(".", ",") + ";");
                System.out.print(Double.toString(myDyn.Batot[i]).replace(".", ",") + ";");
                System.out.print(Double.toString(myDyn.Bvtot[i]).replace(".", ",") + ";");
                System.out.print(Double.toString(myDyn.Otot[i]).replace(".", ",") + ";");
                System.out.print(Double.toString(myDyn.Lfinaltot[i]).replace(".", ",") + ";");
                System.out.print(Double.toString(myDyn.Dvtot[i]).replace(".", ",") + ";");
                double qteArrivantMethaniseur = myDyn.myCommonEquip.Ma[i] + myDyn.myCommonEquip.Mv[i]; ////////////////////Coooooooooool
                System.out.print(Double.toString(qteArrivantMethaniseur).replace(".", ",") + ";");
                System.out.print(Double.toString(myDyn.myCommonEquip.Mfinal[i]).replace(".", ",") + ";");
                System.out.print(Double.toString(myDyn.myCommonEquip.Fv[i]).replace(".", ",") + ";");
                System.out.print(Double.toString(myDyn.myCommonEquip.I[i]).replace(".", ",") + ";");
                System.out.print(Double.toString(valorizationRate[i]).replace(".", ",") + ";");
                System.out.print(Double.toString(nbSolutionsForAllForFood[i]).replace(".", ",") + ";");
                System.out.print(Double.toString(nbSolutionsForAllForGreen[i]).replace(".", ",") + ";");
                System.out.print(Double.toString(tauxEvolutionGreenWaste[i]).replace(".", ",") + ";");
                System.out.print(Double.toString(increaseOfMethanisedFoodWaste[i]).replace(".", ",") + ";");
                System.out.print(Double.toString(multiplicateurVolumeBiodechetOMR[i]).replace(".", ",") + ";");//////////////////////////////////////////////////////////////////////////////
                System.out.print(Double.toString(totalIntentionForFoodWaste[i]).replace(".", ",") + ";");
                System.out.print(Double.toString(totalIntentionForGreenWasteWithoutDechetterie[i]).replace(".", ",") + ";");
                System.out.print(goodValorRate[i] + ";");
                System.out.print(solutionForAllForFood[i] + ";");
                System.out.print(solutionForAllForGreen[i] + ";");
                System.out.print(diminutionGreenWaste[i] + ";");
                System.out.print(correctIncreaseOfMethanisedFoodWaste[i] + ";");
                System.out.print(diminutionVolBiodechetOMR[i] + ";");
                System.out.print(myDyn.checkFluxStage1[i] + ";");
                System.out.print(myDyn.checkFluxStage2[i] + ";");
                System.out.print((2017 + i) + ";");
                System.out.print(volMethanised[i] + ";");
                System.out.print(sommeRespectedObjectifs[i] + ";");
                System.out.print(correct[i] + ";");
                System.out.println();
                //}
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println();
            System.out.println("j'ai un probl�me d'�criture des r�sultats");
        }

    }

    public void ecritureTrajectoire(int nbYears) {
        for (int i = 0 ; i < nbYears; i++) {
        //for (int i = 1; i < nbYears; i++) {
            for (int j = 0; j < nbSubterritories; j++) {
                myDyn.myTerrit[j].printTrajectory(i);
            }
            myDyn.myCommonEquip.printTrajectory(i);
        }
    }

    double[] valorizationRate;
    int[] goodValorRate;
    double[] nbSolutionsForAllForGreen;
    double[] nbSolutionsForAllForFood;
    int[] solutionForAllForGreen;
    int[] solutionForAllForFood; // obligations réglementaires
    double[] tauxEvolutionGreenWaste; // objectifs environnementaux
    double[] increaseOfMethanisedFoodWaste; // objectifs environnementaux
    int[] diminutionGreenWaste;
    int[] correctIncreaseOfMethanisedFoodWaste;
    int[] volMethanised;
    double[] multiplicateurVolumeBiodechetOMR; // objectifs environnementaux
    int[] diminutionVolBiodechetOMR;
    double[] kgDechetAlimDansOMRByHab;
    double[] totalIntentionForFoodWaste;
    double[] totalIntentionForGreenWasteWithoutDechetterie;
    int[] correct;
    int[] sommeRespectedObjectifs ;

    public void indicatorsObjectives(int nbYears) { // pour évaluer atteinte des objectifs données par gouvernement français
        int bound = nbYears + 1;
        // Volume of hsh waste = Btot - compostage local (réduction de 15 % par rapport à 2018 attendue pour 2030
        totalIntentionForFoodWaste = new double[bound];
        Arrays.fill(totalIntentionForFoodWaste, 0.0);
        totalIntentionForGreenWasteWithoutDechetterie = new double[bound];
        Arrays.fill(totalIntentionForGreenWasteWithoutDechetterie, 0.0);
        valorizationRate = new double[bound];
        Arrays.fill(valorizationRate, 0.0);
        goodValorRate = new int[bound];
        Arrays.fill(goodValorRate, 1);
        nbSolutionsForAllForGreen = new double[bound];
        nbSolutionsForAllForFood = new double[bound];
        Arrays.fill(nbSolutionsForAllForFood, 0.0);
        Arrays.fill(nbSolutionsForAllForGreen, 0.0);
        solutionForAllForGreen = new int[bound];
        solutionForAllForFood = new int[bound];
        Arrays.fill(solutionForAllForFood, 1);
        Arrays.fill(solutionForAllForGreen, 1);
        tauxEvolutionGreenWaste = new double[bound];
        increaseOfMethanisedFoodWaste = new double[bound];
        Arrays.fill(tauxEvolutionGreenWaste, 0.0);
        Arrays.fill(increaseOfMethanisedFoodWaste, 0.0);
        diminutionGreenWaste = new int[bound];
        correctIncreaseOfMethanisedFoodWaste = new int[bound];
        Arrays.fill(diminutionGreenWaste, 1);
        Arrays.fill(correctIncreaseOfMethanisedFoodWaste, 1);
        multiplicateurVolumeBiodechetOMR = new double[bound]; // objectifs environnementaux
        Arrays.fill(multiplicateurVolumeBiodechetOMR, 0.0);
        diminutionVolBiodechetOMR = new int[bound];;
        Arrays.fill(diminutionVolBiodechetOMR, 1);
        volMethanised = new int[bound];;
        Arrays.fill(volMethanised, 1);
        kgDechetAlimDansOMRByHab = new double[bound];;
        Arrays.fill(kgDechetAlimDansOMRByHab, 0.0);
        correct = new int[bound];
        Arrays.fill(correct, 0);
        sommeRespectedObjectifs = new int[bound];
        Arrays.fill(sommeRespectedObjectifs, 0);
        for (int i = 1; i < bound; i++) {
            valorizationRate[i] = (myDyn.myCommonEquip.Mfinal[i] + myDyn.myCommonEquip.Fv[i]) / (myDyn.Btot[i] - myDyn.Lfinaltot[i]);
            double totalCapacityForFoodWaste = 0.0;
            double totalProducedFoodWaste = 0.0;
            for (int j = 0; j < nbSubterritories; j++) {
                totalCapacityForFoodWaste = totalCapacityForFoodWaste + myDyn.myTerrit[j].K1courant[i] + myDyn.myTerrit[j].KAcourant[i];
                totalProducedFoodWaste = totalProducedFoodWaste + myDyn.myTerrit[j].Ba[i]; // Ba est pour une année donnée !!!!!!!!T
                myDyn.myTerrit[j].indicSubTerritories(i); // calcul des indicateurs de l'état du sous-territoire j pour l'année i
                // Calcul de intention de solution de tri > 0.95
                totalIntentionForFoodWaste[i] = totalIntentionForFoodWaste[i] + myDyn.myTerrit[j].alpha1Da[i] + myDyn.myTerrit[j].alpha2Da[i];
                totalIntentionForGreenWasteWithoutDechetterie[i] = totalIntentionForGreenWasteWithoutDechetterie[i] + myDyn.myTerrit[j].alpha1Dv[i] + myDyn.myTerrit[j].alpha2Dv[i];
            }
            nbSolutionsForAllForFood[i] = totalCapacityForFoodWaste / totalProducedFoodWaste; // pas de problème pour Green tant que Dechetterie a capacité infinie
            // A CALCULER POUR CHAQUE SOUS-TERRITOIRE

            tauxEvolutionGreenWaste[i] = (myDyn.Dvtot[i] - myDyn.firstYearDechetterieDechetVertTot) / myDyn.firstYearDechetterieDechetVertTot;
            increaseOfMethanisedFoodWaste[i] = (myDyn.myCommonEquip.Ma[i] - myDyn.myCommonEquip.sMa[i]) - myDyn.increasedObjectiveOfMethanisedFoodWaste; // traite des déchets alimentaires méthanisés, doivent augmenter de 5700
            multiplicateurVolumeBiodechetOMR[i] = myDyn.Otot[i] / myDyn.firstYearVolumeDechetOMRTot;
            kgDechetAlimDansOMRByHab[i] = (myDyn.Otot[i] * 1000.0) / myDyn.Ptot[i];
            
        }
        if (nbYears > 5) {
            for (int i = 1; i < bound; i++) {
                if (nbSolutionsForAllForFood[6] < 1.0) { // doit être vrai en iter 6, fin 2023
                    solutionForAllForFood[i] = 0;
                }
                
                if (tauxEvolutionGreenWaste[7] > -0.12) { // 8 = 2025
                    diminutionGreenWaste[i] = 0;
                }
                if (increaseOfMethanisedFoodWaste[7] < 0) { // 8 = 2025 (la CAM doit récolter 5700 tonnes de plus de déchets alimentaires pour méthanisation)
                    correctIncreaseOfMethanisedFoodWaste[i] = 0;
                }
                if (multiplicateurVolumeBiodechetOMR[7] > 0.5) { // 8 = 2025 (objectifs du SBA)
                    diminutionVolBiodechetOMR[i] = 0;
                }
                if (myDyn.myCommonEquip.Mfinal[7] < 12000) {
                    volMethanised[i] = 0;
                }
                sommeRespectedObjectifs[i] = diminutionGreenWaste[i] + correctIncreaseOfMethanisedFoodWaste[i] + diminutionVolBiodechetOMR[i] + volMethanised[i];
                if (sommeRespectedObjectifs[i] == 4) {
                    correct[i] = 1;
                }
            }
        }
    }
}
