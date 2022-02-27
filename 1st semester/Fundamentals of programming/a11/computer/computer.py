import random
from copy import deepcopy


class RandomMove:
    @staticmethod
    def move(board):
        possible_moves = []
        for i in range(7):
            if board[7 * 5 + i] == 0:
                possible_moves.append(i)
        rez = random.randint(0, len(possible_moves) - 1)
        return int(possible_moves[rez])


class AI:
    def computer_move(self, board):
        board2 = board
        move = self.best_move(board2)
        return move

    def best_move(self, board):
        for col in range(7):
            board2 = deepcopy(board)
            try:
                self.move(board2, col, 'Y')
                if self.winning_move(col, board2):
                    return col
            except:
                pass

        for col in range(7):
            board2 = deepcopy(board)
            try:
                self.move(board2, col, 'X')
                if self.winning_move(col, board2):
                    return col
            except:
                pass

        r = RandomMove()
        return r.move(board)

    @staticmethod
    def move(board, col, symbol):
        """
        Verify if the move is valid and do it
        :param board:
        :param col: where do you want to make the move
        :param symbol: 'X' - player or 'Y' - computer
        :return:
        """
        _dict = {'X': 1, 'Y': -1}
        if col > 6 or col < 0 or board[7 * 5 + col] != 0:
            raise ValueError("Move not inside the board!")
        i = 0
        while i < 6:
            if board[7 * i + col] == 0:
                break
            i += 1

        board[7 * i + col] = _dict[symbol]

    @staticmethod
    def winning_move(column, board):
        """
        verify iif the las move is a win
        :param board:
        :param column: where was the last move
        :return:
        """
        if column == -1:
            return False
        poz = 5
        while board[poz * 7 + column] == 0:
            poz -= 1
        symbol = board[poz * 7 + column]
        row = [1, 0, -1, 0, 1, -1, -1, 1]
        col = [0, 1, 0, -1, 1, 1, -1, -1]
        for i in range(8):
            count = 0
            xnou = poz + row[i]
            ynou = column + col[i]
            xstart = xnou
            ystart = ynou
            while -1 < xnou < 6 and -1 < ynou < 7 and symbol == board[xnou * 7 + ynou]:
                count += 1
                xnou += row[i]
                ynou += col[i]
            xnou = xstart
            ynou = ystart
            count -= 1
            while -1 < xnou < 6 and -1 < ynou < 7 and symbol == board[xnou * 7 + ynou]:
                count += 1
                xnou -= row[i]
                ynou -= col[i]
            if count > 3:
                return True
        return False


