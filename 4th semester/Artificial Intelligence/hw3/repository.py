# -*- coding: utf-8 -*-

import pickle

import rand as rand

from domain import *


class repository:
    def __init__(self):
        self.__populations = []
        self.__dmap = Map()
        self.__drone = Drone(rand.randint(0, 19), rand.randint(0, 19))

    def getMap(self):
        return self.__dmap

    def setMap(self, newMap):
        self.__dmap = newMap

    def getPopulations(self):
        return self.__populations

    def setLastPopulation(self, population):
        self.__populations[-1] = population

    def addPopulation(self, population):
        self.__populations.append(population)

    def load_random_map(self):
        self.__dmap.randomMap()

    def getDrone(self):
        return self.__drone
        
    def createPopulation(self, args):
        # args = [populationSize, individualSize] -- you can add more args    
        return Population(args[0], args[1])
        
    # TO DO : add the other components for the repository: 
    #    load and save from file, etc
            