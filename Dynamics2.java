
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
public class Dynamics2 {

    double b; // Quantity of biowaste produced per inhabitant (green and food waste)
    double qv; // share of green waste in bv // coefficioent 
    double qa; // share of food waste in b / coifeccient  
    double alpha1_base; //alpha1 à t0 (ie taux vers compostage local)
    double alpha3t0; // part du flux allant en déchetterie
    double g; // gaspillage 0.03
    // double objGreen; 
    double objGaspi; // objectif taux réduction gaspillage
    double K1courant; // capacité maximale du compostage local au cours de sa mise en place
    double KAcourant; // capacité maximale sur la collecte au cours de sa mise en place
    double K1; // capacité maximale du compostage local
    double KA; // capacité maximale sur la collecte afin de représenter la capacité maximale des poubelles vertes 
    // CALCUL Capacité KA : P(t) x TaillePoubelle(tonne) (non implémenté ici, peut être calculer différemment selon comment se fait la collecte - point de collecte collectif par exemple)
    double K2; // capacité maximale du méthaniseur
    double K3; // capacité maximale de la plateforme de compostage professionnel
    double theta1; //  0.7 Sigmoid Ptri[y]: Social dynamics modelled by the sigmoid, which consists of implementing a sorting incentive ranging from theta1 = 70% to theta2 =95%.
    double theta2; // 0.95  Sigmoid Ptri[y]: Social dynamics modelled by the sigmoid, which consists of implementing a sorting incentive ranging from theta1 = 70% to theta2 =95%.
    double seuil; //0.3 (threshold) maximum increase in alpha1 (share towards local compost) during the simulation, alpha 1 increases by half the threshold after ti year (sigmoid param)
    double accroissementAnnuel; // accroissement annuel de la population 
    // double tiAgreen; 
    double tiAG; // point d'inflexion de la courbe de sigmoïde pratique anti-gaspillage
    double tiLCL; // point d'inflexion de la courbe de sigmoïde logistique local compost
    double tiPCL; // point d'inflexion de la courbe de sigmoïde pratique local compost
    double tiLC; // point d'inflexion de la courbe de sigmoïde logistique collecte
    double tiPT; // point d'inflexion de la courbe de sigmoïde pratique tri

    double[] P; //  population size at each time t
    double[] B; //Quantité de biodéchets produit pour la population
    double[] Bv; //Quantité de déchets verts  pour la population
    double[] Ba; //Quantité de biodéchets alimentaires produit en prenant en compte le gaspillage alimentaire
    //double[] Rtotgreen
    double[] Rtot; // Déchets alimentaires à retirer du fait de la réduction du gaspillage alimentaire
    // double[] R_green; taux de reduction du green waste 
    double[] R; // // taux de réduction du gaspillage alimentaire à t en fonction de objGaspi à terme ??????????? APRES CA TAUX CONSTANT !!!!!!!!!!!!!
    double[] G; // Quantité réduction du gaspillage alimentaire 
    double[] alpha1; // Proportion des biodéchets qui vont au compostage local en prenant en compte les acteurs
    double[] alpha2; // Proportion des biodéchets qui vont à la collecte ou l’OMR
    double[] alpha3; // Proportion des biodéchets qui vont à la déchetterie 
    double[] C_log; // logistic comppost , Issu sigmoide pour évolution mise en place logistique de compostage individuelle
    double[] C_pop; // practic compost,  Issu sigmoide donnant vitesse évolution individu pour pratique compostage
    double[] Lv; // Quantité de biodéchets verts qui part vers le compostage local
    double[] La; // Quantité de déchets alimentaires qui part vers le compostage local
    double[] La_bis; // Quantité de déchets alimentaires dans compostage local après gestion du surplus
    double[] Lv_bis; // Quantité de biodéchets verts dans compostage local après gestion du surplus
    double[] Lfinal; // Quantité de biodéchets dans le compostage local après gestion éventuel surplus
    double[] sL; // Surplus du compostage local
    double[] sLv; // Quantité de biodéchets verts retirés du compostage local à cause du surplus
    double[] sLa; // Quantité de biodéchets alimentaires retirés du compostage local  à cause du surplus
    double[] sLbis; // Gestion intermédiaire des surplus du compostage local
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
    double[] Ptri; // probabilité de trier à chaque temps/ probability of sorting at each time
    double[] I; //Quantité de biodéchets verts qui part vers l’incinérateur
    double[] Mv; // Quantité de biodéchets verts qui part vers le méthaniseur
    double[] Ma; // Quantité de biodéchets alimentaire qui part vers le méthaniseur
    double[] Mfinal; // Valeur de M[y] méthaniseur après avoir retiré le surplus s'il y en avait
    double[] sM; // Premier calcul du surplus
    double[] sMbis; // Deuxième calcul du surplus pour voir si il nous reste du surplus après avoir retiré des biodéchets verts
    double[] Ma_bis; // Quantité de biodéchets alimentaire après l’application du surplus
    double[] Mv_bis; // Quantité de biodéchets verts après l’application du surplus
    double[] sMa; // Quantité de biodéchets alimentaires retirés à cause du surplus
    double[] sMv; //Quantité de biodéchets verts retirés à cause du surplus
    double[] sF; // surplus etape 2
    double[] sFv_meth; // surplus qui part vers le méthaniseur
    double[] sFv_inci; // surplus qui part vers l’incinérateur
    //double[] Fv_bis; // Quantité de biodéchets verts après l’application du surplus
    double[] Fv; // Quantité de biodéchets verts qui part vers la collecte/ not collect its the professional composting !!!
    double[] sFv; // surplus Quantité de biodéchets verts qui part vers la collecte
    double[] sigmoideLogCompostLocal; // stocke l'évolution des pratiques en fonction de ti // ATTENTION LA VITESSE EST LA MEME POUR TOUS LES PROCESSUS CAR TI EST LE MEME POUR TOUS !!!!!!
    //double [] sigmoideAntiGreen; // 
    double[] sigmoideAntiGaspi; // stocke l'évolution des pratiques en fonction de ti // ATTENTION LA VITESSE EST LA MEME POUR TOUS LES PROCESSUS CAR TI EST LE MEME POUR TOUS !!!!!!
    double[] sigmoidePraticCompostLocal; // stocke l'évolution des pratiques en fonction de ti // ATTENTION LA VITESSE EST LA MEME POUR TOUS LES PROCESSUS CAR TI EST LE MEME POUR TOUS !!!!!!
    double[] sigmoideLogCollecte; // stocke l'évolution des pratiques en fonction de ti // ATTENTION LA VITESSE EST LA MEME POUR TOUS LES PROCESSUS CAR TI EST LE MEME POUR TOUS !!!!!!
    double[] sigmoidePraticTri; // stocke l'évolution des pratiques en fonction de ti // ATTENTION LA VITESSE EST LA MEME POUR TOUS LES PROCESSUS CAR TI EST LE MEME POUR TOUS !!!!!!
    boolean[] checkFluxStage1;
    boolean[] checkFluxStage2;

    public Dynamics2(double[] parameters) {
        int borne = (int) parameters[14] + 1; // nbYears of simulation +1 
        init(borne, parameters);
        for (int i = 1; i < borne; i++) {
            iterate(i);
        }

    }

    public void iterate(int year) {
        sigmoideAntiGaspi[year] = sigmoide(year, tiAG);
        //SigmaoidAntiGreen [year] = sigmoide(year, tiAGreen);
        sigmoideLogCompostLocal[year] = sigmoide(year, tiLCL);
        sigmoidePraticCompostLocal[year] = sigmoide(year, tiPCL);
        sigmoideLogCollecte[year] = sigmoide(year, tiLC);
        sigmoidePraticTri[year] = sigmoide(year, tiPT);
        // Production de biodéchets 
        computeProducedBioWaste(year);
        // Étape 1 : Répartition des biodéchets
        computeFluxRates(year);
        localCompost(year);
        collect(year);
        dechetterie(year);
        ordureMenagereResiduelle(year);
        // Étape 2 : Traitement des biodéchets → HYPOTHÈSE
        computeMethanisation(year); // first car priorité 1
        computeCompostPlatform(year); // second car priorité 2
        computeIncinerator(year);
        // Controle de bugs
        checkConservationFlux(year);
    }

    /**
     * Production de biodéchets
     */
    public void computeProducedBioWaste(int y) {
        P[y] = P[y - 1] * (1 + accroissementAnnuel); // Nombre de la population à un instant t
        //sigmoïde gaspillage : Dynamique sociale modélisé par la sigmoïde qui consiste à implémenter une incitation à la réduction du gaspillage allant de 0% à 50%.
        R[y] = objGaspi * sigmoideAntiGaspi[y]; // Food Waste Reduction Rate Calculation: 
        G[y] = g * P[y];// This calculates the total waste generation at time y, presumably before any waste reduction strategies are implemented. g is likely a factor representing per capita waste generation.
        Rtot[y] = R[y] * G[y]; //Total Reduced Waste Calculation: This calculates the total amount of food waste reduced at time y, by multiplying the reduction rate with the initial waste generation.
        Bv[y] = b * P[y] * qv; // Quantité de biodéchets vert produit par les habitants  - Rtotgreen[y]
        Ba[y] = b * P[y] * qa - Rtot[y]; //Quantité de biodéchets alimentaires produit par les habitants en prenant en compte le gaspillage alimentaire
        B[y] = Bv[y] + Ba[y]; //Quantité de biodéchets produit 
    }

    
    public void computeProducedBioWaste2(int y) {
    // Population growth calculation
    P[y] = P[y - 1] * (1 + accroissementAnnuel);

    // Food Waste Reduction Progress Function for food waste
    // ZFunction represents the Z(t, m_f^p) described in the second method
    
    // Calculation of green waste generation
    Bv[y] = b * qv * P[y]; // Equation (3) adapted from the second method  Bv[y] = qv * (1 - objGreen * sigmoideAntiGreen[y]) * P[y]
    
     // Calculation of food waste generation
    Ba[y] = qa * (1 - objGaspi * sigmoideAntiGaspi[y] * g) * P[y]; // Equation (1) adapted from the second method


    // Total bio-waste generation is the sum of food and green waste
    B[y] = Ba[y]+ Bv[y];
}
    
    
   
    
    
    
    public void computeFluxRates(int y) {
        // calculs en vue de distribution en flux de quantité de biodéchets // calculations for flow distribution of quantities of biowaste
        Ptri[y] = theta1 + (theta2 - theta1) * sigmoidePraticTri[y]; //Proportion du tri réalisé par les habitants /Proportion of sorting carried out by residents
        //sigmoïde alpha1 : Dynamique sociale modélisé par la sigmoïde qui consiste  à implémenter une incitation au compostage en prenant en compte les acteurs et les moyens logistiques.
        //Intervalle(pour seuil =0.3) = [0,347;0,647]
        //C_log[y] = sigmoideLogCompostLocal[y];
        C_pop[y] = seuil * sigmoidePraticCompostLocal[y];
        //alpha1[y] = alpha1_base + C_log[y] * C_pop[y]; // Proportion des biodéchets qui vont au compostage local en prenant en compte les acteurs    
        alpha1[y] = alpha1_base + C_pop[y]; // Proportion des biodéchets qui vont au compostage local en prenant en compte les acteurs    
        alpha3[y] = alpha3t0; // (La valeur diminue en fonction de la variation du compostage local, en prenant delta=alpha1[y]-alpha1(t-1). On va soustraire delta à alpha3 afin d’avoir une variation d’alpha3 similaire à alpha1 et ainsi garder la somme des alphas à 1.) →Proportion des biodéchets verts qui vont à la déchetterie
        alpha2[y] = 1.0 - alpha1[y] - alpha3[y]; // Proportion des biodéchets qui vont à la collecte/sorting ou l’OMR
    }

    /**
     * Étape 1 : Répartition des biodéchets
     */
    public void localCompost(int y) {
        Lv[y] = alpha1[y] * Bv[y]; // Quantité de biodéchets verts qui part vers le compostage local
        La[y] = alpha1[y] * Ba[y]; // Quantité de biodéchets alimentaires qui part vers le compostage local
        //→ HYPOTHÈSE : Si L[y] > K1 : Nous avons un surplus, alors on va : Mettre en premier des biodéchets verts Lv[y] dans la déchetterie puis si Lv[y] est vide et qu’il reste du surplus et que L[y] est toujours supérieur à K1 alors on met des biodéchets alimentaires La[y] dans la collecte.
        //System.err.println("K1 avant " + K1);
        K1courant = sigmoideLogCompostLocal[y] * K1;
        //System.err.println("sigmoide " + sigmoideLogCompostLocal[y] + " " + K1 + " Lv " + Lv[y] + " La " + La[y]);
        if ((Lv[y] + La[y]) > K1courant) {
            //sL[y] = Math.max(0.0, (Lv[y] + La[y] - K1)); // →Premier calcul du surplus
            //sLbis[y] = Math.max(0.0, (Lv_bis[y] + La[y] - K1)); // →Deuxième calcul du surplus pour voir si il nous reste du surplus après avoir retiré des biodéchets verts
            //Lv_bis[y] = Math.max(Lv[y] - sL[y], 0.0); //→Quantité de biodéchets verts après l’application du surplus
            //La_bis[y] = Math.max(La[y] - sLbis[y], 0.0); //→Quantité de biodéchets alimentaires après l’application du surplus
            //sLa[y] = Math.min(sLbis[y], La[y]); //→Quantité de biodéchets alimentaires retirés à cause du surplus
            //sLv[y] = Math.min(sL[y], Lv[y]); //→Quantité de biodéchets verts retirés à cause du surplus
            sL[y] = Lv[y] + La[y] - K1courant; // Premier calcul du surplus
            Lv_bis[y] = Math.max(Lv[y] - sL[y], 0.0); //Quantité de biodéchets verts après l’application du surplus
            sLbis[y] = Math.max(0.0, (Lv_bis[y] + La[y] - K1courant)); // Deuxième calcul du surplus pour voir si il nous reste du surplus après avoir retiré des biodéchets verts
            La_bis[y] = Math.max(La[y] - sLbis[y], 0.0); //Quantité de biodéchets alimentaires après l’application du surplus
            sLa[y] = Math.min(sLbis[y], La[y]); //Quantité de biodéchets alimentaires retirés à cause du surplus
            sLv[y] = Math.min(sL[y], Lv[y]); //Quantité de biodéchets verts retirés à cause du surplus
            Lv[y] = Lv_bis[y];
            La[y] = La_bis[y];
        }
        Lfinal[y] = La[y] + Lv[y]; //Valeurs de L après avoir retiré le surplus 
    }

    public void collect(int y) {
        Av[y] = (1 - alpha3[y] - alpha1[y]) * Bv[y]; // Quantité de déchets verts qui part vers la collecte
        Aa[y] = Ptri[y] * (1 - alpha1[y]) * Ba[y] + sLa[y]; //→Quantité de biodéchets verts qui part vers la collecte
        // → HYPOTHÈSE : si A[y] > KA : Nous avons un surplus, alors on va : Mettre en premier des biodéchets verts Av[y] dans la déchetterie puis si Av[y] est vide et qu’il reste du surplus et que A[y] est toujours supérieur à KA alors on met des biodéchets alimentaires Aa[y] dans les OMR.
        KAcourant = sigmoideLogCollecte[y] * KA;
        if ((Av[y] + Aa[y]) > KAcourant) {
            sA[y] = Aa[y] + Av[y] - KAcourant; // →Premier calcul du surplus
            Av_bis[y] = Math.max(Av[y] - sA[y], 0.0); //Quantité de déchets verts après l’application du surplus
            sAbis[y] = Math.max(0.0, (Aa[y] + Av_bis[y] - KAcourant)); // Deuxième calcul du surplus pour voir si il nous reste du surplus après avoir retiré des biodéchets verts
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
        //if(Dv[y]<0.0) System.err.println(alpha3[y]+" "+Bv[y]+" "+sLv[y]+" "+sAv[y]);//System.err.println(sLv[y]+" "+sAv[y]+) ;
    }

    public void ordureMenagereResiduelle(int y) {
        O[y] = (1 - Ptri[y]) * (1 - alpha1[y]) * Ba[y] + sAa[y]; // Quantité de biodéchets verts qui part vers les OMR
    }

    /**
     * Étape 2 : Traitement des biodéchets → HYPOTHÈSE
     */
    public void computeMethanisation(int y) {
        Mv[y] = Av[y]; // Quantité de biodéchets verts qui part vers le méthaniseur
        Ma[y] = Aa[y]; // Quantité de biodéchets alimentaire qui part vers le méthaniseur
        Mfinal[y] = Mv[y] + Ma[y]; //Valeur de M[y] après avoir retiré le surplus s'il y en avait
        //System.err.println("MFINAL MFINAL 1 "+Mfinal[y]+" "+K2);
        // si M[y] > K2 : Nous avons un surplus, alors on va : Mettre en premier des biodéchets verts Mv[y] dans le compostage local puis si Mv[y] est vide et qu’il reste du surplus et que M[y] est toujours supérieur à K2 alors on met des biodéchets alimentaires Ma[y] dans l’incinérateur. → HYPOTHÈSE
        if ((Mv[y] + Ma[y]) > K2) {
            sM[y] = Ma[y] + Mv[y] - K2; // Premier calcul du surplus
            sMbis[y] = Math.max(0.0, Ma[y] + Mv_bis[y] - K2); // Deuxième calcul du surplus pour voir si il nous reste du surplus après avoir retiré des biodéchets verts
            Ma_bis[y] = Math.max(Ma[y] - sMbis[y], 0.0); // Quantité de biodéchets alimentaire après l’application du surplus
            Mv_bis[y] = Math.max(Mv[y] - sM[y], 0.0); // Quantité de biodéchets verts après l’application du surplus
            sMa[y] = Math.min(sMbis[y], Ma[y]); // Quantité de biodéchets alimentaires retirés à cause du surplus mis dans compostage plateforme
            sMv[y] = Math.min(sM[y], Mv[y]); //Quantité de biodéchets verts retirés à cause du surplus
            Mfinal[y] = Mv_bis[y] + Ma_bis[y]; //Valeur de M[y] après avoir retiré le surplus s'il y en avait
            //System.err.println("MFINAL MFINAL 2 "+Mfinal[y]+" "+Mv_bis[y]+" "+Ma_bis[y]);
        }
    }

    public void computeCompostPlatform(int y) {
        Fv[y] = Dv[y] + sMv[y] + sMa[y]; // Quantité de biodéchets verts qui part vers la collecte
        //if(Fv[y]<0.0) System.err.println("RATATA "+Dv[y]+" "+sMv[y]+" "+sMa[y]) ;
        //System.err.println("DJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ "+Fv[y]+" "+Dv[y]+" "+sMv[y]+" "+sMa[y]+" "+K3);
        //→ HYPOTHÈSE : Pas de Fa[y] car on n’a pas de biodéchets alimentaire dans un compostage plateforme → HYPOTHÈSE
        //→ HYPOTHÈSE : si F[y] > K3 : Nous avons un surplus, alors on va : Mettre en premier des biodéchets verts Fv[y] dans le méthaniseur si il reste de la place puis si M[y] est plein et qu’il reste du surplus alors on met des biodéchets verts restant dans l’incinérateur.
        if (Fv[y] > K3) {
            sF[y] = Fv[y] - K3; // Calcul du surplus
            //System.err.print(" D1 "+sF[y]);
            sFv_meth[y] = Math.min(sF[y], K2 - Mfinal[y]); // Calcul du surplus qui part vers le méthaniseur 
            //System.err.print(" D2 "+sFv_meth[y]+" "+Mfinal[y]);
            Mfinal[y] = Mfinal[y] + sFv_meth[y];
            //System.err.print(" D3 "+Mfinal[y]);
            sFv_inci[y] = Math.max(0.0, (sF[y] - sFv_meth[y])); // Calcul du surplus qui part vers l’incinérateur
            //System.err.print(" D4 "+sFv_inci[y]);
            Fv[y] = Math.max((Fv[y] - sFv_inci[y] - sFv_meth[y]), 0.0); // Quantité de biodéchets verts après l’application du surplus
            //System.err.println(" D5 "+Fv[y]);
        }
    }

    public void computeIncinerator(int y) {
        //I[y] = O[y] + sMa[y] + sFv_inci[y]; // // Y A PAS DE SMA CAR LE SURPLUS DE MA VA DANS LA PLATEFORME DE COMPOSTAGE PRO 
        I[y] = O[y] + sFv_inci[y]; // Quantité de biodéchets verts qui part vers l’incinérateur (prend OMR et surplus venant de plateforme compost pro)
    }

    public double sigmoide(double x, double ti) {
        double t = Math.pow(x, 5);
        double z = t / (t + Math.pow(ti, 5)); // ti est le point d'inflexion de la sigmoide (la valeur 0.5 est renvoyée la tième année)
        return z;
    }

    public void init(int sizeData, double[] params) {
        tiAG = params[0]; // point d'inflexion de la courbe de la sigmoide 
        tiLCL = params[1]; // point d'inflexion de la courbe de la sigmoide 
        tiLC = params[2]; // point d'inflexion de la courbe de la sigmoide 
        tiPCL = params[3]; // point d'inflexion de la courbe de la sigmoide 
        tiPT = params[4]; // point d'inflexion de la courbe de la sigmoide 
        b = params[5]; // Quantité de biodéchet produit par habitant
        qv = params[6]; // part des déchets verts dans b
        qa = params[7]; // part des déchets verts dans b
        alpha1_base = params[8];
        g = params[9]; // PAS TRES CLAIR CE QUE C'EST 
        objGaspi = params[10]; // objectif réduction gaspillage
        //objGreen = params[X];
        theta1 = params[11]; // incitation au tri allant de 70% jusqu’à95%
        theta2 = params[12]; // incitation au tri allant de 70% jusqu’à95%
        seuil = params[13];
        KA = params[15]; // capacité poubelle verte 
        accroissementAnnuel = params[16]; // accroissement annuel pop selon statistiques nationales
        alpha3t0 = params[17];
        this.K1 = params[18];
        this.K2 = params[19];
        this.K3 = params[20];

        P = new double[sizeData]; //sizeData = nbyears of simulation + 1 (for the initial state)
        Arrays.fill(P, 0.0);
        P[0] = (int) params[21];
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
        alpha1 = new double[sizeData];
        Arrays.fill(alpha1, 0.0);
        alpha2 = new double[sizeData];
        Arrays.fill(alpha2, 0.0);
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
        I = new double[sizeData];
        Arrays.fill(I, 0.0);
        Ptri = new double[sizeData];
        Arrays.fill(Ptri, 0.0);
        Ma = new double[sizeData];
        Arrays.fill(Ma, 0.0);
        Mv = new double[sizeData];
        Arrays.fill(Mv, 0.0);
        Mfinal = new double[sizeData];
        Arrays.fill(Mfinal, 0.0);
        sM = new double[sizeData];
        Arrays.fill(sM, 0.0);
        sMbis = new double[sizeData];
        Arrays.fill(sMbis, 0.0);
        Ma_bis = new double[sizeData];
        Arrays.fill(Ma_bis, 0.0);
        Mv_bis = new double[sizeData];
        Arrays.fill(Mv_bis, 0.0);
        sM = new double[sizeData];
        Arrays.fill(sM, 0.0);
        sMbis = new double[sizeData];
        Arrays.fill(sMbis, 0.0);
        sMa = new double[sizeData];
        Arrays.fill(sMa, 0.0);
        sMv = new double[sizeData];
        Arrays.fill(sMv, 0.0);
        sF = new double[sizeData];
        Arrays.fill(sF, 0.0);
        sFv_meth = new double[sizeData];
        Arrays.fill(sFv_meth, 0.0);
        sFv_inci = new double[sizeData];
        Arrays.fill(sFv_inci, 0.0);
        //Fv_bis = new double[sizeData];
        //Arrays.fill(Fv_bis, 0.0);
        Fv = new double[sizeData];
        Arrays.fill(Fv, 0.0);
        sFv = new double[sizeData];
        Arrays.fill(sFv, 0.0);
        sigmoideAntiGaspi = new double[sizeData];
        Arrays.fill(sigmoideAntiGaspi, 0.0);
        sigmoideLogCompostLocal = new double[sizeData];
        Arrays.fill(sigmoideLogCompostLocal, 0.0);
        sigmoidePraticCompostLocal = new double[sizeData];
        Arrays.fill(sigmoidePraticCompostLocal, 0.0);
        sigmoideLogCollecte = new double[sizeData];
        Arrays.fill(sigmoideLogCollecte, 0.0);
        sigmoidePraticTri = new double[sizeData];
        Arrays.fill(sigmoidePraticTri, 0.0);
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
        checkFluxStage1 = new boolean[sizeData];
        Arrays.fill(checkFluxStage1, true);
        checkFluxStage2 = new boolean[sizeData];
        Arrays.fill(checkFluxStage2, true);
    }

    public void printVector(double[] edit) {
        for (int i = 0; i < edit.length; i++) {
            System.err.print(edit[i] + "\t");
        }
        System.err.println();
    }

    public void checkConservationFlux(int y) {
        double time1 = Lfinal[y] + O[y] + Afinal[y] + Dv[y];
        double time2 = Mfinal[y] + I[y] + Lfinal[y] + Fv[y];
        double t1 = time1 - B[y];
        double t2 = time2 - B[y];
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
