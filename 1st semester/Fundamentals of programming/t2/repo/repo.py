from domain.player import Player


class Repo:
    def __init__(self):
        self._players = []

    def read_text_file(self, file_name):
        result = []
        try:
            f = open(file_name, "r")
            line = f.readline().strip()
            while len(line) > 0:
                line = line.split(",")
                result.append(Player(int(line[0]), line[1], line[2]))
                line = f.readline().strip()
            f.close()
        except IOError as e:
            """
                Here we 'log' the error, and throw it to the outer layers 
            """
            print("An error occured - " + str(e))
            raise e

        return result


    @property
    def players(self):
        return self._players

    def add_player(self, player):
        self.players.append(player)

    def power_of_2(self, x):
        """
        verify if the len of the list is a power of 2
        :param x: the len
        :return: true if it is, false otherwise
        """
        y = 1
        while x > y  * 2:
            y = y * 2

        return y


def sort_list(_list, function):
    i = 1
    if function is None:
        return
    while i < len(_list):
        if i > 0 and not function(_list[i - 1], _list[i]):
            _list[i - 1], _list[i] = _list[i], _list[i - 1]
            i -= 1
        else:
            i += 1