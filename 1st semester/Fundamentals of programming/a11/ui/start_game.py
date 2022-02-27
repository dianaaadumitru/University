from computer.computer import AI
from domain.board import Board
from service.game import Game


class UI:
    def __init__(self, game):
        self._game = game

    @property
    def game(self):
        return self._game

    @staticmethod
    def read_player_move():
        try:
            choice = input("Your move: ")
            choice = int(choice)
            if 0 <= choice <= 6:
                return choice
            else:
                raise ValueError("Move not into the board")
        except Exception:
            print("Invalid move")

    def start(self):
        board = self.game.board
        l = self.game.board._data

        player_turn = True
        col = -1
        col = int(col)
        while not board.winning_move(col) and not board.is_tie(col):
            if player_turn is True:
                print("\n------------------------------\n")
                print(board)
            if player_turn is True:
                try:
                    col = self.read_player_move()
                    self.game.player_move(col)
                except Exception as e:
                    print(e)
                    continue
            else:
                col = self.game.computer_move(l)
            player_turn = not player_turn

        if board.is_tie(col) is True:
            print("It's a draw")

        elif not player_turn:
            print(board)
            print("Congrats! You won!")
        else:
            print("------------------------------")
            print(board)
            print("You were defeated!")


# b = Board()
# c = AI()
# g = Game(b, c)
# x = UI(g)
# x.start()
