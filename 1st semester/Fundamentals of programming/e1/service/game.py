from domain.board import Board


class Game:
    def __init__(self, board):
        self._board = board

    @property
    def board(self):
        return self._board

    def find_head_snake(self):
        x = 0
        y = 0
        for i in range(self._board._dim):
            for j in range(self._board._dim):
                if self._board._data[i][j] == 1:
                    x = i
                    y = j

        return x, y

    def move(self, n):
        ok = 1
        if n == ' ':
            n = 1
        n = int(n)
        initial_n = n
        x, y = self.find_head_snake()

        nr_apples = 0
        length = self._board.len_snake()
        initial_x = x
        if n > x:
            ok = 0
            return ok

        while n >= 1:
            for i in range(x, x + length):
                if self._board._data[i - 1][y] == 3:
                    nr_apples += 1
                if self._board._data[i - 1][y] == 2 and i == x:
                    ok = 0
                    return ok

                self._board._data[i - 1][y] = self._board._data[i][y]
            x -= 1
            n -= 1

        for i in range(initial_x+length-initial_n, initial_n+initial_x + length):
            self._board._data[i][y] = 0

        for i in range(initial_x+length-initial_n, initial_n+initial_x + nr_apples-1):
            self._board._data[i][y] = 2

        self._board.place_apple(nr_apples)

        return ok

    def move_after_left(self, n):
        ok = 1
        if n == ' ':
            n = 1
        n = int(n)
        initial_n = n
        x, y = self.find_head_snake()

        nr_apples = 0
        length = self._board.len_snake()
        initial_x = x

        if y == 0:
            ok = 0
            return ok

        for i in range(y-1, y+n):
            if self._board._data[x][i-1] == 3:
                nr_apples += 1
            if self._board._data[x][i-1] == 2 and i == x:
                ok = 0
                return ok

            self._board._data[x][i] = self._board._data[x][i+1]
        length = self._board.len_snake()
        for i in range(y, y + length-1):
            length = self._board.len_snake()
            self._board._data[x+1][i+1] = 0

        for i in range(initial_x + length - initial_n, initial_n + initial_x + nr_apples - 1):
            self._board._data[i][y] = 2

        self._board.place_apple(nr_apples)

        return ok

    def left(self):
        x, y = self.find_head_snake()
        ok = 1
        if y == 0:
            ok = 0
            return ok
        nr_apples = 0
        length = self._board.len_snake()
        if self.board._data[x][y - 1] == 3:
            nr_apples = 1
        self.board._data[x][y-1] = 1
        if nr_apples == 0:
            for i in range(x+1, x+length-1):
                if self._board._data[i - 1][y] == 2 and i == x:
                    ok = 0
                    return ok

                self._board._data[i - 1][y] = self._board._data[i][y]
            self._board._data[x+length-1][y] = 0
        else:
            self._board._data[x][y] = 2
            self._board.place_apple(1)

        return ok

    def move_after_right(self, n):
        ok = 1
        if n == ' ':
            n = 1
        n = int(n)
        initial_n = n
        x, y = self.find_head_snake()

        nr_apples = 0
        length = self._board.len_snake()
        initial_x = x

        if y == length - 1:
            ok = 0
            return ok
        if self._board._data[x][y+n] == 3:
            nr_apples = 1
        self._board._data[x][y+n] = 1
        for i in range(y-1, y + n-1):
            self._board._data[x][i+1] = 2
        self._board._data[x + length - 2][y] = 0
        # length = self._board.len_snake()
        # for i in range(y, y + length - 1):
        #     length = self._board.len_snake()
        #     self._board._data[x + 1][i + 1] = 0
        #
        # for i in range(initial_x + length - initial_n, initial_n + initial_x + nr_apples - 1):
        #     self._board._data[i][y] = 2
        #
        # self._board.place_apple(nr_apples)
        if nr_apples == 1:
            self._board.place_apple(1)
        return ok

    def right(self):
        x, y = self.find_head_snake()
        ok = 1
        length = self._board.len_snake()
        if y == length-1:
            ok = 0
            return ok
        nr_apples = 0

        if self.board._data[x][y + 1] == 3:
            nr_apples = 1
        self.board._data[x][y + 1] = 1
        if nr_apples == 0:
            for i in range(x + 1, x + length - 1):
                if self._board._data[i - 1][y] == 2 and i == x:
                    ok = 0
                    return ok

                self._board._data[i - 1][y] = self._board._data[i][y]
            self._board._data[x + length - 1][y] = 0
        else:
            self._board._data[x][y] = 2
            self._board.place_apple(1)

        return ok




# b = Board(7, 10)
# b.place_snake()
# b.place_apple(b._apple_count)
# g = Game(b)
# print(b)
# g.move(2)
# print(b)