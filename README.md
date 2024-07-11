# BEECOME2

Collection Territory Class:
- `Myterritory2 myTerre`: Represents the territory to which the collection territory belongs.

Simulation Parameters:
- `int timeBeforeInitAlpha1BaseDa`, `int timeBeforeInitAlpha1BaseDv`, `int timeBeforeInitAlpha2BaseDa`, `int timeBeforeInitAlpha2BaseDv`: Represent the time before the initialization of different base values for alpha1 and alpha2 for food waste (Da) and green waste (Dv).
- `double K1init`, `double KAinit`: Initial capacities for local composting and collection, respectively.
- `double[] K1courant`, `double[] KAcourant`: Current maximum capacities for local composting and collection over the course of implementation.
- `double K1cible`, `double KAcible`: Target maximum capacities for local composting and collection.
- `int yearRef`: Reference year for individual composting capacity.

Sigmoid Functions:
- `double[] sigmoideLogCompostLocal`: Stores the evolution of local composting logistics based on time.
- `double[] sigmoidePraticCompostLocalDa`, `double[] sigmoidePraticCompostLocalDv`: Store the evolution of local composting practices for food waste (Da) and green waste (Dv) based on time.
- `double[] sigmoideLogCollecte`: Stores the evolution of collection logistics based on time.
- `double[] sigmoidePraticTriDa`, `double[] sigmoidePraticTriDv`: Store the evolution of sorting practices for food waste (Da) and green waste (Dv) based on time.
- `double[] sigmoideEvitGreenWaste`: Stores the evolution of the green waste avoidance coefficient to be applied to the green waste reduction rate.

Waste Flow Rates:
- `double alpha1BaseDa`, `double alpha1BaseDv`: Base values for the proportion of food waste (Da) and green waste (Dv) going to local composting at the start of the simulation.
- `double alpha1ObjectifDa`, `double alpha1ObjectifDv`: Maximum increase in the proportion of food waste (Da) and green waste (Dv) going to local composting during the simulation.
- `double alpha2baseDa`, `double alpha2objectifDa`, `double alpha2baseDv`, `double alpha2objectifDv`: Base and target values for the sorting rates of food waste (Da) and green waste (Dv) for door-to-door collection.
- `double alpha3t0`: Initial proportion of the flow going to the waste collection center.

Population and Waste Production:
- `double accroissementAnnuel`: Annual population growth rate.
- `int sizePop`: Population size of the collection territory.
- `double BaInit`, `double BvInit`: Initial quantities of food waste (Ba) and green waste (Bv) produced per inhabitant per year in tons.

Time Durations:
- `double duraImplemCompo`: Inflection point of the sigmoid curve for local composting implementation.
- `double tiPCL`: Inflection point of the sigmoid curve for local composting practice adoption.
- `double duraImplemCollect`: Inflection point of the sigmoid curve for collection implementation.
- `double tiPT`: Inflection point of the sigmoid curve for sorting practice adoption.
- `double tiGreenWaste`: Inflection point of the sigmoid curve for green waste avoidance.

Waste Reduction and Anti-Waste Objectives:
- `double tauxEviteGreenWate`: Reduction rate of green waste production through avoidance.
- `double objectifAntiGaspillage`: Target proportion of the population to be sensitized to food waste reduction.

Waste Quantities and Flow Variables:
- `double[] P`: Population size at each time step.
- `double[] B`, `double[] Bv`, `double[] Ba`: Quantities of total biowaste, green waste, and food waste produced by the population.
- `double[] Rtot`, `double[] R`, `double[] G`: Variables related to food waste reduction.
- `double[] alpha1Da`, `double[] alpha1Dv`, `double[] alpha3`, `double[] alpha2Da`, `double[] alpha2Dv`: Proportions of food waste and green waste going to local composting, waste collection center, and door-to-door collection.
- `double[] C_log`, `double[] C_pop`: Variables related to the evolution of local composting logistics and practices.
- `double[] Lv`, `double[] La`, `double[] La_bis`, `double[] Lv_bis`, `double[] Lfinal`: Quantities of green waste and food waste going to local composting, with adjustments for surplus management.
- `double[] sL`, `double[] sLv`, `double[] sLa`, `double[] sLbis`: Variables related to surplus management in local composting.
- `double[] Dv`: Quantity of green waste going to the waste collection center.
- `double[] Av`, `double[] Aa`, `double[] Afinal`, `double[] Aa_bis`, `double[] Av_bis`: Quantities of green waste and food waste going to door-to-door collection, with adjustments for surplus management.
- `double[] sAa`, `double[] sAv`, `double[] sAa_bis`, `double[] sAv_bis`, `double[] sA`, `double[] sAbis`: Variables related to surplus management in door-to-door collection.
- `double[] O`: Quantity of food waste going to residual household waste.

Additional Variables:
- `int subTerritoryName`: Identifier of the collection territory.
- `double[] propPopDesserviCollDA`: Proportion of the population served by food waste collection each year.
- `double[] nbKgCollectHabDesservi`: Number of kilograms of food waste collected per inhabitant served by the collection each year.
- `double[] nbKgOMRHab`: Number of kilograms of food waste collected per inhabitant in residual household waste each year.
- `double[] tauxReductionDechetVert`: Rate of reduction of green waste entering the waste collection center.
- `int ident`: Identifier of the collection territory.

These variables and parameters are used to model and simulate the waste management processes in the collection territories, including local composting, door-to-door collection, waste collection centers, and residual household waste. The class includes methods to calculate waste production, distribute waste flows, manage surplus, and track various indicators related to waste management over time.

Myterritory2 class:

Territory Class:
- `boolean printTrajectory`: Determines whether to print the trajectory of the simulation.
- `boolean useSocialDynamics`: Allows to cancel the evolution of the behavioral intention (i.e., the alpha values) when set to false.

Waste Reduction and Anti-Waste Parameters:
- `double einit`: Initial edible part of the food waste production.
- `double theta1`, `double theta2`: Parameters for the sigmoid function modeling the social dynamics of sorting incentives.
- `double tiAG`: Inflection point of the sigmoid curve for anti-waste practice adoption.

Total Waste Quantities and Flow Variables:
- `double[] Ptot`: Total population size at each time step.
- `double[] Btot`, `double[] Batot`, `double[] Bvtot`: Total quantities of biowaste, food waste, and green waste produced by the population.
- `double[] RGlobtot`: Total quantity of waste reduction due to anti-waste efforts.
- `double[] Lfinaltot`: Total quantity of biowaste in local composting after surplus management.
- `double[] Afinaltot`: Total quantity of biowaste in door-to-door collection after surplus management.
- `double[] Otot`: Total quantity of food waste going to residual household waste.
- `double[] Dvtot`: Total quantity of green waste going to waste collection centers.
- `double[] sLatot`, `double[] sLvtot`: Total compostable food and green waste surpluses.
- `double[] sAvtot`, `double[] sAatot`: Total compostable green and food waste surpluses from door-to-door collection.

Reference Year and Initial Values:
- `int refYear`: Reference year between 0 and the number of simulation years for evaluating objectives such as increase or decrease.
- `double firstYearDechetterieDechetVertTot`: Total quantity of green waste in waste collection centers in the first year.
- `double firstYearVolumeDechetOMRTot`: Total volume of residual household waste in the first year.

Anti-Waste Sigmoid Function:
- `double[] sigmoideAntiGaspi`: Stores the evolution of anti-waste practices based on time.

Flux Conservation Checks:
- `boolean[] checkFluxStage1`, `boolean[] checkFluxStage2`: Arrays to check the conservation of fluxes at different stages of the simulation.

Territory Composition:
- `int territoryName`: Identifier of the territory.
- `int nbSubterritories`: Number of collection territories within the territory.
- `int nbEquipments`: Number of valorization equipment units.
- `Subterritory2[] myTerrit`: Array of collection territories belonging to the territory.
- `EquipmentValorisation2 myCommonEquip`: Common valorization equipment shared by the collection territories.

Objectives:
- `double increasedObjectiveOfMethanisedFoodWaste`: Increased objective for methanized food waste.

The Myterritory2 class acts as a container for the collection territories (Subterritory2 objects) and the common valorization equipment (EquipmentValorisation2 object). It initializes and manages the simulation for the entire territory.

Key methods in the Myterritory2 class:
- `computeSubterritories(int year)`: Computes the waste production and distribution for each collection territory in a given year.
- `sigmoide(double x, double ti)`: Calculates the sigmoid function value for a given input and inflection point.
- `init(int sizeData, double[] params)`: Initializes the territory parameters and common valorization equipment.
- `computeTotalFluxSubTerritories(int y)`: Computes the total waste fluxes for all collection territories in a given year.
- `computeFluxesForCommonEquipment(int y)`: Computes the waste fluxes for the common valorization equipment in a given year.
- `checkConservationFlux(int y)`: Checks the conservation of fluxes at different stages of the simulation in a given year.

The Myterritory2 class manages the overall simulation by coordinating the collection territories and the common valorization equipment. It ensures the conservation of fluxes and tracks the total waste quantities and flows throughout the simulation.

Dynamics2 class:

Waste Production Parameters:
- `double b`: Quantity of biowaste produced per inhabitant (green and food waste).
- `double qv`: Share of green waste in biowaste.
- `double qa`: Share of food waste in biowaste.

Waste Flow Rates and Objectives:
- `double alpha1_base`: Base value for the proportion of biowaste going to local composting at the start of the simulation.
- `double alpha3t0`: Initial proportion of the flow going to the waste collection center.
- `double g`: Initial food waste percentage.
- `double objGaspi`: Objective for food waste reduction rate.
- `double theta1`, `double theta2`: Parameters for the sigmoid function modeling the social dynamics of sorting incentives.
- `double seuil`: Maximum increase in the proportion of biowaste going to local composting during the simulation.

Capacities:
- `double K1courant`, `double KAcourant`: Current maximum capacities for local composting and door-to-door collection over the course of implementation.
- `double K1`, `double KA`: Maximum capacities for local composting and door-to-door collection.
- `double K2`: Maximum capacity of the methanizer.
- `double K3`: Maximum capacity of the professional composting platform.

Population and Growth:
- `double accroissementAnnuel`: Annual population growth rate.

Time Durations:
- `double tiAG`: Inflection point of the sigmoid curve for anti-waste practice adoption.
- `double tiLCL`: Inflection point of the sigmoid curve for local composting logistics implementation.
- `double tiPCL`: Inflection point of the sigmoid curve for local composting practice adoption.
- `double tiLC`: Inflection point of the sigmoid curve for door-to-door collection logistics implementation.
- `double tiPT`: Inflection point of the sigmoid curve for sorting practice adoption.

Waste Quantities and Flow Variables:
- `double[] P`: Population size at each time step.
- `double[] B`, `double[] Bv`, `double[] Ba`: Quantities of total biowaste, green waste, and food waste produced by the population.
- `double[] Rtot`, `double[] R`, `double[] G`: Variables related to food waste reduction.
- `double[] alpha1`, `double[] alpha2`, `double[] alpha3`: Proportions of biowaste going to local composting, door-to-door collection or residual household waste, and waste collection center.
- `double[] C_log`, `double[] C_pop`: Variables related to the evolution of local composting logistics and practices.
- `double[] Lv`, `double[] La`, `double[] La_bis`, `double[] Lv_bis`, `double[] Lfinal`: Quantities of green waste and food waste going to local composting, with adjustments for surplus management.
- `double[] sL`, `double[] sLv`, `double[] sLa`, `double[] sLbis`: Variables related to surplus management in local composting.
- `double[] Dv`: Quantity of green waste going to the waste collection center.
- `double[] Av`, `double[] Aa`, `double[] Afinal`, `double[] Aa_bis`, `double[] Av_bis`: Quantities of green waste and food waste going to door-to-door collection, with adjustments for surplus management.
- `double[] sAa`, `double[] sAv`, `double[] sAa_bis`, `double[] sAv_bis`, `double[] sA`, `double[] sAbis`: Variables related to surplus management in door-to-door collection.
- `double[] O`: Quantity of food waste going to residual household waste.
- `double[] Ptri`: Probability of sorting at each time step.
- `double[] I`: Quantity of waste going to the incinerator.
- `double[] Mv`, `double[] Ma`, `double[] Mfinal`: Quantities of green waste and food waste going to the methanizer, with adjustments for surplus management.
- `double[] sM`, `double[] sMbis`, `double[] Ma_bis`, `double[] Mv_bis`, `double[] sMa`, `double[] sMv`: Variables related to surplus management in the methanizer.
- `double[] sF`, `double[] sFv_meth`, `double[] sFv_inci`, `double[] Fv`, `double[] sFv`: Variables related to the professional composting platform and surplus management.

Sigmoid Functions:
- `double[] sigmoideAntiGaspi`: Stores the evolution of anti-waste practices based on time.
- `double[] sigmoideLogCompostLocal`: Stores the evolution of local composting logistics based on time.
- `double[] sigmoidePraticCompostLocal`: Stores the evolution of local composting practices based on time.
- `double[] sigmoideLogCollecte`: Stores the evolution of door-to-door collection logistics based on time.
- `double[] sigmoidePraticTri`: Stores the evolution of sorting practices based on time.

Flux Conservation Checks:
- `boolean[] checkFluxStage1`, `boolean[] checkFluxStage2`: Arrays to check the conservation of fluxes at different stages of the simulation.

The Dynamics2 class simulates the waste management dynamics over time, including waste production, distribution of waste flows to different treatment options, and surplus management. It takes into account various parameters such as capacities, waste reduction objectives, and sigmoid functions to model the evolution of practices and logistics.

Key methods in the Dynamics2 class:
- `iterate(int year)`: Performs the simulation for a given year, including waste production, flow distribution, and treatment.
- `computeProducedBioWaste(int y)`: Computes the quantity of biowaste produced by the population in a given year.
- `computeFluxRates(int y)`: Computes the flow rates and proportions of waste going to different treatment options in a given year.
- `localCompost(int y)`, `collect(int y)`, `dechetterie(int y)`, `ordureMenagereResiduelle(int y)`: Methods for distributing waste flows to local composting, door-to-door collection, waste collection centers, and residual household waste.
- `computeMethanisation(int y)`, `computeCompostPlatform(int y)`, `computeIncinerator(int y)`: Methods for treating waste in the methanizer, professional composting platform, and incinerator.
- `sigmoide(double x, double ti)`: Calculates the sigmoid function value for a given input and inflection point.
- `init(int sizeData, double[] params)`: Initializes the simulation parameters and arrays.
- `checkConservationFlux(int y)`: Checks the conservation of fluxes at different stages of the simulation in a given year.

The Dynamics2 class explains waste management dynamics, considering various treatment options, capacities, and the evolution of practices and logistics over time.
