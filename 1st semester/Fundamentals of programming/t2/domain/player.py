class Player:
    """
    represent a player
    """
    def __init__(self, player_id, name, strength):
        """
        create a player
        :param player_id:
        :param name:
        :param strength:
        """
        self._player_id = player_id
        self._name = name
        self._strength = strength

    @property
    def player_id(self):
        return self._player_id

    @property
    def name(self):
        return self._name

    @property
    def strength(self):
        return self._strength

    @strength.setter
    def strength(self, value):
        self._strength = value

    def update_strength(self):
        self.strength = self.strength + 1

    def tostring(self):
        return str(self.player_id) + ', ' + str(self.name) + ', ' + str(self.strength)
