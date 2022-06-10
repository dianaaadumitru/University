import pygame

from repository import *


class Controller:
    def __init__(self):
        self.repo = Repository()
        self.iterationCount = 0

    def getMap(self):
        return self.repo.cmap

    def updatePopulation(self, child1, child2, parent1, parent2, order):
        if child1 == order[0] or child1 == order[1]:
            self.repo.populations[-1].addToPopulation(child1)
        if child2 == order[0] or child2 == order[1]:
            self.repo.populations[-1].addToPopulation(child2)
        if parent1 == order[2] or parent1 == order[3]:
            self.repo.populations[-1].removeFromPopulation(parent1)
        if parent2 == order[2] or parent2 == order[3]:
            self.repo.populations[-1].removeFromPopulation(parent2)

    def iteration(self):
        # args - list of parameters needed to run one iteration

        # TODO a iteration: selection of the parents, create offsprings by crossover of the parents, apply some
        #  mutations, selection of the survivors
        auxiliary_array = []
        population_length = len(self.repo.populations[-1].getPopulation())
        self.repo.populations[-1].evaluate()
        parents = self.repo.populations[-1].selection(population_length)
        best_parent1, best_parent2 = parents[0], parents[1]  # selection of the first 2 parents
        worst_parent1, worst_parent2 = parents[- 1], parents[- 2]  # selection of the last 2 parents
        child1, child2 = best_parent1.crossover(best_parent2)  # create offsprings by crossover of the parents
        child1.mutate()  # apply mutations
        child2.mutate()
        child1.fitness(self.repo.populations[-1].startingPosition, self.repo.cmap)
        child2.fitness(self.repo.populations[-1].startingPosition, self.repo.cmap)
        auxiliary_array.append(child1)
        auxiliary_array.append(child2)
        auxiliary_array.append(worst_parent1)
        auxiliary_array.append(worst_parent2)
        auxiliary_array.sort(key=lambda e: e.getFitness(), reverse=True)
        self.updatePopulation(child1, child2, worst_parent1, worst_parent2,
                              auxiliary_array)  # select survivors, update the repo

    def stopCondition(self, maxIterations):
        if self.iterationCount > maxIterations:
            return True
        return False

    def run(self, maxIterations):
        # TODO until stop condition, perform an iteration, save the information needed for the statistics,
        #  return the results and the info for statistics
        result = []
        while not self.stopCondition(maxIterations):
            self.iteration()
            self.iterationCount += 1
            result.append(self.repo.populations[-1].averageFitness())
        return self.repo.populations[-1].selection(1)[0], result

    def solver(self, maxIterations, populationSize, individualSize, startingPos):
        # TODO create the population, run the algorithm, return the results and the statistics
        population = self.repo.createPopulation(startingPos, populationSize, individualSize)
        self.repo.populations.append(population)
        self.iterationCount = 0
        return self.run(maxIterations)

    def randomMap(self):
        self.repo.randomMap()

    def saveMap(self, file):
        self.repo.saveMap(file)

    def loadMap(self, file):
        self.repo.loadMap(file)
