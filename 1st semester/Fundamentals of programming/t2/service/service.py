from repo.repo import Repo


class Service:
    def __init__(self):
        self._player = Repo()

    @property
    def player(self):
        return self._player

    def qualifications(self):
        """"
        for qualifications we save in another list the last players sorted by strength
        """
        ok = 0
        result = []
        a = self.player.read_text_file("C:\\Users\\diana\\Documents\\GitHub\\t2-913-Dumitru-Diana\\ui\\players.txt")
        l = len(a)
        if l != self.player.power_of_2(l):
            ok = 1

        if ok:
            p = abs(self.player.power_of_2(l) - l)

        for x in range(len(a) - p*2, len(a), 1):
            result.append(x)

        return ok, result
