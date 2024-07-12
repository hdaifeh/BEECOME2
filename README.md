# BEECOME2
Our model is a Java-based project designed to model and analyze the dynamics of biowaste. This project aims to understand how human behavior and infrastructure interact to achieve biowaste policy objectives. It includes multiple Java classes that represent different components of biowaste management:


![image](https://github.com/hdaifeh/BEECOME2/assets/73614180/6f9c8ef7-5174-4cb6-ba10-e71b04776e57)

- `Myterritory2 myTerre`: Represents the territory to which the collection territory belongs.

Simulation Parameters:
- `int timeBeforeInitAlpha1BaseDa`, `int timeBeforeInitAlpha1BaseDv`, `int timeBeforeInitAlpha2BaseDa`, `int timeBeforeInitAlpha2BaseDv`: Represent the time before the initialization of different base values for alpha1 and alpha2 for food waste (Da) and green waste (Dv).
- `double K1init`, `double KAinit`: Initial capacities for local composting and collection, respectively.
- `double[] K1courant`, `double[] KAcourant`: Current maximum capacities for local composting and collection throughout implementation.
- `double K1cible`, `double KAcible`: Target maximum capacities for local composting and collection.
- `int yearRef`: Reference year for individual composting capacity.


Sigmoid function (innovation diffusion to model social behaviour)

- `double[] sigmoidePraticCompostLocalDa`, `double[] sigmoidePraticCompostLocalDv`: Store the evolution of local composting practices for food waste (Da) and green waste (Dv) based on time.
- `double[] sigmoidePraticTriDa`, `double[] sigmoidePraticTriDv`: Store the evolution of sorting practices for food waste (Da) and green waste (Dv) based on time.
- `double[] sigmoideEvitGreenWaste`: Stores the evolution of the green waste avoidance (reduction) innovation diffusion to be applied to the green waste reduction rate.


Intention rates

- `double alpha1BaseDa`, `double alpha1BaseDv`: Base values for the proportion of food waste (Da) and green waste (Dv) going to local composting at the start of the simulation.
- `double alpha1ObjectifDa`, `double alpha1ObjectifDv`: Maximum increase in the proportion of food waste (Da) and green waste (Dv) going to local composting during the simulation.
- `double alpha2baseDa`, `double alpha2objectifDa`, `double alpha2baseDv`, `double alpha2objectifDv`: Base and target values for the sorting rates of food waste (Da) and green waste (Dv) for door-to-door collection.
- `double alpha3t0`: Initial proportion of the flow going to the waste collection centre.


Demographic parameters
:
- `double accroissementAnnuel`: Annual population growth rate.
- `int sizePop`: Population size of the collection territory.
- `double BaInit`, `double BvInit`: Initial quantities of food waste (Ba) and green waste (Bv) produced per inhabitant per year in tons.

Time Duration:
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

2. Myterritory2 class:
![image](https://github.com/hdaifeh/BEECOME2/assets/73614180/ad0606e9-0aa9-412a-9e22-7f38b2d56b9d)


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


![image](https://github.com/hdaifeh/BEECOME2/assets/73614180/3d5c4580-c851-4ad4-a315-2f8250d5b2f5)


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

The Dynamics2 class simulates the waste management dynamics over time, including waste production, distribution of waste flows to different treatment options and surplus management. It takes into account various parameters such as capacities, waste reduction objectives, and sigmoid functions to model the evolution of practices and logistics.

Key methods in the Dynamics2 class:
- `iterate(int year)`: Performs the simulation for a given year, including waste production, flow distribution, and treatment.
- `computeProducedBioWaste(int y)`: Computes the quantity of biowaste produced by the population in a given year.
- `computeFluxRates(int y)`: Computes the flow rates and proportions of waste going to different treatment options in a given year.
- `localCompost(int y)`, `collect(int y)`, `dechetterie(int y)`, `ordureMenagereResiduelle(int y)`: Methods for distributing waste flows to local composting, door-to-door collection, waste collection centres, and residual household waste.
- `computeMethanisation(int y)`, `computeCompostPlatform(int y)`, `computeIncinerator(int y)`: Methods for treating waste in the methanizer, professional composting platform, and incinerator.
- `sigmoide(double x, double ti)`: Calculates the sigmoid function value for a given input and inflection point.
- `init(int sizeData, double[] params)`: Initializes the simulation parameters and arrays.
- `checkConservationFlux(int y)`: Checks the conservation of fluxes at different simulation stages in a given year.

The Dynamics2 class explains waste management dynamics, considering various treatment options, capacities, and the evolution of practices and logistics over time.


![image](https://github.com/hdaifeh/BEECOME2/assets/73614180/53c2fcf2-cc2d-404b-a6af-bc4f82f9b769)


Capacities:
- `double K2`: Maximum capacity of the methanizer.
- `double K3`: Maximum capacity of the professional composting platform.
- `double KInc`: Maximum capacity of the incinerator.

Waste Quantities and Flow Variables:
- `double[] I`: Quantity of waste going to the incinerator.
- `double[] Mv`, `double[] Ma`, `double[] Mfinal`: Quantities of green waste and food waste going to the methanizer, with adjustments for surplus management.
- `double[] sM`, `double[] sMbis`, `double[] Ma_bis`, `double[] Mv_bis`, `double[] sMa`, `double[] sMv`: Variables related to surplus management in the methanizer.
- `double[] sF`, `double[] sFv_meth`, `double[] sFv_inci`, `double[] Fv`, `double[] sFv`: Variables related to the professional composting platform and surplus management.

The EquipmentValorisation2 class represents the common equipment for the valorization of biowaste, including a methanizer, a professional composting platform, and an incinerator. It manages the treatment of biowaste flows coming from one or more territories.

Key methods in the EquipmentValorisation2 class:
- `init(int sizeData, int KMethaniseur, int KIncinerator, int KnbCompostPro)`: Initializes the equipment capacities and arrays.
- `iterate(int year, double fluxAv, double fluxAa, double fluxDv, double fluxOMR)`: Performs the waste treatment for a given year, considering the incoming waste flows from the territories.
- `computeMethanisation(int y, double fluxAv, double fluxAa)`: Processes the waste in the methanizer, managing any surplus.
- `computeCompostPlatform(int y, double fluxDv)`: Processes the waste in the professional composting platform, managing any surplus and redistributing it to the methanizer or incinerator if necessary.
- `computeIncinerator(int y, double fluxOMR)`: Processes the waste in the incinerator, including residual household waste and any surplus from the professional composting platform.
- `printTrajectory(int year)`: Prints the trajectory of the waste flows and quantities for a given year.

The EquipmentValorisation2 class ensures the efficient treatment of biowaste flows, prioritizing the use of the methanizer, followed by the professional composting platform, and finally the incinerator. It manages surplus waste by redistributing it to the appropriate treatment options based on available capacities.

The class assumes that there is one set of common equipment (methanizer, professional composting platform, incinerator) shared by one or more territories. It also assumes that there is at most one professional composting platform per territory.

The waste treatment follows a specific order:
1. Methanization: The incoming waste flows from door-to-door collection (fluxAv for green waste and fluxAa for food waste) are first processed in the methanizer. If there is a surplus exceeding the methanizer's capacity, the green waste is redirected to the professional composting platform, and any remaining surplus of food waste is sent to the incinerator.
2. Professional Composting: The incoming waste flow from the waste collection center (fluxDv) and any surplus green waste from the methanizer (sMv) are processed in the professional composting platform. If there is a surplus exceeding the platform's capacity, it is redistributed to the methanizer if there is available capacity, and any remaining surplus is sent to the incinerator.
3. Incineration: The residual household waste (fluxOMR) and any surplus from the professional composting platform that couldn't be processed in the methanizer (sFv_inci) are sent to the incinerator.

The EquipmentValorisation2 class provides detailed management of biowaste treatment, optimizing the use of available capacities and ensuring the efficient valorization of waste flows from the territories.


![image](https://github.com/hdaifeh/BEECOME2/assets/73614180/8799329d-26e0-4543-9c0f-7ae5eea0499e)

Simulation Settings:
- `boolean printTrajectory`: Determines whether to print the detailed trajectory of the simulation.
- `PrintStream console`, `PrintStream ps`, `FileOutputStream fic`: Variables for writing the simulation results to a file.
- `String nomFichier`: Name of the file to write the simulation results.
- `double[] paramsTerritory`: Array to store the global parameters for the territory.
- `int nameExpe`: Identifier of the experiment.

Simulation Parameters:
- `int nbYearsSimu`: Number of years for the simulation.
- `int nbSubterritories`: Number of sub-territories in the territory.
- `double[][] paramsSubTerritories`: 2D array to store the parameters for each sub-territory.

Territory and Dynamics:
- `Myterritory2 myDyn`: Object representing the territory and its dynamics.

Methods:
- `MyModel2(String param, int ligne, int fs, boolean entete)`, `MyModel2(String param, String nomFichR, int ligne, int fs, boolean entete)`: Constructors for initializing the model with different parameters.
- `lectureEntree(String fileName, int ligne, int frequenceSauvegarde, boolean entete)`: Reads the input parameters from a file.
- `ecritureResultats(String nameResult)`: Writes the simulation results to a file.
- `ecritureResultatsComputed(int nbYears)`: Writes the computed simulation results to a file.
- `ecritureTrajectoire(int nbYears)`: Writes the trajectory of the simulation to a file.
- `indicatorsObjectives(int nbYears)`: Calculates indicators and objectives for the simulation.

Indicators and Objectives:
- `double[] valorizationRate`: Array to store the valorization rates.
- `int[] goodValorRate`: Array to store the status of good valorization rates.
- `double[] nbSolutionsForAllForGreen`, `double[] nbSolutionsForAllForFood`: Arrays to store the number of solutions for green waste and food waste.
- `int[] solutionForAllForGreen`, `int[] solutionForAllForFood`: Arrays to store the status of solutions for green waste and food waste.
- `double[] tauxEvolutionGreenWaste`, `double[] increaseOfMethanisedFoodWaste`: Arrays to store the evolution rates of green waste and the increase of methanized food waste.
- `int[] diminutionGreenWaste`, `int[] correctIncreaseOfMethanisedFoodWaste`: Arrays to store the status of green waste reduction and correct increase of methanized food waste.
- `int[] volMethanised`: Array to store the status of methanized volumes.
- `double[] multiplicateurVolumeBiodechetOMR`: Array to store the multiplier for biowaste volume in residual household waste.
- `int[] diminutionVolBiodechetOMR`: Array to store the status of biowaste volume reduction in residual household waste.
- `double[] kgDechetAlimDansOMRByHab`: Array to store the kilograms of food waste per inhabitant in residual household waste.
- `double[] totalIntentionForFoodWaste`, `double[] totalIntentionForGreenWasteWithoutDechetterie`: Arrays to store the total intentions for food waste and green waste (excluding waste collection centers).
- `int[] correct`, `int[] sommeRespectedObjectifs`: Arrays to store the status of correct objectives and the sum of respected objectives.

The MyModel2 class is responsible for initializing and running the simulation model. It reads the input parameters from a file, initializes the territory and sub-territories based on the parameters, and runs the simulation for the specified number of years.

During the simulation, it calculates various indicators and objectives related to waste management, such as valorization rates, solutions for waste treatment, evolution rates of waste, and achievement of specific objectives.

The class provides methods for writing the simulation results and trajectories to files, allowing for analysis and visualization of the simulation outcomes.

The indicators and objectives calculated in the `indicatorsObjectives` method assess the performance of the waste management system in terms of valorization rates, solutions for waste treatment, reduction of waste volumes, and achievement of specific targets set by the French government.

Overall, the MyModel2 class serves as the main entry point for the simulation model, handling the initialization, execution, and output of the simulation results based on the provided parameters and objectives.


![image](https://github.com/hdaifeh/BEECOME2/assets/73614180/efb25291-905b-4dc5-9d95-46cc3face103)

The MultiSimParam2 class is designed to run multiple simulations with different sets of parameters. It allows for batch processing of simulations by reading parameter sets from a file and executing the MyModel2 class for each set of parameters.


Instance Variables:
- `Vector jeuxParam`: A vector to store the parameter sets.
- `Vector dossierResult`: A vector to store the result folders.

Constructors:
- `MultiSimParam2(String fileName, int nLDebut, int nLFin, int fs)`: Constructs a MultiSimParam2 object with the specified file name, starting line number, ending line number, and frequency of saving results. It reads the parameter sets from the specified file and executes the MyModel2 class for each set of parameters.
- `MultiSimParam2(String fileName, String nomFichierSortie, int nLDebut, int nLFin, int fs)`: Similar to the previous constructor but with an additional parameter to specify the output file name.
- `MultiSimParam2(String fileName, int nLDebut, int fs)`: Constructs a MultiSimParam2 object with the specified file name, starting line number, and frequency of saving results. It determines the ending line number by counting the total number of lines in the file.

Static Methods:
- `nombreLignes(String fileName)`: Counts the number of lines in the specified file.
- `marqueur(String s)`: Checks if a string starts with a hyphen ("-"), indicating a command-line option.
- `messageParam()`: Displays a reminder message about the command-line parameters.
- `main(String[] args)`: The main method of the class, which parses the command-line arguments and creates a MultiSimParam2 object based on the provided parameters. It handles the following command-line options:
  - `-o`: Specifies the output file name.
  - `-f`: Specifies the frequency of saving results.

The main workflow of the MultiSimParam2 class is as follows:
1. The class reads the parameter sets from a specified file.
2. It creates a MyModel2 object for each set of parameters and executes the simulation.
3. The simulation results can be saved to an output file at a specified frequency.
4. The class keeps track of the progress by printing a message after each simulation run.

The command-line arguments for running the MultiSimParam2 class are as follows:
- The name of the parameter file.
- (Optional) The starting line number and ending line number of the parameter sets in the file.
- (Optional) `-o` followed by the output file name.
- (Optional) `-f` followed by the frequency of saving results.

If the required parameters are not provided or there is a problem with the command-line arguments, the `messageParam()` method is called to display a reminder message about the expected parameters.

Overall, the MultiSimParam2 class provides a convenient way to run multiple simulations with different parameter sets by automating the process of reading parameters from a file and executing the MyModel2 class for each set of parameters. It simplifies the task of running batch simulations and allows for easy customization of the output and saving frequency.
