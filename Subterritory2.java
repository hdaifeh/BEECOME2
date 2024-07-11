
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
public class Subterritory2 {

    Myterritory2 myTerre;
    // Define the starting point of the simulation
    int timeBeforeInitAlpha1BaseDa;
    int timeBeforeInitAlpha1BaseDv;
    int timeBeforeInitAlpha2BaseDa;// its 2 for CAM
    int timeBeforeInitAlpha2BaseDv;

    double K1init; // (parameter) initial capacity of local composting 
    double KAinit; //(parameter) initial collection capacity 
    double[] K1courant; // (variable) maximum local composting capacity over the course of its implementation (changing value due to sigmoidal effect of personal compost implementation) 
    double[] KAcourant; // (variable) maximum capacity on collection during set-up (value changing due to sigmoidal effect on collection set-up)
    double K1cible; // (parameter) maximum capacity local composting target 
    double KAcible; // (parameter) maximum capacity target on collection to represent the maximum capacity of green garbage cans 
    int yearRef; // year of departure for individual composing capacity non-limiting to departure

    double[] sigmoideLogCompostLocal;

    double[] sigmoidePraticCompostLocalDa; // stocke l'évolution des pratiques en fonction de ti (ti*2 donne le nombre d'année adoption totale prévue de la pratique (ie alpha1_base+alpha1ObjectifDa)
    double[] sigmoidePraticCompostLocalDv; // 2024

    double[] sigmoideLogCollecte; // stocke l'évolution des pratiques en fonction de ti (ti*2 donne le nombre d'année mise en place de la logistique) 

    double[] sigmoidePraticTriDa; // stocke l'évolution des pratiques en fonction de ti (ti*2 donne le nombre d'année adoption totale prévue de la pratique (alpha2baseDa+alpha2objectifDa)
    double[] sigmoidePraticTriDv; // 2024

    double[] sigmoideEvitGreenWaste; // stocke l'évolution du coefficient de l'evitement déchets verts à appliquer au tauxReducGreenWaste
    double alpha1BaseDa; //alpha1 à t0 (ie taux de déchets alimentaires vers compostage local)
    double alpha1BaseDv; //alpha1 à t0 (ie taux de déchets verts vers compostage local)
    double alpha1ObjectifDa; // augmentation maximale de alpha1 déchets alimetaires (part vers compost local) au cours de la simulation, alpha 1 croit de la moitié de alpha1ObjectifDa après ti année (param sigmoide)
    double alpha1ObjectifDv; // idem alpha1ObjectifDa mais pour déchets verts
    double alpha2baseDa; // taux initial de pratique du tri - alpha2baseDa = 70% jusqu’à alpha2objectifDa =95%
    double alpha2objectifDa; // taux final de pratique du tri est alpha2baseDa + alpha2objectifDa - alpha2baseDa = 70% jusqu’à alpha2objectifDa =95%
    double alpha2baseDv; // idem ci-dessous mais pour déchets verts
    double alpha2objectifDv;

    // TODO HYPOTHESE PAS DE CAPACITE POUR LA DECHETTERIE + 1 SEULE DECHETTERIE PAR TERRITOIRE
    double BaInit; // quantité initiale de production de déchets alimentaires par hab/an en tonnes
    double BvInit; // quantité initiale de production de déchets verts par hab/an en tonnes            

    double alpha3t0; // part du flux allant en déchetterie

    double accroissementAnnuel; // accroissement annuel de la population 
    int sizePop; // taille population sous-territoire

    double duraImplemCompo; // point d'inflexion de la courbe de sigmoïde logistique local compost
    double tiPCL; // point d'inflexion de la courbe de sigmoïde pratique local compost
    double duraImplemCollect; // point d'inflexion de la courbe de sigmoïde logistique collecte
    double tiPT; // point d'inflexion de la courbe de sigmoïde pratique tri
    double tiGreenWaste; // point d'influxion de la courbe sigmoide d'évitement des déchets verts

    double tauxEviteGreenWate; // taux de diminution de la production de Bv par évitement

    double[] P; // taille de la population à chaque temps t
    double[] B; //Quantité de biodéchets produit pour la population
    double[] Bv; //Quantité de déchets verts  pour la population
    double[] Ba; //Quantité de biodéchets alimentaires produit en prenant en compte le gaspillage alimentaire
    double[] Rtot; // Food waste to be removed as a result of reducing food waste
    double[] R; // //rate of reduction of food waste at t as a function of objGaspi at term (term = 2xtiantigaspi of the sigmoid) AFTER THAT CONSTANT RATE
    double[] G; // Quantité réduction du gaspillage alimentaire 
    double[] alpha1Da; // Proportion des biodéchets alimentaires qui vont au compostage local en prenant en compte les acteurs
    double[] alpha1Dv; // Proportion des déchets verts qui vont au compostage local en prenant en compte les acteurs
    double[] alpha3; // Proportion des biodéchets qui vont à la déchetterie 
    double[] alpha2Da; //taux de pratique de tri déchets alimentaires pour la collecte en porte à porte en 2O18
    double[] alpha2Dv; // idem ci-dessus mais pour déchets verts
    double[] C_log; // Issu sigmoide pour évolution mise en place logistique de compostage individuelle
    double[] C_pop; // Issu sigmoide donnant vitesse évolution individu pour pratique compostage
    double[] Lv; // Quantité de biodéchets verts qui part vers le compostage local
    double[] La; // Quantité de déchets alimentaires qui part vers le compostage local
    double[] La_bis; // Quantité de déchets alimentaires dans compostage local après gestion du surplus
    double[] Lv_bis; // Quantité de biodéchets verts dans compostage local après gestion du surplus
    double[] Lfinal; // Quantité de biodéchets dans le compostage local après gestion éventuel surplus
    double[] sL; // Surplus du compostage local
    double[] sLv; // Quantité de biodéchets verts retirés du compostage local à cause du surplus
    double[] sLa; // Quantité de biodéchets alimentaires retirés du compostage local  à cause du surplus
    double[] sLbis; // Gestion intermédiaire des surplus du compostage local (adjusted composted )
    double[] Dv; // Quantité de biodéchets verts qui part vers la déchetterie (= totalité du flux déchetterie)
    double[] Av; // Quantité de biodéchets verts qui part vers la collecte
    double[] Aa; // Quantité de déchets alimentairess qui part vers la collecte
    double[] Afinal; // Quantité de biodéchets dans collecte dans gestion des éventuels surplus
    double[] Aa_bis; // Quantité de déchets alimentaires dans collecte après gestion du surplus
    double[] Av_bis; // Quantité de déchets verts dans collecte après gestion du surplus
    double[] sAa; //Quantité de biodéchets alimentaires retirés collecte à cause du surplus
    double[] sAv; //Quantité de biodéchets verts retirés collecte à cause du surplus
    double[] sAa_bis; // Quantité de déchets alimentaires retirés collecte à cause du surplus
    double[] sAv_bis; // Quantité de biodéchets verts retirés collecte à cause du surplus
    double[] sA; // Surplus de la collecte n°1
    double[] sAbis; // Surplus de la collecte n° 2
    double[] O; //Quantité de biodéchets verts qui part vers les OMR
    double objectifAntiGaspillage; // part de population objectif à sensibiliser à l'anti-gaspillage
    //double[] Ptri; // probabilité de trier à chaque temps

    //double firstYearDechetterieDechetVert;
    //double firstYearVolumeDechetOMR;
    int subTerritoryName;

    double[] propPopDesserviCollDA; // proportion de la population desservi par la collecte alimentaire une année t
    double[] nbKgCollectHabDesservi; // nombre de kilos de déchets alimentaires collectés par habitant desservi par la collecte une année t 
    double[] nbKgOMRHab; // nombre de kilos de déchets alimentaires collectés par habitant desservi par la collecte une année t 
    //EquipmentValorisation myOwnEquip; // POUR L'INSTANT ON CONSIDERE QUE LES SUBTERRITORIES N ONT PAS D EQUIPEMENTS PROPRE OU LEUR CAPACITES SONT SOMMES POUR FAIRE UN EQUIPEMENT COMMUN
    double[] tauxReductionDechetVert; // taux de réduction des déchets verts entrant en déchetterie
    int ident; // numéro du sous-territoire 

    public Subterritory2(Myterritory2 mt, int id) {
        myTerre = mt;
        ident = id;
    }

    // TODO J'AI FAIT L HYPOTHESE D'UNE DECHETTERIE PAR TERRITOIRE// calling the function
    public void iterate(int year) {
        sigmoideLogCompostLocal[year] = linear(year, duraImplemCompo);
        sigmoideLogCollecte[year] = linear(year, duraImplemCollect); // logistic sorting capacity : duration = 7 
        if (myTerre.useSocialDynamics) {
            //sigmoideLogCompostLocal[year] = sigmoide(year, duraImplemCompo); // logistic composting capacity
             // logistic composting capacity : duration = 7 .

            sigmoidePraticCompostLocalDa[year] = sigmoide(year + timeBeforeInitAlpha1BaseDa, tiPCL ); // Practical composting behvior : should i add +timeBeforeInitAlpha1BaseDa to tiPCL ?

            sigmoidePraticCompostLocalDv[year] = sigmoide(year + timeBeforeInitAlpha1BaseDv, tiPCL  ); // Practical composting behvior : Should i add timeBeforeInitAlpha1BaseDv to tiPCL ?

            //sigmoideLogCollecte[year] = sigmoide(year, duraImplemCollect); // Logistic collection capacity
            

            sigmoidePraticTriDa[year] = sigmoide(year + timeBeforeInitAlpha2BaseDa, tiPT ); // Practical sorting behavior
            sigmoidePraticTriDv[year] = sigmoide(year +  timeBeforeInitAlpha2BaseDv, tiPT ); // Practical sorting behavior
            //System.err.println(year+" "+tiPT+" timebefore "+timeBeforeInitAlpha2BaseDv+" sig "+sigmoide(year + timeBeforeInitAlpha2BaseDv, tiPT));
            sigmoideEvitGreenWaste[year] = sigmoide(year, tiGreenWaste); //reduction green waste adoption 
        }
        

        computeProducedBioWaste(year);
        // Étape 1 : Répartition des biodéchets
        computeFluxRates(year); // alpha
        localCompost(year); // C
        collect(year); // S
        dechetterie(year); //DV
        ordureMenagereResiduelle(year);// OMR
        //myOwnEquip.iterate(year, this); // POUR L'INSTANT ON CONSIDERE QUE LES SUBTERRITORIES N ONT PAS D EQUIPEMENTS PROPRE OU LEUR CAPACITES SONT SOMMES POUR FAIRE UN EQUIPEMENT COMMUN
    }

    public void computeProducedBioWaste(int y) {
        P[y] = P[y - 1] * (1 + accroissementAnnuel); // Taille de la population à un instant t
       
        R[y] = objectifAntiGaspillage * myTerre.sigmoideAntiGaspi[y]; // taux de réduction du gaspillage alimentaire à t en fonction de objGaspi à terme
        //G[y] = myTerre.einit * P[y]; // einit=volume in tons de déchets alimentaires gaspillé en 2018 par an et par habitant, donc total gaspillé par la population
        //G[y] = myTerre.einit * P[y - 1];
        Rtot[y] = R[y] * G[y]; //Nombre de déchets alimentaires à retirer dû à la réduction du gaspillage alimentaire
        
        //Bv[y] = (BvInit - (BvInit * sigmoideEvitGreenWaste[y] * tauxEviteGreenWate)) * P[y]; // Quantité de biodéchets vert produit par les habitants
        Bv[y] = BvInit * (1 - tauxEviteGreenWate * myTerre.sigmoideAntiGaspi[y] ) * P[y];
//Bv[y] = (BvInit - (BvInit * sigmoideEvitGreenWaste[y] * tauxEviteGreenWate)) * P[y - 1];        
        //System.err.println(tauxEviteGreenWate+" "+sigmoideEvitGreenWaste[y]);
        //double e = myTerre.einit/BaInit ; // the percentage of edible part
        Ba[y] = BaInit * (1 - objectifAntiGaspillage * myTerre.sigmoideAntiGaspi[y] * myTerre.einit) * P[y];//𝒃_𝒇^𝒑  (𝟏−𝒐_𝒇^𝒑   𝒁(𝒕,𝒎_𝒇^𝒑 )   𝒆)  𝑷(𝒕) it was (y-1)I have change the parametersation of the model //Quantité de biodéchets alimentaires produit par les habitants en prenant en compte le gaspillage alimentaire
        //System.err.println(Ba[y]+" "+BaInit+" "+P[y]+" "+Rtot[y]+" "+R[y]+" "+G[y]+" "+myTerre.objGaspi+" "+myTerre.einit+" "+myTerre.sigmoideAntiGaspi[y]);
        B[y] = Bv[y] + Ba[y]; //Quantité de biodéchets produit 
    }

    // computation of the intention to act (realised if the capacity is sufficient)
    public void computeFluxRates(int y) {// social behaviour
        double trucDa;
        double trucDv;
        alpha1Da[y] = Math.min((alpha1BaseDa + ((1 - alpha1BaseDa) * sigmoidePraticCompostLocalDa[y - 1])), 1.0);
        alpha1Dv[y] = Math.min((alpha1BaseDv + ((1 - alpha1BaseDv) * sigmoidePraticCompostLocalDv[y - 1])), 1.0);// Proportion des biodéchets qui vont au compostage local en prenant en compte les acteurs   
        alpha2Da[y] = alpha2baseDa + ((1 - alpha2baseDa) * sigmoidePraticTriDa[y]); // on privilégie le désir de compost et supposons que les gens composte ou participe à la collecte
        trucDa = alpha1Da[y] + alpha2Da[y];
        if (trucDa > 1.0) {
            alpha2Da[y] = (1 - alpha1Da[y]);
        }
        alpha2Dv[y] = alpha2baseDv + ((alpha2objectifDv - alpha2baseDv) * sigmoidePraticTriDv[y]);
        //System.err.println(y+" "+alpha2baseDv+" "+alpha2Dv[y]+" "+sigmoidePraticTriDv[y]) ;
        trucDv = alpha1Dv[y] + alpha2Dv[y];
        if (trucDv > 1.0) {
            alpha2Dv[y] = 1.0 - alpha1Dv[y];
        }
        alpha3[y] = 1 - alpha1Dv[y] - alpha2Dv[y]; // ne concerne que les déchets verts qui seuls vont en déchetterie
        
        //System.err.println("alpha2baseDa: " + alpha2baseDa);
        // System.err.println("alpha2baseDv: " + alpha2baseDv);
        // System.err.println("alpha2objectifDa: " + alpha2objectifDa);
        // System.err.println("alpha2objectifDv: " + alpha2objectifDv);
    }

    /**
     * Étape 1 : Répartition des biodéchets
     */
    public void localCompost(int y) {
        Lv[y] = alpha1Dv[y] * Bv[y]; // Quantité de déchets verts qui part vers le compostage local
        La[y] = alpha1Da[y] * Ba[y]; // Quantité de déchets alimentaires qui part vers le compostage local
        //→ HYPOTHÈSE : Si L[y] > K1 : Nous avons un surplus, alors on va : Mettre en premier des biodéchets verts Lv[y] dans la déchetterie puis si Lv[y] est vide et qu’il reste du surplus et que L[y] est toujours supérieur à K1 alors on met des biodéchets alimentaires La[y] dans la collecte.
        if (y == yearRef) { // Calibration pour le cas du SBA
            K1init = Lv[y] + La[y];
        }
        K1courant[y] = K1init + ((K1cible - K1init) * sigmoideLogCompostLocal[y]);// here sigmoide become linear see iteration
        //System.err.println("sigmoide " + sigmoideLogCompostLocal[y] + " K1courant " + K1courant + " Lv " + Lv[y] + " La " + La[y]+" Py "+P[y-1]+" "+alpha1Dv[y]+" Bv "+Bv[y]+" alpha1Dv "+alpha1Dv[y]+" alpha3 "+alpha3[y]);
        if ((Lv[y] + La[y]) > K1courant[y]) {
            sL[y] = Lv[y] + La[y] - K1courant[y]; // Premier calcul du surplus
            Lv_bis[y] = Math.max(Lv[y] - sL[y], 0.0); //Quantité de biodéchets verts après l’application du surplus
            sLbis[y] = Math.max(0.0, (Lv_bis[y] + La[y] - K1courant[y])); // Deuxième calcul du surplus pour voir si il nous reste du surplus après avoir retiré des biodéchets verts
            La_bis[y] = Math.max(La[y] - sLbis[y], 0.0); //Quantité de biodéchets alimentaires après l’application du surplus
            sLa[y] = Math.min(sLbis[y], La[y]); //Quantité de biodéchets alimentaires retirés à cause du surplus
            sLv[y] = Math.min(sL[y], Lv[y]); //Quantité de biodéchets verts retirés à cause du surplus
            Lv[y] = Lv_bis[y];
            La[y] = La_bis[y];
        }
        Lfinal[y] = La[y] + Lv[y]; //Valeurs de L après avoir retiré le surplus 
    }

    public void collect(int y) {
        Av[y] = alpha2Dv[y] * Bv[y]; // Quantité de déchets verts qui part vers la collecte
        Aa[y] = (alpha2Da[y] * Ba[y]) + sLa[y]; //→Quantité de biodéchets alimentaires qui part vers la collecte
        //if (y == 1) {
        //System.err.println(alpha2Dv[y] + " ka " + KAinit + " Av " + Av[y] + " Aa " + Aa[y] + " a3dv " + alpha3[y] + " a1dv " + alpha1Dv[y]);
        //}
        // → HYPOTHÈSE : si A[y] > KA : Nous avons un surplus, alors on va : Mettre en premier des biodéchets verts Av[y] dans la déchetterie puis si Av[y] est vide et qu’il reste du surplus et que A[y] est toujours supérieur à KA alors on met des biodéchets alimentaires Aa[y] dans les OMR.
        KAcourant[y] = KAinit + ((KAcible - KAinit) * sigmoideLogCollecte[y]);
        //if (ident==1) System.err.println("year "+y+" ident terr "+ident+" Kacourant "+KAcourant) ;
        if ((Av[y] + Aa[y]) > KAcourant[y]) {
            sA[y] = Aa[y] + Av[y] - KAcourant[y]; // →Premier calcul du surplus
            Av_bis[y] = Math.max(Av[y] - sA[y], 0.0); //Quantité de déchets verts après l’application du surplus
            sAbis[y] = Math.max(0.0, (Aa[y] + Av_bis[y] - KAcourant[y])); // Deuxième calcul du surplus pour voir si il nous reste du surplus après avoir retiré des biodéchets verts
            //Av_bis[y] = Math.max(Av[y] - sA[y], 0.0); //Quantité de biodéchets verts après l’application du surplus
            Aa_bis[y] = Math.max(Aa[y] - sAbis[y], 0.0); // →Quantité de biodéchets alimentaire après l’application du surplus
            sAv[y] = Math.min(sA[y], Av[y]); // Quantité de biodéchets verts retirés à cause du surplus VA A LA DECHETTERIE !!!!
            //if (sAv[y]<0.0) { System.err.println(" jfjqkdksdj "+sA[y]+" "+Av[y]); }
            //Dv[y]=Dv[y]+sAv[y]; // remise de surplus à la déchetterie
            sAa[y] = Math.min(sAbis[y], Aa[y]); // →Quantité de biodéchets alimentaires retirés à cause du surplus
            Av[y] = Av_bis[y];
            Aa[y] = Aa_bis[y];
        }
        Afinal[y] = Av[y] + Aa[y]; // →Valeur de A[y] après avoir retiré le surplus
    }

    public void dechetterie(int y) {
        Dv[y] = alpha3[y] * Bv[y] + sLv[y] + sAv[y]; // Quantité de biodéchets verts qui part vers la déchetterie
        //System.err.println(Dv[y]+" alpha3 "+alpha3[y]+" sLv "+sLv[y]+" Bv "+Bv[y]+" sAv "+sAv[y]) ;
        //System.err.println(Dv[y]+" "+alpha3[y]+" "+Bv[y]+" "+sLv[y]+" "+sAv[y]);
        //if(Dv[y]<0.0) System.err.println(alpha3[y]+" "+Bv[y]+" "+sLv[y]+" "+sAv[y]);//System.err.println(sLv[y]+" "+sAv[y]+) ;
    }

    public void ordureMenagereResiduelle(int y) {
        O[y] = (1 - alpha1Da[y] - alpha2Da[y]) * Ba[y] + sAa[y]; // Quantité de biodéchets alimentaires qui part vers les OMR
        if (O[y] < 0) {
            System.err.println(alpha2Da[y] + " alpha1 " + alpha1Da[y] + " Ba " + Ba[y] + " sAa " + sAa[y]);
        }
    }

    public double sigmoide(double x, double ti) {
        double t = Math.pow(x, 5);
        double z = t / (t + Math.pow(ti, 5)); // ti est le point d'inflexion de la sigmoide (la valeur 0.5 est renvoyée la tième année)
        return z;
    }

    public double linear(double t, double duration) {
        return Math.min(t / duration, 1.0);
    }

    public int calculateTimeBeforeInit(double alpha_base, double ti) { //time process
        int timeBeforeInit = 0;
        // Continuously calculate the sigmoid value at increasing time steps until it meets or exceeds alpha_base.
        if (alpha_base > 0) {

            double sigmoideValue = sigmoide(timeBeforeInit, ti);
            while (sigmoideValue < alpha_base) { //  (the while-loop will not be entered if alpha_base is less than or equal to zero).
                timeBeforeInit++;
                sigmoideValue = sigmoide(timeBeforeInit, ti);
                //System.err.println(alpha_base + " " + ti + " " + timeBeforeInit + " " + sigmoideValue);
            }
        }
        return timeBeforeInit;
    }

    public void init(int sizeData, double[] params, int refYear) {
        yearRef = refYear;
        subTerritoryName = (int) params[0]; // identifiant numérique du sous territoire
        duraImplemCompo = params[1]; // point d'inflexion de la courbe de la sigmoide 
        duraImplemCollect = params[2]; // point d'inflexion de la courbe de la sigmoide 
        tiPCL = params[3]; // point d'inflexion de la courbe de la sigmoide 
        tiPT = params[4]; // point d'inflexion de la courbe de la sigmoide 
        BaInit = params[5]; // Quantité de biodéchet produit par habitant
        BvInit = params[6]; // part des déchets verts dans b
        alpha1BaseDa = params[7];
        alpha1BaseDv = params[8];
        alpha2baseDa = params[9]; // pratique de tri pour collecte porte-à-porte init (de 70% jusqu’à95%)
        alpha2objectifDa = params[10]; // pratique de tri pour collecte porte-à-porte objectif (de 70% jusqu’à95%)
        alpha1ObjectifDa = params[11]; // souhait d'augmenter de la pratique compostage local déchets alimentaires
        alpha1ObjectifDv = params[12]; // idem ci-dessus mais pour déchets verts
        alpha2baseDv = params[13];// initial sorting of green waste
        alpha2objectifDv = params[14];
        K1init = params[15]; // accroissement annuel pop selon statistiques nationales
        K1cible = params[16]; //K(expected)^c
        KAinit = params[17]; // accroissement annuel pop selon statistiques nationales
        KAcible = params[18];
        sizePop = (int) params[19]; // taille pop sous territoire
        accroissementAnnuel = params[20]; // taille pop sous territoire
        tiGreenWaste = params[21]; //tiActionsEvitementDechetsVerts
        tauxEviteGreenWate = params[22]; //tauxEvitementDechetsVertsHorizon2024        
        objectifAntiGaspillage = params[23];
        // Calculating time before the simulation starts for each alpha value (calculating time process)
        timeBeforeInitAlpha1BaseDa = calculateTimeBeforeInit(alpha1BaseDa, tiPCL);
        timeBeforeInitAlpha1BaseDv = calculateTimeBeforeInit(alpha1BaseDv, tiPCL);
        timeBeforeInitAlpha2BaseDa = calculateTimeBeforeInit(alpha2baseDa, tiPT);
        timeBeforeInitAlpha2BaseDv = calculateTimeBeforeInit(alpha2baseDv, tiPT);
        //System.err.println("ms "+tiPT) ;

        P = new double[sizeData]; //sizeData = nbyears of simulation + 1 (for the initial state)
        Arrays.fill(P, 0.0);
        P[0] = sizePop;
        R = new double[sizeData];
        Arrays.fill(R, 0.0);
        Rtot = new double[sizeData];
        Arrays.fill(Rtot, 0.0);
        G = new double[sizeData];
        Arrays.fill(G, 0.0);
        B = new double[sizeData];
        Arrays.fill(B, 0.0);
        Bv = new double[sizeData];
        Arrays.fill(Bv, 0.0);
        Ba = new double[sizeData];
        Arrays.fill(Ba, 0.0);
        alpha1Da = new double[sizeData];
        Arrays.fill(alpha1Da, 0.0);
        alpha1Dv = new double[sizeData];
        Arrays.fill(alpha1Dv, 0.0);
        alpha3 = new double[sizeData];
        Arrays.fill(alpha3, 0.0);
        C_log = new double[sizeData];
        Arrays.fill(C_log, 0.0);
        C_pop = new double[sizeData];
        Arrays.fill(C_pop, 0.0);
        Lfinal = new double[sizeData];
        Arrays.fill(Lfinal, 0.0);
        Lv = new double[sizeData];
        Arrays.fill(Lv, 0.0);
        La = new double[sizeData];
        Arrays.fill(La, 0.0);
        sL = new double[sizeData];
        Arrays.fill(sL, 0.0);
        sLa = new double[sizeData];
        Arrays.fill(sLa, 0.0);
        sLv = new double[sizeData];
        Arrays.fill(sLv, 0.0);
        Lv_bis = new double[sizeData];
        Arrays.fill(Lv_bis, 0.0);
        La_bis = new double[sizeData];
        Arrays.fill(La_bis, 0.0);
        sLbis = new double[sizeData];
        Arrays.fill(sLbis, 0.0);
        Dv = new double[sizeData];
        Arrays.fill(Dv, 0.0);
        sAv = new double[sizeData];
        Arrays.fill(sAv, 0.0);
        O = new double[sizeData];
        Arrays.fill(O, 0.0);
        KAcourant = new double[sizeData];
        Arrays.fill(KAcourant, 0.0);
        K1courant = new double[sizeData];
        Arrays.fill(K1courant, 0.0);
        //Fv_bis = new double[sizeData];
        //Arrays.fill(Fv_bis, 0.0);

        sigmoideLogCompostLocal = new double[sizeData];
        Arrays.fill(sigmoideLogCompostLocal, 0.0);
        sigmoidePraticCompostLocalDa = new double[sizeData];
        Arrays.fill(sigmoidePraticCompostLocalDa, 0.0);
        sigmoidePraticCompostLocalDv = new double[sizeData];
        Arrays.fill(sigmoidePraticCompostLocalDv, 0.0);
        sigmoideLogCollecte = new double[sizeData];
        Arrays.fill(sigmoideLogCollecte, 0.0);
        sigmoidePraticTriDa = new double[sizeData];
        Arrays.fill(sigmoidePraticTriDa, 0.0);
        sigmoidePraticTriDv = new double[sizeData];
        Arrays.fill(sigmoidePraticTriDv, 0.0);
        sigmoideEvitGreenWaste = new double[sizeData];
        Arrays.fill(sigmoideEvitGreenWaste, 0.0);
        Av = new double[sizeData];
        Arrays.fill(Av, 0.0);
        Aa = new double[sizeData];
        Arrays.fill(Aa, 0.0);
        Aa_bis = new double[sizeData];
        Arrays.fill(Aa_bis, 0.0);
        Av_bis = new double[sizeData];
        Arrays.fill(Av_bis, 0.0);
        Afinal = new double[sizeData];
        Arrays.fill(Afinal, 0.0);
        sA = new double[sizeData];
        Arrays.fill(sA, 0.0);
        sAbis = new double[sizeData];
        Arrays.fill(sAbis, 0.0);
        sAa = new double[sizeData];
        Arrays.fill(sAa, 0.0);

        propPopDesserviCollDA = new double[sizeData];
        Arrays.fill(propPopDesserviCollDA, 0.0);
        nbKgCollectHabDesservi = new double[sizeData];
        Arrays.fill(nbKgCollectHabDesservi, 0.0);
        nbKgOMRHab = new double[sizeData];
        Arrays.fill(nbKgOMRHab, 0.0);
        tauxReductionDechetVert = new double[sizeData];
        Arrays.fill(tauxReductionDechetVert, 0.0);
        alpha2Dv = new double[sizeData];
        Arrays.fill(alpha2Dv, 0.0);
        alpha2Da = new double[sizeData];
        Arrays.fill(alpha2Da, 0.0);
        Ba[0] = BaInit * P[0];
        Bv[0] = BvInit * P[0];
        Lv[0] = Bv[0] * alpha1BaseDv;
        La[0] = Ba[0] * alpha1BaseDa;
        Aa[0] = Ba[0] * alpha2baseDa;
        Av[0] = Bv[0] * alpha2baseDv;
        Dv[0] = Bv[0] - Lv[0] - Av[0];
        O[0] = Ba[0] - La[0] - Aa[0];

    }

    public void printVector(double[] edit) {
        for (int i = 0; i < edit.length; i++) {
            System.err.print(edit[i] + "\t");
        }
        System.err.println();
    }

    public void indicSubTerritories(int year) {
        //System.err.print("year "+year+" "+" KAcourant "+KAcourant+" KA "+KA+" nb hab desservi ") ;
        double nbHabDesservi = Math.min(P[year], (double) KAcourant[year] / (39.0 / 1000.0)); // 39 kg [converti en tonnes] correspond à quantité article 5 arrêté du 7 juillet 2021 (base calcul pour qté détournée par habitant desservi par la collecte de dechets alimentaires)  
        //System.err.print(nbHabDesservi) ;
        propPopDesserviCollDA[year] = nbHabDesservi / P[year];
        if (nbHabDesservi > 0) {
            nbKgCollectHabDesservi[year] = (Aa[year] * 1000.0) / nbHabDesservi;
        }
        nbKgOMRHab[year] = (O[year] * 1000.0) / P[year];
        tauxReductionDechetVert[year] = (Dv[year] - Dv[0]) / Dv[0]; // this evolution perecentage of green waste in dechetre, negative value means reduction

    }

    public void printTrajectory(int year) {
        System.out.print(ident + ";");
        System.out.print(P[year] + ";");
        System.out.print(Ba[year] + ";");
        System.out.print(Bv[year] + ";");
        System.out.print(alpha1Da[year] + ";");
        System.out.print(alpha1Dv[year] + ";");
        System.out.print(La[year] + ";");
        System.out.print(sLa[year] + ";");
        //System.err.println("je viens d'écrire SLA") ;
        System.out.print(Lv[year] + ";");
        System.out.print(sLv[year] + ";");
        System.out.print(K1courant[year] + ";");
        System.out.print(alpha2Da[year] + ";");
        System.out.print(alpha2Dv[year] + ";");
        System.out.print(Aa[year] + ";");
        System.out.print(sAa[year] + ";");
        System.out.print(Av[year] + ";");
        System.out.print(sAv[year] + ";");
        System.out.print(KAcourant[year] + ";");
        System.out.print(O[year] + ";");
        System.out.print(alpha3[year] + ";");
        System.out.print(Dv[year] + ";");
    }
}
