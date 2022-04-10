# -*- coding: utf-8 -*-


# imports
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
#              ATENTION! the function doesn't check if the path passes trough walls

class UI:
    def __init__(self):
        self.__controller = controller()
        self.__popSize = 50
        self.__indSize = 30
        self.__genCount = 20
        self.__nrIterations = 100

    @staticmethod
    def printMenu():
        print("\n1. Map options")
        print("2. EA Options")
        print("3. Exit")

    @staticmethod
    def printMapOptions():
        print("\n1. Create a random map")
        print("2. Load a map")
        print("3. Save a map")
        print("4. Visualize a map")
        print("0. Back")

    @staticmethod
    def printEAOptions():
        print("\n1. Parameters setup")
        print("2. Run the solver")
        print("3. Visualize the statistics")
        print("4. View the drone moving on a path")
        print("0. Back")

    def runMap(self):
        newMap = Map()

        done = False

        while not done:
            self.printMapOptions()
            option = int(input("Your option: "))
            if option == 1:
                newMap.randomMap()
                print("Map successfully generated\n")
            elif option == 2:
                fileName = input("Give a file name: ")
                newMap.loadMap(fileName)
                print("Map successfully loaded\n")
            elif option == 3:
                fileName = input("Give a file name: ")
                newMap.saveMap(fileName)
                print("Map successfully saved\n")
            elif option == 4:
                print(newMap)
            elif option == 0:
                done = True
            else:
                print("Invalid command")

        self.__controller.setMap(newMap)

    def runEA(self):
        done = False

        while not done:
            self.printEAOptions()
            option = int(input("Your option: "))
            if option == 1:
                self.__popSize = int(input("Population size: "))
                self.__indSize = int(input("Number of individuals: "))
                self.__genCount = int(input("Number of generations: "))
                self.__nrIterations = int(input("Number of iterations: "))
            elif option == 2:
                pass
            elif option == 3:
                pass
            elif option == 4:
                pass
            elif option == 0:
                done = True
            else:
                print("Invalid command")

    def run(self):
        done = False

        while not done:
            self.printMenu()
            option = int(input("Your option: "))
            if option == 1:
                self.runMap()
            elif option == 2:
                self.runEA()
            elif option == 3:
                done = True
            else:
                print("Invalid command")
