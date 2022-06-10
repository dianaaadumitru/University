# -*- coding: utf-8 -*-

# imports
import numpy
import matplotlib.pyplot as plt
import random as rand
from gui import *
from controller import *
from repository import *
from domain import *


# create a menu
#   1. map options:
#         a. create random map
#         b. load a map
#         c. save a map
#         d visualise map
#   2. EA options:
#         a. parameters setup
#         b. run the solver
#         c. visualise the statistics
#         d. view the drone moving on a path
#              function gui.movingDrone(currentMap, path, speed, markseen)
#              ATTENTION! the function doesn't check if the path passes through walls

class UI:
    def __init__(self):
        self.controller = Controller()
        self.max_iterations = 1000
        self.starting_position = (0, 0)
        self.population_size = 100
        self.individual_size = 10
        self.path = []
        self.__rows = 20
        self.__columns = 20

    def randomMap(self):
        self.controller.randomMap()

    def loadMap(self):
        self.controller.loadMap("test.map")

    def saveMap(self):
        self.controller.saveMap("test.map")

    def viewMap(self):
        screen = initPyGame((self.controller.getMap().n * 20, self.controller.getMap().m * 20))
        for i in range(20):
            screen.blit(image(self.controller.getMap()), (0, 0))
        pygame.display.flip()
        pygame.time.wait(5000)
        closePyGame()

    def setupParams(self):
        self.max_iterations = int(input("Give the no of maximum iterations: "))
        x = int(input("Give x coordinate of the starting position: "))
        y = int(input("Give y coordinate of the starting position: "))
        self.population_size = int(input("Give population size: "))
        self.individual_size = int(input("Give individual size: "))
        self.starting_position = (x, y)

    def show_plot(self, averages):
        x = numpy.linspace(1, len(averages), len(averages))
        y = averages

        fig, ax = plt.subplots()
        ax.plot(x, y, linewidth=2.0)

        # ax.set(xlim=(1, len(averages)), xticks=numpy.arange(2, len(averages)),
        #        ylim=(0, self.__rows * self.__columns // 2), yticks=numpy.arange(1, self.__rows * self.__columns // 2))
        plt.show()

    def runSolver(self):
        start = time.time()
        result, averages = self.controller.solver(self.max_iterations, self.population_size, self.individual_size,
                                                  self.starting_position)
        end = time.time()
        print("Execution time: ", end - start)
        print(result.getFitness())
        self.path = [self.starting_position]
        for gene in result.getGenes():  # create the path
            self.path.append((self.path[-1][0] + v[gene.move][0], self.path[-1][1] + v[gene.move][1]))
        self.show_plot(averages)
        print("Average fitness: ", np.average(averages))

    def viewStats(self):
        list = []
        for i in range(30):
            rand.seed(i)
            list.append(self.controller.solver(self.max_iterations, self.population_size, self.individual_size,
                                               self.starting_position)[0].getFitness())
        print("Average fitness:", numpy.average(list))
        print("Standard deviation", numpy.std(list))

    def viewMovingDrone(self):
        movingDrone(self.controller.getMap(), self.path)

    @staticmethod
    def printMenu():
        print("exit")
        print("1. map options:")
        print(" a. create random map \n b. load a map \n c. save a map \n d. visualise map \n")
        print("2. EA options:")
        print(
            " a. parameters setup \n b. run the solver \n c. visualise the statistics \n d. view the drone moving on a "
            "path \n")
        print("Input example: 1a")

    def start(self):
        self.printMenu()
        command_dict = {"1a": self.randomMap, "1b": self.loadMap, "1c": self.saveMap, "1d": self.viewMap,
                        "2a": self.setupParams, "2b": self.runSolver, "2c": self.viewStats, "2d": self.viewMovingDrone}
        done = False
        while not done:
            command = input('command> ')

            if command in command_dict:
                # try:
                command_dict[command]()
                # except Exception as val_error:
                #    print(str(val_error))
            elif command == 'exit':
                done = True
            else:
                print('No command found')


if __name__ == '__main__':
    uis = UI()
    uis.start()
