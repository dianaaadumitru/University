# -*- coding: utf-8 -*-
import pickle
from random import *
from utils import *
import numpy as np

# define directions
UP = 0
DOWN = 2
LEFT = 1
RIGHT = 3


# the glass gene can be replaced with int or float, or other types
# depending on your problem's representation

class Gene:
    def __init__(self):
        # TODO random initialise the gene according to the representation
        self.move = randint(0, 3)  # integer representation (0,1,2,3-moves N,S,E,V)


class Individual:
    def __init__(self, size=0):
        self.__size = size
        self.__x = [Gene() for i in range(self.__size)]  # genotip (seria de gene cu care reprezinti fenotipul)
        self.__f = None

    def getGenes(self):
        return self.__x

    @staticmethod
    def distance(start, end):
        return abs(end[0] - start[0]) + abs(end[1] - start[1])

    def fitness(self, startingPosition, map):
        # TODO compute the fitness for the individual and save it in self.__f
        # nr casute vazute pana la conflict
        if self.__f is not None:
            return
        seen = 0
        current_position = startingPosition
        aux_map = Map()
        for i in range(map.n):
            for j in range(map.m):
                aux_map.surface[i][j] = map.surface[i][j]
        for gene in self.__x:
            if 0 > current_position[0] or current_position[0] > 19 or \
                    0 > current_position[1] or current_position[1] > 19 or \
                    map.surface[current_position[0]][current_position[1]] == 1:
                break
            readings = aux_map.readUDMSensors(current_position[0], current_position[1])
            seen = seen + readings[0] + readings[1] + readings[2] + readings[3]
            if gene.move == 0:
                current_position = (current_position[0] - 1, current_position[1])  # N
            elif gene.move == 1:
                current_position = (current_position[0] + 1, current_position[1])  # S
            elif gene.move == 2:
                current_position = (current_position[0], current_position[1] + 1)  # E
            elif gene.move == 3:
                current_position = (current_position[0], current_position[1] - 1)  # V
        if 0 > current_position[0] or current_position[0] > 19 or \
                0 > current_position[1] or current_position[1] > 19 or \
                map.surface[current_position[0]][current_position[1]] == 1:
            self.__f = seen
            return

        readings = aux_map.readUDMSensors(current_position[0], current_position[1])
        seen = seen + readings[0] + readings[1] + readings[2] + readings[3] #- self.distance(startingPosition, current_position)
        self.__f = seen


    def getFitness(self):
        return self.__f

    def mutate(self, mutateProbability=0.04):
        if random() < mutateProbability:
            # TODO perform a mutation with respect to the representation
            # Random resetting mutation: the value of a  gene is changed (by probability pm) into another value (from the definition domain)
            positions = []
            for i in range(self.__size // 2):  # half of the values are changed
                r = randint(0, self.__size - 1)
                while r in positions:
                    r = randint(0, self.__size - 1)
                positions.append(r)
                self.__x[r] = Gene()

    def crossover(self, otherParent, crossoverProbability=0.8):
        offspring1, offspring2 = Individual(self.__size), Individual(self.__size)
        # TODO perform the crossover between the self and the otherParent

        # Uniform crossover: Each gene of an offspring comes from a randomly and uniform selected parent:
        #  For each gene a uniform random number r is generated
        for i in range(self.__size):
            #  If r < probability p (usually, p=0.5), c1 will inherit that gene from p2 and c2 from p1,
            if random() < crossoverProbability:
                offspring1.__x[i] = otherParent.__x[i]
                offspring2.__x[i] = self.__x[i]
            else:
                #  otherwise, c1 will inherit p1 and c2 will inherit p2
                offspring1.__x[i] = self.__x[i]
                offspring2.__x[i] = otherParent.__x[i]


        return offspring1, offspring2


class Population:
    def __init__(self, starting_position, map, populationSize=0, individualSize=0):
        self.__populationSize = populationSize
        self.__v = [Individual(individualSize) for x in range(populationSize)]
        self.startingPosition = starting_position
        self.map = map

    def evaluate(self):
        # evaluates the population
        for x in self.__v:
            x.fitness(self.startingPosition, self.map)

    def getPopulation(self):
        return self.__v

    def selection(self, k=0):
        # TODO perform a selection of k individuals from the population and returns that selection
        # assuming k < population size
        self.__v.sort(key=lambda e: e.getFitness(), reverse=True)
        selection = []
        for i in range(k):
            selection.append(self.__v[i])
        return selection

    def addToPopulation(self, element):
        self.__v.append(element)

    def removeFromPopulation(self, element):
        self.__v.remove(element)

    def averageFitness(self):
        average = 0
        for x in self.__v:
            average += x.getFitness()
        return average / len(self.__v)

        # return np.average([x.getFitness() for x in self.__v])


class Map:
    def __init__(self, n=20, m=20):
        self.n = n
        self.m = m
        self.surface = np.zeros((self.n, self.m))

    def __getitem__(self, x, y):
        return self.surface[x][y]

    def randomMap(self, fill=0.2):
        for i in range(self.n):
            for j in range(self.m):
                if random() <= fill:
                    self.surface[i][j] = 1

    def saveMap(self, fileName="test.map"):
        with open(fileName, 'wb') as f:
            pickle.dump(self, f)
            f.close()

    def loadMap(self, fileName):
        with open(fileName, "rb") as f:
            dummy = pickle.load(f)
            self.n = dummy.n
            self.m = dummy.m
            self.surface = dummy.surface
            f.close()

    def readUDMSensors(self, x, y):
        readings = [0, 0, 0, 0]
        # UP
        xf = x - 1
        while (xf >= 0) and (self.surface[xf][y] != 1):
            if self.surface[xf][y] != 2:
                readings[UP] = readings[UP] + 1
            self.surface[xf][y] = 2
            xf = xf - 1
        # DOWN
        xf = x + 1
        while (xf < self.n) and (self.surface[xf][y] != 1):
            if self.surface[xf][y] != 2:
                readings[DOWN] = readings[DOWN] + 1
            self.surface[xf][y] = 2
            xf = xf + 1
        # LEFT
        yf = y + 1
        while (yf < self.m) and (self.surface[x][yf] != 1):
            if self.surface[x][yf] != 2:
                readings[LEFT] = readings[LEFT] + 1
            self.surface[x][yf] = 2
            yf = yf + 1
        # RIGHT
        yf = y - 1
        while (yf >= 0) and (self.surface[x][yf] != 1):
            if self.surface[x][yf] != 2:
                readings[RIGHT] = readings[RIGHT] + 1
            self.surface[x][yf] = 2
            yf = yf - 1

        return readings

    def __str__(self):
        string = ""
        for i in range(self.n):
            for j in range(self.m):
                string = string + str(int(self.surface[i][j]))
            string = string + "\n"
        return string
