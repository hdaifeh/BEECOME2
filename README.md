# BEECOME2

Collection Territory Class:
- `Myterritory2 myTerre`: Represents the parent territory to which the collection territory belongs.

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
Dynamic class:
This class handles the dynamic aspects of the biowaste management system. Below is a detailed breakdown of its components and methods:
Variables
- `b`, `qv`, `qa`: Parameters for biowaste production per capita (food and green) and waste composition.
- `alpha1_base`, : Initial intention of composting.
- `g`, `objGaspi`: Parameters related to food waste reduction.
- `K1`, `KA`, `K2`, `K3`: Capacities for local composting, green waste collection, and professional composting facilities.
- `theta1`, `theta2`: Parameters for the sigmoid function modeling social dynamics.
- `seuil`, `accroissementAnnuel`: Population growth and threshold parameters for biowaste management.
-  `tiPCL`, `tiPT`: Inflection points for various social intention.
Arrays
Arrays to store values for population size, biowaste production, waste reduction, sorting probabilities, and waste flows over time.
Constructor
- `Dynamics2(double[] parameters)`: Initializes the class with given parameters and runs the simulation for the specified duration.
Methods
- `iterate(int year)`: Simulates the biowaste management dynamics for a given year.
- `computeProducedBioWaste(int y)`: Calculates the production of biowaste based on population size and waste reduction strategies.
- `computeFluxRates(int y)`: Computes the distribution rates of biowaste to different management pathways.
- `localCompost(int y)`: Models the local composting process and handles potential surplus.
- `collect(int y)`: Manages the collection of biowaste and handles surplus.
- `dechetterie(int y)`: Manages the flow of biowaste to waste facilities.
- `ordureMenagereResiduelle(int y)`: Manages the flow of residual waste.
- `computeMethanisation(int y)`: Models the methanisation process for biowaste.
- `computeCompostPlatform(int y)`: Manages the compost platform and handles surplus.
- `computeIncinerator(int y)`: Models the incineration process for biowaste.
- `sigmoide(double x, double ti)`: Sigmoid function used to model social dynamics.
- `init(int sizeData, double[] params)`: Initializes arrays and parameters for the simulation.
- `printVector(double[] edit)`: Utility method to print array values.
- `checkConservationFlux(int y)`: Ensures the conservation of mass in the biowaste flow calculations.
