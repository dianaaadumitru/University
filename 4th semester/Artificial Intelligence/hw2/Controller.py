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

    def getMapImage(self):
        return self.__dMap.image(self.getDroneX(), self.getDroneY(), self.getDroneVisited())

    def markDetectedWalls(self):
        return self.__dMap.markDetectedWalls(self.__env, self.getDroneX(), self.getDroneY())

    def getEnvImage(self):
        return self.__env.image()

    def manhattanDist(self, x1, y1, x2, y2):
        return abs(x1 - x2) + abs(y1 - y2)

    def validNode(self, x, y):
        return -1 < x < 20 and -1 < y < 20 and self.__dMap.surface[x][y] == 0

    def buildPath(self, prev, finalX, finalY):
        path = [(finalX, finalY)]
        coord = prev[(finalX, finalY)]
        while coord != (None, None):
            path.append(coord)
            coord = prev[coord]
        path.reverse()
        return path

    def searchAStar(self, initialX, initialY, finalX, finalY):
        found = False
        visited = []
        visitQueue = [(initialX, initialY)]
        prev = dict()
        prev[(initialX, initialY)] = (None, None)
        gValues = dict()  # store the g values
        gValues[(initialX, initialY)] = 0

        while visitQueue and not found:
            node = visitQueue.pop(0)  # pop the first element from the queue
            visited.append(node)  # mark the next step as visited

            if node == (finalX, finalY):
                found = True
            else:
                aux = []
                for d in v:  # search for neighbours and if they are nit visited also save them in aux
                    newX = node[0] + d[0]
                    newY = node[1] + d[1]

                    if self.validNode(newX, newY) and (newX, newY) not in visited:
                        if (newX, newY) not in visitQueue:
                            aux.append((newX, newY))  # add node to aux
                            prev[(newX, newY)] = node  # build the path
                            gValues[(newX, newY)] = gValues[node] + 1  # add to each new node the g value
                        else:
                            if gValues[(newX, newY)] > gValues[node] + 1:  # check if it's quicker to first visit the node, then the neighbour
                                visitQueue.remove((newX, newY))
                                aux.append((newX, newY))  # add node to aux
                                prev[(newX, newY)] = node  # build the path
                                gValues[(newX, newY)] = gValues[node] + 1  # add to each node the g value

                visitQueue.extend(aux)
                visitQueue.sort(
                    key=lambda coord: self.manhattanDist(coord[0], coord[1], finalX, finalY) + gValues[coord])

        if found:
            return self.buildPath(prev, finalX, finalY)
        else:
            return []

    def searchGreedy(self, initialX, initialY, finalX, finalY):
        found = False
        visited = []
        prev = dict()
        prev[(initialX, initialY)] = (None, None)
        visitQueue = [(initialX, initialY)]

        while visitQueue and not found:
            node = visitQueue.pop(0)
            visited.append(node)

            if node == (finalX, finalY):
                found = True
            else:
                aux = []
                for d in v:
                    newX = node[0] + d[0]
                    newY = node[1] + d[1]

                    if self.validNode(newX, newY) and (newX, newY) not in visited:
                        aux.append((newX, newY))
                        prev[(newX, newY)] = node
                visitQueue.extend(aux)
                visitQueue.sort(key=lambda coord: self.manhattanDist(coord[0], coord[1], finalX, finalY))

        if found:
            return self.buildPath(prev, finalX, finalY)
        else:
            return []

    def dummysearch(self):
        # example of some path in test1.map from [5,7] to [7,11]
        return [[5, 7], [5, 8], [5, 9], [5, 10], [5, 11], [6, 11], [7, 11]]

    def displayWithPath(self, image, path, color):
        mark = pygame.Surface((20, 20))
        mark.fill(color)
        for move in path:
            image.blit(mark, (move[1] * 20, move[0] * 20))

        return image

    def mapWithDrone(self, mapImage):
        drona = pygame.image.load("drona.png")
        mapImage.blit(drona, (self.getDroneY() * 20, self.getDroneX() * 20))

        return mapImage
