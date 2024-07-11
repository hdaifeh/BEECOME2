
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author shuet This class represent the common equipments for the valorisation
 * of the biowaste
 */
public class EquipmentValorisation2 {

    double K2; // capacité maximale du méthaniseur
    double K3; // capacité maximale de la plateforme de compostage professionnel
    double KInc; // capacité maximale de l'incinérateur

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
    double[] Fv; // Quantité de biodéchets verts de la plateforme de compostage professionnelle
    double[] sFv; // surplus Quantité de biodéchets verts 

    public EquipmentValorisation2() {
        // un ensemble d'equipements (methaniseur, compost pro, incinerateur) est approvisionné par un (territoire global) ou UN sous-territoire
        // la priorité pour le traitement des surplus est toujours 1. methaniseur, 2 compost pro, 3 incinerateur)
    }

    public void init(int sizeData, int KMethaniseur, int KIncinerator, int KnbCompostPro) {

        K2 = KMethaniseur;
        K3 = KnbCompostPro;
        KInc = KIncinerator;

        I = new double[sizeData];
        Arrays.fill(I, 0.0);
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
        Fv = new double[sizeData];
        Arrays.fill(Fv, 0.0);
        //sFv = new double[sizeData];
        //Arrays.fill(sFv, 0.0);
    }

    public void iterate(int year, double fluxAv, double fluxAa, double fluxDv, double fluxOMR) { // TODO TRAITER L ARRIVEE PAR TERRITOIRE ET TRAITER UN OU DES EQUIPEMENT
        // Étape 2 : Traitement des biodéchets → HYPOTHÈSE
        computeMethanisation(year, fluxAv, fluxAa); // first car riorité 1
        computeCompostPlatform(year, fluxDv); // second car priorité 2
        computeIncinerator(year, fluxOMR);
    }

    public void computeIncinerator(int y, double fluxOMR) {
        //I[y] = O[y] + sMa[y] + sFv_inci[y]; // // Y A PAS DE SMA CAR LE SURPLUS DE MA VA DANS LA PLATEFORME DE COMPOSTAGE PRO 
        I[y] = fluxOMR + sFv_inci[y]; // Quantité de biodéchets verts qui part vers l’incinérateur (prend OMR et surplus venant de plateforme compost pro)
    }

    /**
     * Étape 2 : Traitement des biodéchets → HYPOTHÈSE
     */
    public void computeMethanisation(int y, double fluxAv, double fluxAa) {
        // si A supérieur à 0 sinon on ne fait rien (veut dire qu'il y a une collecte biodéchet
        // si A>0 alors si K2 supérieur à 0 sinon A sera traité au niveau MyTerritory (par équipement commun)
        if (K2 > 0) { // il y a un méthaniseur (ou des) local
            Mv[y] = fluxAv ; // Quantité de biodéchets verts qui part vers le méthaniseur
            Ma[y] = fluxAa ; // Quantité de biodéchets alimentaire qui part vers le méthaniseur
            Mfinal[y] = Mv[y] + Ma[y]; //Valeur de M[y] après avoir retiré le surplus s'il y en avait
            //System.err.println("MFINAL MFINAL 1 "+Mfinal[y]+" "+K2);
            // si M[y] > K2 : Nous avons un surplus, alors on va : Mettre en premier des biodéchets verts Mv[y] dans le compostage local puis si Mv[y] est vide et qu’il reste du surplus et que M[y] est toujours supérieur à K2 alors on met des biodéchets alimentaires Ma[y] dans l’incinérateur. → HYPOTHÈSE
            if ((Mv[y] + Ma[y]) > K2) {
                sM[y] = Ma[y] + Mv[y] - K2; // Premier calcul du surplus
                Mv_bis[y] = Math.max(Mv[y] - sM[y], 0.0); // Quantité de biodéchets verts après l’application du surplus
                sMbis[y] = Math.max(0.0, Ma[y] + Mv_bis[y] - K2); // Deuxième calcul du surplus pour voir si il nous reste du surplus après avoir retiré des biodéchets verts
                Ma_bis[y] = Math.max(Ma[y] - sMbis[y], 0.0); // Quantité de biodéchets alimentaire après l’application du second surplus
                //Mv_bis[y] = Math.max(Mv[y] - sM[y], 0.0); // Quantité de biodéchets verts après l’application du surplus
                
                sMv[y] = Math.min(sM[y], Mv[y]); //Quantité de biodéchets verts retirés à cause du surplus
                sMa[y] = Math.min(sMbis[y], Ma[y]); // Quantité de biodéchets alimentaires retirés à cause du surplus mis dans compostage plateforme
                Mfinal[y] = Mv_bis[y] + Ma_bis[y]; //Valeur de M[y] après avoir retiré le surplus s'il y en avait
                //System.err.println("MFINAL MFINAL 2 "+Mfinal[y]+" "+Mv_bis[y]+" "+Ma_bis[y]);
            }
        }
    }

    public void computeCompostPlatform(int y, double fluxDv) { // on suppose une plateforme de compostage ou de broyage par territoire
        Fv[y] = fluxDv + sMv[y] + sMa[y]; // Quantité de biodéchets verts qui part vers la collecte
        //→ HYPOTHÈSE : Pas de Fa[y] car on n’a pas de biodéchets alimentaire dans un compostage plateforme → HYPOTHÈSE
        //→ HYPOTHÈSE : si F[y] > K3 : Nous avons un surplus, alors on va : Mettre en premier des biodéchets verts Fv[y] dans le méthaniseur si il reste de la place puis si M[y] est plein et qu’il reste du surplus alors on met des biodéchets verts restant dans l’incinérateur.
        if (Fv[y] > K3) {
            sF[y] = Fv[y] - K3; // Calcul du surplus
            sFv_meth[y] = Math.min(sF[y], K2 - Mfinal[y]); // Calcul du surplus qui part vers le méthaniseur 
            Mfinal[y] = Mfinal[y] + sFv_meth[y];
            sFv_inci[y] = Math.max(0.0, (sF[y] - sFv_meth[y])); // Calcul du surplus qui part vers l’incinérateur
            Fv[y] = Math.max((Fv[y] - sFv_inci[y] - sFv_meth[y]), 0.0); // Quantité de biodéchets verts après l’application du surplus
        }//By systematically following these steps, the model ensures that any surplus green biowaste is either processed in the methanization unit for energy recovery or managed through incineration, with the aim of maintaining operational efficiency and capacity balance within the professional composting platform.
    }
    
    public void printTrajectory(int year) {
        System.out.print((year+2017)+";");
        System.out.print(Fv[year]+";");
        System.out.print(Ma[year]+";");
        System.out.print(Ma_bis[year]+";");
        System.out.print(Mv[year]+";");
        System.out.print(Mv_bis[year]+";");
        System.out.print(I[year]+";");
        System.out.println();
    }

}
