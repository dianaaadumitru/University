import pygame
from pygame.locals import *

from Domain.DMap import DMap
from Domain.Drone import Drone
from Domain.Environment import Environment
from Domain.Constants import *


class Controller:
    def __init__(self, x, y):
        self.__env = Environment()
        self.__drone = Drone(x, y)
        self.__dMap = DMap()

    def getEnvironment(self):
        return self.__env

    def getDMap(self):
        return self.__dMap

    def getDrone(self):
        return self.__drone

    def getDroneX(self):
        return self.__drone.getX()

    def getDroneY(self):
        return self.__drone.getY()

    def getDroneStack(self):
        return self.__drone.getStack()

    def getDroneVisited(self):
        return self.__drone.getVisited()

    def move(self, detectedMap):
        pressed_keys = pygame.key.get_pressed()
        if self.x > 0:
            if pressed_keys[K_UP] and detectedMap.surface[self.x - 1][self.y] == 0:
                self.x = self.x - 1
        if self.x < 19:
            if pressed_keys[K_DOWN] and detectedMap.surface[self.x + 1][self.y] == 0:
                self.x = self.x + 1

        if self.y > 0:
            if pressed_keys[K_LEFT] and detectedMap.surface[self.x][self.y - 1] == 0:
                self.y = self.y - 1
        if self.y < 19:
            if pressed_keys[K_RIGHT] and detectedMap.surface[self.x][self.y + 1] == 0:
                self.y = self.y + 1

    def moveDFS(self):
        self.__drone.visited.append((self.__drone.x, self.__drone.y))

        # order will be UP, LEFT, DOWN, RIGHT
        for pos in v:
            # check if the drone stays within the map
            if -1 < self.__drone.x + pos[0] < 20 and -1 < self.__drone.y + pos[1] < 20:
                # check if the area is visited and if the drone doesn't hit any walls
                if self.__dMap.surface[self.__drone.x + pos[0]][self.__drone.y + pos[1]] == 0 and \
                        (self.__drone.x + pos[0], self.__drone.y + pos[1]) not in self.__drone.visited:
                    # add the current position to the stack, so we can trace back when the drone gets stuck
                    self.__drone.stack.append((self.__drone.x, self.__drone.y))
                    # add the next position
                    self.__drone.stack.append((self.__drone.x + pos[0], self.__drone.y + pos[1]))

        # when the stack is empty, x and y will be None and execution will stop
        if not self.__drone.stack:
            self.__drone.x = None
            self.__drone.y = None


        else:
            # get the next position
            current = self.__drone.stack.pop()
            self.__drone.x = current[0]
            self.__drone.y = current[1]

    def getMapImage(self):
        return self.__dMap.image(self.getDroneX(), self.getDroneY(), self.getDroneVisited())

    def markDetectedWalls(self):
        return self.__dMap.markDetectedWalls(self.__env, self.getDroneX(), self.getDroneY())

    def getEnvImage(self):
        return self.__env.image()
