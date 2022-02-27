from texttable import Texttable
import math
import random
from copy import deepcopy


class Board:
    def __init__(self):
        # Empty squares marked with 0
        self._data = [0] * 42

        # Count the number of moves
        self._moves = 0

    def get(self, col):
        """
        Return symbol on the collum col on board
        None     -> empty square
        'X', 'Y' -> symbols
        """
        return self._data[7 * 5 + col] == 0

    def move(self, col, symbol):
        """
        Verify if the move is valid and do it
        :param col: where do you want to make the move
        :param symbol: 'X' - player or 'Y' - computer
        :return:
        """
        _dict = {'X': 1, 'Y': -1}
        if col > 6 or col < 0 or self._data[7 * 5 + col] != 0:
            raise ValueError("Move not inside the board!")
        i = 0
        while i < 6:
            if self._data[7 * i + col] == 0:
                break
            i += 1

        self._data[7 * i + col] = _dict[symbol]
        self._moves += 1

    def winning_move(self, column):
        """
        verify iif the las move is a win
        :param column: where was the last move
        :return:
        """
        if column == -1:
            return False
        poz = 5
        while self._data[poz * 7 + column] == 0:
            poz -= 1
        symbol = self._data[poz * 7 + column]
        row = [1, 0, -1, 0, 1, -1, -1, 1]
        col = [0, 1, 0, -1, 1, 1, -1, -1]
        for i in range(8):
            count = 0
            # print(count)
            xnou = poz + row[i]
            ynou = column + col[i]
            xstart = xnou
            ystart = ynou
            while -1 < xnou < 6 and -1 < ynou < 7 and symbol == self._data[xnou * 7 + ynou]:
                count += 1
                xnou += row[i]
                ynou += col[i]
            xnou = xstart
            ynou = ystart
            count -= 1
            while -1 < xnou < 6 and -1 < ynou < 7 and symbol == self._data[xnou * 7 + ynou]:
                count += 1
                # print(count)
                xnou -= row[i]
                ynou -= col[i]
            if count > 3:
                return True
        return False

    def have_player_won(self, col):
        if self.winning_move(col):
            return True
        return False

    def is_tie(self, col):
        return self.winning_move(col) is False and self._moves == 42

    def __str__(self):
        t = Texttable()
        l = []
        d = {-1: 'Y', 0: ' ', 1: 'X'}
        for i in range(0, 42, 7):
            row = self._data[i:i + 7]
            for j in range(7):
                row[j] = d[row[j]]
            l.append(row)
        s = len(l) - 1
        while s > -1:
            t.add_row(l[s])
            s -= 1
        return t.draw()

