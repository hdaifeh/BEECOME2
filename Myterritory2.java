
import static java.lang.Float.max;
import static java.lang.Float.min;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author shuet
 */
public class Myterritory2 {

    boolean printTrajectory;
    boolean useSocialDynamics = true; // allows to cancel the evolution of the behavioural intention (ie the alpha) when it is false
    double einit; // part edible de la production initial de dechtes alimentaire = 0.032
    //double objGaspi ; // objectif anti-gaspillage si applicable à tout le territoire
    double theta1; // Sigmoïde Ptri[y] : Dynamique sociale modélisé par la sigmoïde qui consiste à implémenter une incitation au tri allant de alpha2baseDa = 70% jusqu’à alpha2objectifDa =95%
    double theta2; // Sigmoïde Ptri[y] : Dynamique sociale modélisé par la sigmoïde qui consiste à implémenter une incitation au tri allant de alpha2baseDa = 70% jusqu’à alpha2objectifDa =95%

    double tiAG; // point d'inflexion de la courbe de sigmoïde pratique anti-gaspillage

    double[] Ptot; // taille de la population à chaque temps t
    double[] Btot; //Quantité de biodéchets produit pour la population
    double[] Batot; //Quantité de biodéchets produit pour la population
    double[] Bvtot; //Quantité de biodéchets produit pour la population
    double[] RGlobtot; //Quantité d'anti-gaspillage
    double[] Lfinaltot; // Quantité de biodéchets dans le compostage local après gestion éventuel surplus
    double[] Afinaltot; // Quantité de biodéchets dans collecte dans gestion des éventuels surplus
    double[] Otot; //Quantité de biodéchets verts qui part vers les OMR
    double[] Dvtot; //Quantité de biodéchets verts dans les déchetteries
    
    double[] sLatot; // total compostable food surplus
    double[] sLvtot; // total compostable green surplus
    
     double[] sAvtot; // total compostable green surplus
     double[] sAatot; // total compostable green surplus

    int refYear; // année de référence entre 0 et nbYears simulation pour l'évaluation des objectifs type accroissement ou diminution
    double firstYearDechetterieDechetVertTot;
    double firstYearVolumeDechetOMRTot;

    double[] sigmoideAntiGaspi; // stocke l'évolution des pratiques en fonction de ti // ATTENTION LA VITESSE EST LA MEME POUR TOUS LES PROCESSUS CAR TI EST LE MEME POUR TOUS !!!!!!

    boolean[] checkFluxStage1;
    boolean[] checkFluxStage2;

    int territoryName;
    int nbSubterritories;
    int nbEquipments;
    Subterritory2[] myTerrit;
    EquipmentValorisation2 myCommonEquip;

    double increasedObjectiveOfMethanisedFoodWaste;

    public Myterritory2(int nbYears, int nbSubterritories, double[] paramsTerritory, double[][] paramsSubTerritories, boolean printTraj) {

        refYear = 0; //
        printTrajectory = printTraj;
        myTerrit = new Subterritory2[nbSubterritories];
        int sizeData = nbYears + 1;
        for (int i = 0; i < nbSubterritories; i++) {
            myTerrit[i] = new Subterritory2(this, i);
            myTerrit[i].init(sizeData, paramsSubTerritories[i], refYear);
        }
        // initialisation territoire (après sous-territoires car requis pour indiquer qui abondent aux equipements valo commun)
        myCommonEquip = new EquipmentValorisation2();
        init(sizeData, paramsTerritory);
        // iterate over the time
        for (int i = 1; i <= nbYears; i++) {
            computeSubterritories(i);
            // calcul des sommes des flux des territoires amendant un équipement
            computeFluxesForCommonEquipment(i);
            // calcul valorisation ou incinérateur commun
            myCommonEquip.iterate(i, totCollecteVert, totCollecteFood, totDechetterie, totOMR);
            computeTotalFluxSubTerritories(i);
            // Controle de bugs
            checkConservationFlux(i);
        }

    }

    public void computeSubterritories(int year) {
        sigmoideAntiGaspi[year] = sigmoide(year, tiAG);
        for (int i = 0; i < nbSubterritories; i++) {
            // Production de biodéchets et Répartition des biodéchets dans compost local, déchetterie et collecte
            myTerrit[i].iterate(year);
        }
    }

    public double sigmoide(double x, double ti) {
        double t = Math.pow(x, 5);
        double z = t / (t + Math.pow(ti, 5)); // ti est le point d'inflexion de la sigmoide (la valeur 0.5 est renvoyée la tième année)
        return z;
    }

    public void init(int sizeData, double[] params) { // initialisation des paramètres globaux et des équipements de valorisation communs

// TODO A ENTRER DANS LES PARAMETRES, notamment pour la CAM
        increasedObjectiveOfMethanisedFoodWaste = 5700.0; // la CAM doit récolter auprès des habitants 5700 t de biodéchets supplémentaires (on part en 0 ou quasi) déchets alimentaires méthanisés réf SGTDO sur tous les producteurs (objectifs multiplication par trois)

        territoryName = (int) params[0];
        tiAG = params[1]; // point d'inflexion de la courbe de la sigmoide 
        einit = params[2]; // 
        //objGaspi = params[3]; // objectif réduction gaspillage
        sigmoideAntiGaspi = new double[sizeData];
        Arrays.fill(sigmoideAntiGaspi, 0.0);
        int KMethaniseur = (int) params[5]; // priority 1 if surplus capacite K2
        int KIncinerator = (int) params[6]; // priority 2 if surplus 
        int KnbCompostPro = (int) params[7]; // priority 3 if surplus capacite K3
        nbSubterritories = (int) params[4];

        myCommonEquip.init(sizeData, KMethaniseur, KIncinerator, KnbCompostPro);

        Ptot = new double[sizeData]; //sizeData = nbyears of simulation + 1 (for the initial state)
        Arrays.fill(Ptot, 0.0);
        Btot = new double[sizeData];
        Arrays.fill(Btot, 0.0);
        Batot = new double[sizeData];
        Arrays.fill(Batot, 0.0);
        Bvtot = new double[sizeData];
        Arrays.fill(Bvtot, 0.0);
        RGlobtot = new double[sizeData];
        Arrays.fill(RGlobtot, 0.0);
        Lfinaltot = new double[sizeData];
        Arrays.fill(Lfinaltot, 0.0);
        Otot = new double[sizeData];
        Arrays.fill(Otot, 0.0);
        Afinaltot = new double[sizeData];
        Arrays.fill(Afinaltot, 0.0);
        Dvtot = new double[sizeData];
        Arrays.fill(Dvtot, 0.0);

        //computeTotalFluxSubTerritories(0);
        checkFluxStage1 = new boolean[sizeData];
        Arrays.fill(checkFluxStage1, true);
        checkFluxStage2 = new boolean[sizeData];
        Arrays.fill(checkFluxStage2, true);
        computeFluxesForCommonEquipment(0);
        computeTotalFluxSubTerritories(0);
        this.myCommonEquip.I[0] = this.totOMR;
        this.myCommonEquip.Fv[0] = this.totDechetterie;
        this.myCommonEquip.Ma[0] = this.totCollecteFood;
        this.myCommonEquip.Mv[0] = this.totCollecteVert;
    }

    public void printVector(double[] edit) {
        for (int i = 0; i < edit.length; i++) {
            System.err.print(edit[i] + "\t");
        }
        System.err.println();
    }

    public void computeTotalFluxSubTerritories(int y) {
        for (int i = 0; i < nbSubterritories; i++) {
            Ptot[y] = Ptot[y] + myTerrit[i].P[y];
            //System.err.println("i: "+i+" y: "+y+" pop "+Ptot[y]+" "+myTerrit[i].P[y]) ;
            Btot[y] = Btot[y] + myTerrit[i].B[y];
            Batot[y] = Batot[y] + myTerrit[i].Ba[y];
            Bvtot[y] = Bvtot[y] + myTerrit[i].Bv[y];
            RGlobtot[y] = RGlobtot[y] + myTerrit[i].Rtot[y];
            Lfinaltot[y] = Lfinaltot[y] + myTerrit[i].Lfinal[y]; // compost perso
            Otot[y] = Otot[y] + myTerrit[i].O[y]; // biodéchets dans omr
            Afinaltot[y] = Afinaltot[y] + myTerrit[i].Afinal[y]; // collecte de biodéchets
            Dvtot[y] = Dvtot[y] + myTerrit[i].Dv[y]; // déchets verts dans déchetterie
            if (y == refYear) {
                firstYearDechetterieDechetVertTot = firstYearDechetterieDechetVertTot + myTerrit[i].Dv[0];
                firstYearVolumeDechetOMRTot = firstYearVolumeDechetOMRTot + myTerrit[i].O[0];
            }
        }
    }

    double totDechetterie;
    double totCollecteVert;
    double totCollecteFood;
    double totOMR;

    public void computeFluxesForCommonEquipment(int y) {
        totDechetterie = 0.0;
        totCollecteVert = 0.0;
        totCollecteFood = 0.0;
        totOMR = 0.0;
        for (int i = 0; i < nbSubterritories; i++) {
            totDechetterie = totDechetterie + myTerrit[i].Dv[y];
            totCollecteVert = totCollecteVert + myTerrit[i].Av[y];
            totCollecteFood = totCollecteFood + myTerrit[i].Aa[y];
            totOMR = totOMR + myTerrit[i].O[y];
        }
    }

    public void checkConservationFlux(int y) {
        int i = 0;
        double time1 = 0.0;
        double time2 = 0.0;
        time1 = time1 + Lfinaltot[y] + Otot[y] + Afinaltot[y] + Dvtot[y]; // sous-territoire
        time2 = time2 + myCommonEquip.Mfinal[y] + myCommonEquip.I[y] + Lfinaltot[y] + myCommonEquip.Fv[y]; // Equipt valorisation
        double t1 = time1 - Btot[y];
        double t2 = time2 - Btot[y];
        if (Math.abs(t1) > 0.00000001) {
            System.err.println("there is a bug in the computation of flux in stage 1, the error is " + t1 + " at time " + y);
            checkFluxStage1[y] = false;
        }
        if (Math.abs(t2) > 0.00000001) {
            System.err.println("there is a bug in the computation of flux in stage 2, the error is " + t2 + " at time " + y);
            checkFluxStage2[y] = false;
        }
    }

}
