from ui.start_game import *
import pygame
import sys


class GUI:
    def __init__(self, game):
        self._game = game
        self._blue = (0, 0, 255)
        self._black = (0, 0, 0)
        self._red = (255, 0, 0)
        self._yellow = (255, 255, 0)
        self._squaresize = 100
        self._radius = int(self._squaresize / 2 - 5)
        self._player_piece = 1
        self._ai_piece = -1

    @property
    def game(self):
        return self._game

    def draw_board(self, board, screen, height):
        for c in range(7):
            for r in range(6):
                pygame.draw.rect(screen, self._blue, (c * self._squaresize, r * self._squaresize + self._squaresize, self._squaresize, self._squaresize))
                pygame.draw.circle(screen, self._black, (int(c * self._squaresize + self._squaresize / 2), int(r * self._squaresize + self._squaresize + self._squaresize / 2)), self._radius)

        for c in range(7):
            for r in range(6):
                if board[r * 7 + c] == self._player_piece:
                    pygame.draw.circle(screen, self._red, (int(c * self._squaresize + self._squaresize / 2), height - int(r * self._squaresize + self._squaresize / 2)), self._radius)
                elif board[r * 7 + c] == self._ai_piece:
                    pygame.draw.circle(screen, self._yellow, (int(c * self._squaresize + self._squaresize / 2), height - int(r * self._squaresize + self._squaresize / 2)), self._radius)

        pygame.display.update()

    def start(self):
        board = self.game.board._data
        game_over = False
        pygame.init()
        width = 7 * self._squaresize
        height = 7 * self._squaresize
        size = (width, height)
        screen = pygame.display.set_mode(size)
        self.draw_board(board, screen, height)
        pygame.display.update()
        myfont = pygame.font.SysFont("comicsansms", 60)
        player_turn = True
        col = -1
        while not self.game.board.winning_move(col) and not self.game.board.is_tie(col):

            for event in pygame.event.get():

                if event.type == pygame.QUIT:
                    sys.exit()
                if event.type == pygame.MOUSEMOTION:
                    pygame.draw.rect(screen, self._black, (0, 0, width, self._squaresize))
                    posx = event.pos[0]
                    if player_turn is True:
                        pygame.draw.circle(screen, self._red, (posx, int(self._squaresize // 2)), self._radius)

                pygame.display.update()

                if event.type == pygame.MOUSEBUTTONDOWN:
                    if player_turn is True:
                        posx = event.pos[0]
                        col = posx // self._squaresize
                        self.game.player_move(col)
                        pygame.display.update()
                        self.draw_board(board, screen, height)
                    else:
                        col = self.game.computer_move(board)
                        self.draw_board(board, screen, height)
                    player_turn = not player_turn
                    pygame.display.update()

        if self.game.board.is_tie(col) is True:
            label = myfont.render("It's a draw", 1, self._red)
            screen.blit(label, (40, 10))
            game_over = True

        elif not player_turn:
            label = myfont.render("Congrats! You won!", 1, self._red)
            screen.blit(label, (40, 10))
            game_over = True
        else:
            label = myfont.render("You were defeated!", 1, self._red)
            screen.blit(label, (40, 10))
            game_over = True
        self.draw_board(board, screen, height)

        if game_over:
            pygame.time.wait(3000)


b = Board()
c = AI()
game = Game(b, c)
g = GUI(game)
g.start()
