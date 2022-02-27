from texttable import Texttable
import random


class Board:
    def __init__(self, dim, apple_count):
        """
        0 - empty cell
        1 - head of snake
        2 - body of snake
        3 - apples
        :param dim:
        """
        self._dim = dim
        self._apple_count = apple_count
        self._data = [[0 for x in range(self._dim)] for y in range(self._dim)]

    def pos_apple(self):
        possible_sol = []
        for i in range(1, self._dim - 1):
            for j in range(1, self._dim - 1):
                l = []
                if self._data[i-1][j] == 0 and self._data[i+1][j] == 0 and self._data[i][j] == 0:
                    l.append(i)
                    l.append(j)
                    possible_sol.append(l)

        for j in range(1, self._dim - 1):
            for i in range(self._dim):
                l = []
                if self._data[i][j - 1] == 0 and self._data[i][j + 1] == 0 and self._data[i][j] == 0:
                    l.append(i)
                    l.append(j)
                    possible_sol.append(l)

        rez = random.randint(0, len(possible_sol) - 1)
        return possible_sol[rez]

    def place_apple(self, how_many):
        count = 0
        while count < how_many:
            l = self.pos_apple()
            x = l[0]
            y = l[1]
            self._data[x][y] = 3
            count += 1

    def place_snake(self):
        col = self._dim // 2
        mid_row = self._dim // 2
        self._data[mid_row][col] = 2
        self._data[mid_row+1][col] = 2
        self._data[mid_row-1][col] = 1

    def len_snake(self):
        length = 0
        for i in range(self._dim):
            for j in range(self._dim):
                if self._data[i][j] == 1 or self._data[i][j] == 2:
                    length += 1

        return length

    @staticmethod
    def game_over(ok):
        if ok:
            return True
        return False

    def __str__(self):
        t = Texttable()
        d = {0:' ', 1: '*', 2: '+', 3: '.'}
        for i in range(self._dim):
            row = []
            for j in range(self._dim):
                row.append((d[self._data[i][j]]))

            t.add_row(row)

        return t.draw()


# b = Board(7, 10)
# b.place_apple()
# b.place_snake()
# print(b)

