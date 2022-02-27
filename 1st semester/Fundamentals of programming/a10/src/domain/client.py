class Client:
    """
    Represents a client
    """
    def __init__(self, client_id, name):
        """
        create client
        :param client_id:
        :param name:
        """
        self._client_id = client_id
        self._name = name

    @property
    def client_id(self):
        return self._client_id

    @property
    def name(self):
        return self._name

    def update_name(self, name):
        self.name = name

    def update_id(self, _id):
        self.client_id = _id

    def tostring(self):
        return 'client_id: ' + str(self.client_id) + ', name: ' + str(self.name)

    @name.setter
    def name(self, value):
        self._name = value

    @client_id.setter
    def client_id(self, value):
        self._client_id = value
