# -*- coding: utf-8 -*-

import pickle
from random import randint

from domain import *

LAST_POPULATION = -1
DUMMY_STARTING_POSITION = 0  # will be changed later by a setter


class Repository:
    def __init__(self):
        self.__populations = []
        self.__map = Map()
        self.__drone = [DUMMY_STARTING_POSITION, DUMMY_STARTING_POSITION]
        # self.createPopulation()

    def createPopulation(self):
        # args = [populationSize, individualSize] -- you can add more args
        self.__populations.append(Population(self.__map, self.__drone))

    def getPopulations(self):
        return self.__populations

    def setLastPopulation(self, population):
        self.__populations[-1] = population

    def getAverageAndStdOfLastPopulation(self):
        return self.__populations[-1].findAverageAndStd()

    def addPopulation(self, population):
        self.__populations.append(population)

    def setDroneInitialPosition(self, newPosition):
        self.__drone = newPosition

    def load_random_map(self):
        self.__map.randomMap()

    def random_drone(self):
        x = randint(0, self.__map.n - 1)
        y = randint(0, self.__map.m - 1)
        while self.__map.surface[x][y] != 0:
            x = randint(0, self.__map.n - 1)
            y = randint(0, self.__map.m - 1)
        self.__drone = [x, y]

    def getMap(self):
        return self.__map

    def getDrone(self):
        return self.__drone

    def setMap(self, newMap):
        self.__map = newMap