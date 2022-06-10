from Controller import *
from random import randint
import time


class GUI:
    def __init__(self):
        # we position the drone somewhere in the area
        # x = randint(STARTAREA, ENDAREA)
        # y = randint(STARTAREA, ENDAREA)
        # self.__controller = Controller(x, y)

        self.__initialX = randint(STARTAREA, ENDAREA)
        self.__initialY = randint(STARTAREA, ENDAREA)
        # get final coordinates for the drone
        self.__finalX = randint(STARTAREA, ENDAREA)
        self.__finalY = randint(STARTAREA, ENDAREA)
        self.__controller = Controller(self.__initialX, self.__initialY)

    @staticmethod
    def initGame():
        # initialize the pygame module
        pygame.init()

        # load and set the logo
        logo = pygame.image.load("logo32x32.png")
        pygame.display.set_icon(logo)
        pygame.display.set_caption("drone exploration")

    def setScreen(self):
        # create a surface on screen that has the size of 800 x 480
        screen = pygame.display.set_mode(MAINWINDOW)
        screen.fill(WHITE)
        screen.blit(self.__controller.getEnvironment().image(), (0, 0))

        return screen

    def start(self):
        pygame.init()
        # load and set the logo
        logo = pygame.image.load("logo32x32.png")
        pygame.display.set_icon(logo)
        pygame.display.set_caption("drone exploration")

        # create a surface on screen that has the size of 800 x 480
        screen = pygame.display.set_mode(MAINWINDOW)
        screen.fill(WHITE)

        # check whether the end point is a wall
        while self.__controller.getDMap().surface[self.__finalX][self.__finalY] == 1:
            self.__finalX = randint(STARTAREA, ENDAREA)
            self.__finalY = randint(STARTAREA, ENDAREA)

        print('Start: (' + str(self.__initialX) + ', ' + str(self.__initialY) + ')')
        print('End: (' + str(self.__finalX) + ', ' + str(self.__finalY) + ')')

        # define a variable to control the main loop
        running = True

        # variable for printing path
        printedTime = False
        printedG = False
        printedA = False

        # main loop
        while running:
            for event in pygame.event.get():
                # only do something if the event is of type QUIT
                if event.type == pygame.QUIT:
                    # change the value to False, to exit the main loop
                    running = False

            startG = time.time()
            pathG = self.__controller.searchGreedy(self.__initialX, self.__initialY, self.__finalX, self.__finalY)
            endG = time.time()
            if len(pathG) == 0:
                print('Path does not exist.')
                return

            if not printedG:
                print('Greedy Path: ')
                print(pathG)
                printedG = True
            screen.blit(self.__controller.displayWithPath(self.__controller.getDMap().image(PURPLE), pathG, LIGHTPURPLE),
                        (0, 0))

            startA = time.time()
            pathA = self.__controller.searchAStar(self.__initialX, self.__initialY, self.__finalX, self.__finalY)
            endA = time.time()
            if not printedA:
                print('A* Path: ')
                print(pathA)
                printedA = True
            screen.blit(self.__controller.displayWithPath(self.__controller.getDMap().image(GREEN2), pathA, PALEGREEN),
                        (400, 0))

            if not printedTime:
                print('\nExecution time for Greedy: ' + str(endG - startG))
                print('Execution time for A*: ' + str(endA - startA))
                printedTime = True

            pygame.display.flip()

        pygame.quit()
