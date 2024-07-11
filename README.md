# BEECOME2
Biowaste
This class handles the dynamic aspects of the biowaste management system. Below is a detailed breakdown of its components and methods:
Variables
- `b`, `qv`, `qa`: Parameters for biowaste production per capita (food and green) and waste composition.
- `alpha1_base`, : Initial intention of composting.
- `g`, `objGaspi`: Parameters related to food waste reduction.
- `K1`, `KA`, `K2`, `K3`: Capacities for local composting, green waste collection, and professional composting facilities.
- `theta1`, `theta2`: Parameters for the sigmoid function modeling social dynamics.
- `seuil`, `accroissementAnnuel`: Population growth and threshold parameters for biowaste management.
- `tiAG`, `tiLCL`, `tiPCL`, `tiLC`, `tiPT`: Inflection points for various logistic curves.
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
