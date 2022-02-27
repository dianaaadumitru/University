from domain.board import Board
from service.game import Game


class UI:
    def __init__(self, game):
        self._game = game

    @staticmethod
    def split_command(command):
        tokens = command.strip().split(' ', 1)
        command_word = tokens[0].lower().strip()
        command_params = tokens[1].strip() if len(tokens) == 2 else ' '

        return command_word, command_params

    def start(self):
        board = self._game._board._data
        command_z = 0
        self._game.board.place_snake()
        self._game.board.place_apple(self._game.board._apple_count)
        print(self._game.board)
        ok = 1
        while self._game.board.game_over(ok) != 0:
            command = input("command > ")
            command_word, command_params = self.split_command(command)
            if command_word == 'move':

                if command_z == 0:
                    try:
                        ok = self._game.move(command_params)
                        print(self._game.board)
                    except Exception as e:
                        print(e)
                        ok = 0
                elif command_z == 'left':
                    ok = self._game.move_after_left(command_params)
                    print(self._game.board)
                else:
                    ok = self._game.move_after_right(command_params)
                    print(self._game.board)
            elif command_word == 'left':
                command_z = 'left'
                ok = self._game.left()
                print(self._game.board)

            elif command_word == 'right':
                command_z = 'right'
                ok = self._game.right()
                print(self._game.board)

            elif command_word == 'up':
                pass
            elif command_word == 'down':
                pass

            else:
                print("invalid input")

        if self._game.board.game_over(ok) == 0:
            print("Game over")


b = Board(7, 10)
g = Game(b)
u = UI(g)
u.start()
