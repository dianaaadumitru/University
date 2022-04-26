# -*- coding: utf-8 -*-
import secrets
from copy import deepcopy, copy
import random as rand
from utils import *
import numpy as np

POSSIBLE_GENE_VALUES = [UP, RIGHT, DOWN, LEFT]
MAP_TERRITORY = [element for element in range(0, 20)]
START_STUCK = 1   # negative number, so that we don't mistake it for a bad path
BORDER_STUCK = 0   # negative number, so that we don't mistake it for a bad pat
# the glass gene can be replaced with int or float, or other types
# depending on your problem's representation


class Gene:
    def __init__(self):
        self.value = secrets.choice(
             POSSIBLE_GENE_VALUES # we chose a random value from UP/DOWN/LEFT/RIGHT
        )

    def getValue(self):
        return self.value

    @staticmethod
    def getNewGeneWithValue(value):
        gene = Gene()
        gene.value = value
        return gene

    def __str__(self):
        return "Gene->" + str(self.value)

    def __repr__(self):
        return "Gene->" + str(self.value)


class Individual:
    def __init__(self, dmap, drone, size=0):
        self.__size = size  # nr of genes = path length - 1 ??
        self.__path = [rand.randint(0, 3) for _ in range(self.__size)]
        self.__drone = drone
        self.__map = dmap
        self.__visited = []
        self.__f = self.fitness()

    def newVerticalPositions(self):
        allNewVisiblePosition = self.__readUDMSensors(
            self.__drone[0],
            self.__drone[1],
            copy(self.__map.surface))

        return allNewVisiblePosition[UP] + allNewVisiblePosition[DOWN]

    def newHorizontalPositions(self):
        allNewVisiblePosition = self.__readUDMSensors(
            self.__drone[0],
            self.__drone[1],
            copy(self.__map.surface))

        return allNewVisiblePosition[LEFT] + allNewVisiblePosition[RIGHT]

    def __readUDMSensors(self, x, y, auxMap):
        readings = 0
        # UP
        xf = x - 1
        while (xf >= 0) and (auxMap[xf][y] != 1):
            if (xf,y) not in self.__visited:
                readings = readings + 1
                self.__visited.append((xf,y))
            xf = xf - 1
        # DOWN
        xf = x + 1
        while (xf < self.__map.n) and (auxMap[xf][y] != 1):
            if (xf, y) not in self.__visited:
                readings = readings + 1
                self.__visited.append((xf,y))
            xf = xf + 1
        # LEFT
        yf = y + 1
        while (yf < self.__map.m) and (auxMap[x][yf] != 1):
            if (x, yf) not in self.__visited:
                readings = readings + 1
                self.__visited.append((x,yf))
            yf = yf + 1
        # RIGHT
        yf = y - 1
        while (yf >= 0) and (auxMap[x][yf] != 1):
            if (x, yf) not in self.__visited:
                readings = readings + 1
                self.__visited.append((x, yf))
            yf = yf - 1

        return readings, auxMap

    def validCoord(self, x, y):
        return -1 < x < self.__map.m and -1 < y < self.__map.n and self.__map.surface[x][y] != 1

    @staticmethod
    def checkMapConstraint(givenPosition):
        if givenPosition[0] in MAP_TERRITORY and givenPosition[1] in MAP_TERRITORY:
            return True
        return False

    def getDrone(self):
        return self.__drone

    def fitness(self):
        # self.__visited = []
        # auxMap = copy(self.__map.surface)
        # sumFitness, newMap = self.__readUDMSensors(self.__drone.getX(),self.__drone.getY(),auxMap)
        # auxMap = newMap
        # coords = (self.__drone.getX(),self.__drone.getY())
        # nrMoves = 0
        #
        # for x in self.__path:
        #     nrMoves += 1
        #     dir = v[x]
        #     newX = coords[0]+dir[0]
        #     newY = coords[1]+dir[1]
        #     if self.validCoord(newX, newY):
        #         fitness, newMap = self.__readUDMSensors(newX, newY, auxMap)
        #         auxMap = newMap
        #         sumFitness+=fitness
        #     else:
        #         sumFitness-=50
        #     coords = (newX, newY)
        #     if nrMoves == self.__drone.getBattery():
        #         break
        #
        # self.__f = sumFitness
        wentOverWall = 0

        print("drone: ", self.getDrone())
        if not self.checkMapConstraint(self.getDrone()):
            return BORDER_STUCK
        if self.__map.surface[self.__drone[0]][self.__drone[1]] == 1:
            return START_STUCK  # if the first position is a wall

        finalFitness = sum(self.__readUDMSensors(self.__drone[0], self.__drone[1], copy(self.__map.surface)))
        # put all visible positions from the initial position of the drone

        currentPosition = deepcopy(self.__drone)

        localPath = list()
        localPath.append(deepcopy(currentPosition))

        for position in self.__path:
            if self.__map.surface[currentPosition[0]][currentPosition[1]] == 1:
                wentOverWall += 1  # when finding a wall
                # the reason we don't block the path is to not get stuck in a local optima after that
                # we only add the new visible positions (if we advance vertically, no new vertical positions will
                # appear)
            elif position == 0:
                currentPosition[0] = currentPosition[0] - 1
                if not self.checkMapConstraint(currentPosition):
                    return BORDER_STUCK

                if currentPosition not in localPath:
                    finalFitness += self.newHorizontalPositions()
                localPath.append(deepcopy(currentPosition))

            elif position == 2:
                currentPosition[0] = currentPosition[0] + 1
                if not self.checkMapConstraint(currentPosition):
                    return BORDER_STUCK

                if currentPosition not in localPath:
                    finalFitness += self.newHorizontalPositions()
                localPath.append(deepcopy(currentPosition))

            elif position == 1:
                currentPosition[1] = currentPosition[1] - 1
                if not self.checkMapConstraint(currentPosition):
                    return BORDER_STUCK

                if currentPosition not in localPath:
                    finalFitness += self.newVerticalPositions()
                localPath.append(deepcopy(currentPosition))

            elif position == 3:
                currentPosition[1] = currentPosition[1] + 1
                if not self.checkMapConstraint(currentPosition):
                    return BORDER_STUCK

                # if currentPosition not in localPath:
                #     finalFitness += self.newVerticalPositions()
                localPath.append(deepcopy(currentPosition))

        print("wall: ", wentOverWall)
        if wentOverWall:
            finalFitness = int(finalFitness / (2 ** wentOverWall))

        return finalFitness

    def getFitness(self):
        self.fitness()
        return self.__f

    def getChromosome(self):
        return self.__path

    def mutate(self, mutateProbability=0.04):
        if rand.random() < mutateProbability:
            self.__path[rand.randint(0, self.__size - 1)] = rand.randint(0, 3)
            # perform a mutation with respect to the representation

        # performing a creep mutation
        # if rand.random() < mutateProbability:
        #     randomGeneIndex = rand.randint(0, self.__size - 1)  # randomly select a position from the representation
        #     geneValuesForMutation = deepcopy(POSSIBLE_GENE_VALUES)  # take the possible gene values
        #     # print(self.__path[randomGeneIndex])
        #     geneValuesForMutation.remove(
        #         self.__path[randomGeneIndex])  # keep only values that differ from the one
        #     # we want to change
        #
        #     self.__path[randomGeneIndex] = rand.choice(geneValuesForMutation)
        #       # change the value randomly with one from the above selected

    def crossover(self, otherParent, crossoverProbability=0.8):
        # here we use cutting points
        offspring1, offspring2 = Individual(self.__drone, self.__map, self.__size), \
                                 Individual(self.__drone, self.__map, self.__size)
        if rand.random() < crossoverProbability:
            cut = rand.randint(0, self.__size - 1)  # randomly select a position from the representation used as a cutting point
            offspring1.__path = self.__path[:cut] + otherParent.__path[cut:]
            offspring2.__path = otherParent.__path[:cut] + self.__path[cut:]
            # perform the crossover between the self and the otherParent

        return offspring1, offspring2

    def computePath(self):
        finalX = self.__drone[0]
        finalY = self.__drone[1]
        path = [[finalY, finalX]]
        # iterate reversely in positions
        pathTaken = deepcopy(self.__path)
        pathTaken.reverse()
        for position in pathTaken:
            if position == UP:
                finalY += 1
                path.append([finalY, finalX])
            elif position == DOWN:
                finalY -= 1
                path.append([finalY, finalX])
            elif position == LEFT:
                finalX += 1
                path.append([finalY, finalX])
            elif position == RIGHT:
                finalX -= 1
                path.append([finalY, finalX])
        return path


class Population:
    def __init__(self, map, drone, populationSize=10, individualSize=20):
        self.__populationSize = populationSize
        self.__v = [Individual(map, drone, individualSize) for x in range(populationSize)]

    def evaluate(self):
        # evaluates the population
        for x in self.__v:
            x.fitness()

    def selection(self, k=3):
        # perform a selection of k individuals from the population
        # and returns that selection
        return sorted(self.__v, key=lambda x: x.getFitness(), reverse=True)[:k]

    def getIndividuals(self):
        return self.__v

    def setIndividuals(self, ind):
        self.__v = ind

    def addIndividual(self, individual):
        self.__v.append(individual)

    def findAverageAndStd(self):
        totalFitness = list()
        for individual in self.__v:
            totalFitness.append(individual.getFitness())
        return np.average(totalFitness), np.std(totalFitness)

    def getBestPath(self):
        return max(self.__v, key=lambda individual: individual.getFitness()).computePath()


class Map:
    def __init__(self, n=20, m=20):
        self.n = n
        self.m = m
        self.surface = np.zeros((self.n, self.m))

    def randomMap(self, fill=0.2):
        for i in range(self.n):
            for j in range(self.m):
                if rand.random() <= fill:
                    self.surface[i][j] = 1

    def __str__(self):
        string = ""
        for i in range(self.n):
            for j in range(self.m):
                string = string + str(int(self.surface[i][j]))
            string = string + "\n"
        return string

    def loadMap(self, fileName):
        file = open(fileName, "r")
        line = file.readline()
        l = line.split(" ")
        n, m = l[0], l[1]
        self.n = int(n)
        self.m = int(m)
        for i in range(int(n)):
            line = file.readline().split(" ")
            for j in range(int(m)):
                self.surface[i][j] = int(line[j])
        file.close()

    def saveMap(self,fileName):
        file = open(fileName, "w")
        file.write("{0} {1}\n".format(self.n, self.m))
        string = ""
        for i in range(self.n):
            for j in range(self.m):
                string = string + str(int(self.surface[i][j]))
            string = string + "\n"
        file.write(string)
        file.close()

# class Drone:
#     def __init__(self,x ,y, battery = 20):
#         self.__x = x
#         self.__y = y
#         self.__battery = battery
#
#     def getX(self):
#         return self.__x
#
#     def getY(self):
#         return self.__y
#
#     def getBattery(self):
#         return self.__battery
