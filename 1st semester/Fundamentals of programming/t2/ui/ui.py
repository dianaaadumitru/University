from repo.repo import Repo, sort_list
from service.service import Service
import random


class UI:
    def __init__(self, repo):
        self.service = Service()

    def display(self):
        a = self.service.player.read_text_file("players.txt")
        sort_list(a, lambda x, y: x.strength > y.strength)

        for player in a:
            print(player.tostring())

    def match_ui(self):
        """
        choose randomly the players who are playing against w=each other
        :return:
        """
        a = self.service.player.read_text_file("players.txt")
        sort_list(a, lambda x, y: x.strength > y.strength)
        _list = []
        yz = self.service.player.power_of_2(len(a))
        ok, result = self.service.qualifications()
        nr = len(a) - len(result)
        for i in range(nr):
            _list.append(a[i])
        if nr == 0:
            print("no qualifications")
            _list = a
        if nr == 1:
            print("Qualifications: ")
            while len(result):
                x = random.randint(0, len(result) - 1)

                player_1 = a[result[x]]
                result.remove(result[x])
                y = random.randint(0, len(result) - 1)
                player_2 = a[result[y]]

                print("Choose the winner between: ")
                print("1: ", player_1.tostring())
                print("2: ", player_2.tostring())

                _list.append(player_1)
                _list.append(player_2)

                z = input("your choice: ")

                if z == '1':
                    value = int(player_1.strength) + 1
                    player_1.strength = value
                    _list.remove(player_2)

                else:
                    value = int(player_2.strength) + 1
                    player_2.strength = value
                    _list.remove(player_1)

                result.remove(result[y])
        for i in _list:
            print(i.tostring())
        if len(_list) == 8:
            print("\nQuarterfinals")
        elif len(_list) == 4:
            print("\nSemifinals")
        elif len(_list) == 2:
            print("\nFinals")

    @staticmethod
    def print_menu():
        s = 'Available commands:\n'
        s += '   1. Display\n'
        s += '   2. Match\n'
        s += '   0. Exit\n'
        print(s)

    def start(self):
        done = False
        command_dict = {'1': self.display, '2': self.match_ui}
        while not done:
            print()
            self.print_menu()
            nr = input("Enter the number of the operation you want: ")
            nr = nr.strip()

            if nr in command_dict:
                try:
                    command_dict[nr]()
                except Exception as valerr:
                    print(str(valerr))
            elif nr == '0':
                done = True
            else:
                print("Invalid input")


r = Repo()
ui = UI(r)
ui.start()
