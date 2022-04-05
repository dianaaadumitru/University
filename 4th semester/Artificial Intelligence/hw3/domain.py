# -*- coding: utf-8 -*-

from random import *
from utils import *
import numpy as np
from copy import copy

# the glass gene can be replaced with int or float, or other types
# depending on your problem's representation


class gene:
    def __init__(self):
        # random initialise the gene according to the representation
        pass


class Individual:
    def __init__(self, drone, dmap, size=0):
        self.__size = size
        self.__x = [gene() for i in range(self.__size)]
        self.__f = None
        self.__drone = drone
        self.__map = dmap
        self.__visited = []

    def __readUDMSensors(self, x, y, auxMap):
        readings = 0
        # UP
        xf = x - 1
        while (xf >= 0) and (auxMap[xf][y] != 1):
            if (xf, y) not in self.__visited:
                readings = readings + 1
                self.__visited.append((xf, y))
            xf = xf - 1
        # DOWN
        xf = x + 1
        while (xf < self.__map.n) and (auxMap[xf][y] != 1):
            if (xf, y) not in self.__visited:
                readings = readings + 1
                self.__visited.append((xf, y))
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
        
    def fitness(self):
        # compute the fitness for the individual
        # and save it in self.__f
        self.__visited = []
        auxMap = copy(self.__map.surface)
        sumFitness, newMap = self.__readUDMSensors(self.__drone.getX(), self.__drone.getY(), auxMap)
        auxMap = newMap
        coords = (self.__drone.getX(), self.__drone.getY())
        nrMoves = 0

        for x in self.__x:
            nrMoves += 1
            dir = v[x]
            newX = coords[0] + dir[0]
            newY = coords[1] + dir[1]
            if self.validCoord(newX, newY):
                fitness, newMap = self.__readUDMSensors(newX, newY, auxMap)
                auxMap = newMap
                sumFitness += fitness
            else:
                sumFitness -= 50
            coords = (newX, newY)
            if nrMoves == self.__drone.getBattery():
                break

        self.__f = sumFitness
    
    def mutate(self, mutateProbability=0.04):
        if random() < mutateProbability:
            pass
            # perform a mutation with respect to the representation

    def crossover(self, otherParent, crossoverProbability=0.8):
        offspring1, offspring2 = Individual(self.__size, self.__map, self.__size), Individual(self.__size, self.__map, self.__size)
        if random() < crossoverProbability:
            pass
            # perform the crossover between the self and the otherParent 
        
        return offspring1, offspring2
    

class Population:
    def __init__(self, drone, dmap, populationSize=10, individualSize=20):
        self.__populationSize = populationSize
        self.__v = [Individual(drone, dmap, individualSize) for x in range(populationSize)]
        
    def evaluate(self):
        # evaluates the population
        for x in self.__v:
            x.fitness()

    def selection(self, k=0):
        # perform a selection of k individuals from the population
        # and returns that selection
        pass

    def getIndividuals(self):
        return self.__v

    def setIndividuals(self, ind):
        self.__v = ind

    def addIndividual(self, individual):
        self.__v.append(individual)
    

class Map:
    def __init__(self, n=20, m=20):
        self.n = n
        self.m = m
        self.surface = np.zeros((self.n, self.m))
    
    def randomMap(self, fill=0.2):
        for i in range(self.n):
            for j in range(self.m):
                if random() <= fill:
                    self.surface[i][j] = 1
                
    def __str__(self):
        string = ""
        for i in range(self.n):
            for j in range(self.m):
                string = string + str(int(self.surface[i][j]))
            string = string + "\n"
        return string

    def loadMap(self, mapName):
        pass

    def saveMap(self, mapName):
        pass


class Drone:
    def __init__(self, x, y, battery=20):
        self.__x = x
        self.__y = y
        self.__battery = battery

    def getX(self):
        return self.__x

    def getY(self):
        return self.__y

    def getBattery(self):
        return self.__battery
