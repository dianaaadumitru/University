from repository import *


class controller():
    def __init__(self):
        # args - list of parameters needed in order to create the controller
        self.__repository = repository()

    def getMap(self):
        return self.__repository.getMap()

    def getDrone(self):
        return self.__repository.getDrone()

    def iteration(self, args):
        # args - list of parameters needed to run one iteration
        # a iteration:
        # selection of the parrents
        # create offsprings by crossover of the parents
        # apply some mutations
        # selection of the survivors
        pass
        
    def run(self, args):
        # args - list of parameters needed in order to run the algorithm
        
        # until stop condition
        #    perform an iteration
        #    save the information need it for the satistics
        
        # return the results and the info for statistics
        pass

    def solver(self, args):
        # args - list of parameters needed in order to run the solver
        
        # create the population,
        # run the algorithm
        # return the results and the statistics
        pass

    def setMap(self, newMap):
        self.__repository.setMap(newMap)
