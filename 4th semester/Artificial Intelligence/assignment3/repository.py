# -*- coding: utf-8 -*-

from domain import *


class Repository:
    def __init__(self):
        self.__populations = []  # list of populations
        self._cmap = Map()

    @property
    def populations(self):
        return self.__populations

    @property
    def cmap(self):
        return self._cmap

    def createPopulation(self, startingPos, populationSize, individualSize):
        return Population(startingPos, self._cmap, populationSize, individualSize)

    # TODO : add the other components for the repository: load and save from file, etc
    def randomMap(self):
        self._cmap = Map()
        self.cmap.randomMap()

    def loadMap(self, file):
        self.cmap.loadMap(file)

    def saveMap(self, file):
        self.cmap.saveMap(file)

    @cmap.setter
    def cmap(self, value):
        self._cmap = value
