from Controller import *
from random import randint
import time


class GUI:
    def __init__(self):
        # we position the drone somewhere in the area
        x = randint(STARTAREA, ENDAREA)
        y = randint(STARTAREA, ENDAREA)
        self.__controller = Controller(x, y)

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
        self.initGame()
        screen = self.setScreen()

        # define a variable to control the main loop
        running = True

        # main loop
        while running:
            for event in pygame.event.get():
                # only do something if the event is of type QUIT
                if event.type == pygame.QUIT:
                    # change the value to False, to exit the main loop
                    running = False

            # move one step using DFS
            self.__controller.moveDFS()
            time.sleep(SLEEP)

            # stop main window execution when the stack is empty
            if self.__controller.getDroneX() is None:
                running = False
            else:
                self.__controller.markDetectedWalls()
                screen.blit(self.__controller.getMapImage(), MAPBLIT)
                pygame.display.flip()

        pygame.quit()
