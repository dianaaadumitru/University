class Game:
    def __init__(self, board, computer):
        self._board = board
        self._computer = computer

    @property
    def board(self):
        return self._board

    @property
    def computer(self):
        return self._computer

    def player_move(self, col):
        self.board.move(col, 'X')

    def computer_move(self, board):
        col = self.computer.computer_move(board)
        self.board.move(col, 'Y')
        return col
