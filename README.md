# Battle Of Sexes: implementing an evolutionary system on Java

Vincenzo Mensitieri, Emanuele Scaccia, Stefano Zappa, Giordano Tambara

Sapienza Università di Roma

## Abstract:

## Introduction:

## Adopted Models:

### Setting:

The setting of the project has been chosen to be built around the concept of a Government that manages all the processes.

This Government is supported by institutions that supervise the phases the individuals have to pass through.

There is the Counselor: it is meant to oversee the matching procedure, aiming to assign the most suitable option for everyone.

When the Counselor has finished to match every individual, he passes to the next generation, which is composed only by the children of the previous one.

### Matching:

In the model adopted in this project, courtship was decided to be unilateral: men decide if they want to propose to a woman, and women decide whether to accept or not the proposal.

It is important to highlight that matching is determined by both sexes’ preference.

In fact, our model is inspired by the “Stable Matching Problem” and, consequently, shares core features with the Gale-Shapley Algorithm: a parametrized number of men (default number was chosen to be 2) of random type interacts with the same number of randomly picked women.

Every man expresses a predilection towards its possible partner: with most of parameters' triplets, there will be a union that is better for him than the other one based on the payoff he will get for being with that type of woman. The bigger payoff, the better.

The same exact modality happens for the other sex; nevertheless, the choice is limited by the random pick of the individuals involved; in fact, during a single match, we can end up with every person of the same sex being of the same type. In that case, there is just one "obliged" option to select.

The main difference with the original Gale-Shapley Algorithm is that, during a cycle, we don't want to necessarily pair every "participant" immediately. Instead, we couple only if the two individuals taken in consideration are the top preference for each other; If not coupled, the person will be sent again to the "Counselor" and re-picked for another cycle.

### Exceptions:

Obviously, coherently to what was precedently stated, Philanderer men and Coy women cannot interbreed in any case; this is took in consideration in the code, in fact they will never form a couple.

Furthermore, in theory the Philanderer men shouldn't form a couple of any kind; according to Dawkins' book, they should leave their partner after copulating.                                    Nevertheless, given that they can pair only with Fast women, for the sake of the project, there isn't any practical difference in mating with different women or being in a monogamous relationship.

Everyone is given the same opportunity to have a defined number of children, so, doing it with the same Fast woman or with different ones will not change the result.

### Children:

On the other hand, in case a couple is formed, it will procreate and pass their children to the next generation.

The number of children varies from 1 to 4, with every option having a parametrizable percentage to happen (Default values have been chosen to be 10% probability to have just one child, 60% for two, 20% for 3 and 10% for four)

These values have been chosen in order to have a slight increase in the population when we pass to the next generation (the average is 2.3 children).

## Software structure:

In the main package we have 4 subpackages and 2 classes.

Let’s begin talking about the subpackages and the most essential components of our code.

### Utils:

#### ProbabilityWheel:

This is a utility class, used to pick random items with determined probabilities.

It takes a generic parameter "T", which represent the type of object that will be picked.

We use one map to store the values of the probabilities, in percentage, regarding the items, called "originals" and one LinkedHashMap to store the decimal probability value.

It has 3 methods: the first and most important one is spin(); this method "spins the wheel" returning a random object based on the given probabilities.

Then we have the methods getProbabilities() that returns originals, and getRanges that returns ranges.

### Beans:

This package contains components used to collect data.

#### PayoffTable:

This class is used to gather the data from the Payoff Table, namely to compute the payoffs of every type of individual in every single situation.

We initially define 3 floats parameters : a, b, c based on which the payoffs will be computed with different equations depending on the pair of people we are taking in consideration.

Thus, 8 methods have been implemented to return every single value we need (because we have 4 possible pair, everyone with two payoff values, one for the man and one for the woman)

Plus, there is the method get() that takes in input the genes taken in consideration and returns the payoff.

#### RandomPicker:

This class contains the methods that are needed to randomize different parts of the simulation; for this purpose we use ProbabilityWheel.

We set sons, maleGenes and femaleGenes as instances of ProbabilityWheel; we create 3 methods (setSons, setMaleGenes, setFemaleGenes) that set the data based on the given probabilties.

The other 3 methods Sons(), maleGenes(), femaleGenes() return a random value of the regarding type based on the given probabilities.

### Models:

This package contains the classes determining the individuals' attributes and their behaviour.

#### Genetype:

Genetype is a functional interface that only has one method : getCode().

Being an interface, it lacks the actual implementation which will be written when called in specific situations.

#### Human:

This is an abstract superclass whose constructor takes the GeneType type object as an argument.

From this class we will extend the classes Man and Woman.

#### Man:

This class extends Human, so it gets the variable type from its superclass; We defined the getType() method which returns the type associated to the Man object.

Here has been implemented the topPreferences method that, based on the payoff that Women would get by being with a specific type of man, ranks the possible partners and returns the best option for the woman taken in consideration.

Then we define the instance enum Type, which implements Genetype; here we define the two types of genes a man can have : F (Faithfull) and P (Philanderer).

#### Woman:

This class is basically the same thing as Man but it obviously contains the topPreferences method for the Men, which returns the best type of woman for the man taken in consideration based on the value of the payoffs, and it defines the genes regarding women : C (Coy) and S (Fast).

#### Couple:

In this class we define a Man and a Woman from their classes and a set of Humans, which are the children.

A couple takes as argument the Man and the Woman and contains an initially empty set of children; with the method procreate, we generate the offspring from the partners taken as argument: the number is random and defined by the randomPick class about which we talked before, and the sex has a 50/50 chance.

Sons will inherit the father's type, Daughters the mother's one.

### Matching:

#### Generation:

Generation is a synchronous object that takes as an argument the list of Man and Women and puts them in two separated queues.

The purpose of this object is two generate, through the method compute(), the next generation of children, based, obviously, on the current generation and its charateristics.

The method compute() uses the "Counselors", objects that we will explain later in detail, which extend threads and manage the "procreation".

Other methods contained in this class are getTypeRatios, that returns the various percentages of the different types in the new generation, getTotalChildren that returns the total number of them and toString that returns the ratios result in an undrestandable format.

Lastly, we have addCouple, through which a couple formed during the matching is added to the generation and made procreate to add children to the next one.

#### Counselor:

Counselor, as we said, extends Threads, and it is run asynchronously.

When used in the compute() method of Generation, 4 of them are created and run simultaneously and stopped when they finish or after a timeout of 3 seconds.

When the Thread is executed, it calls the run() regarding Counselor, which basically is the process of matching precedently explained.

Until we have enough people, we run an algorithm similar to the Gale-Shapley for 3 random women and 3 random men; the only difference is that the only coupled formed are the one that match the partners' top preferences.

### BattleOfSexesApplication:

This is the first code to be run, it contains all the parametrizable properties and variable: spring, with @AutoWired, automatically executes them.

### Emulator:

Emulator is our "main" class: it starts from a random population (always based on given probabilities) and computes the next generations.

The method isStable() checks if the current and the previous populations has similar proportions among the various type : in that case, we say that we reached a state of evolutionary stability.

We return the proportion (in percentage) of every generation and the number of them that we needed to reach stability.

## Case Studies: