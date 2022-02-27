from src.domain.book import CustomException


class ClientRepo:
    """
    Functionalities for clients
    """

    def __init__(self):
        self._clients = []

    @property
    def clients(self):
        return self._clients

    def __len__(self):
        return len(self.clients)

    def add_client(self, client):
        """
        Add a client
        :param client: the new client
        :return: -
        """
        self.clients.append(client)

    def remove_client(self, client_id):
        """
        Remove a certain book from the list
        :param client_id: the given book
        :return: -
        """
        x = self.find_by_client_id(client_id)
        if not x:
            raise CustomException("client_id does not exist")
        self.clients.remove(x)
        return x

    def update_client(self, client_id, op, value):
        """
        Updates a book and it verifies if it exist the id in the list
        :param client_id: the client we are searching for
        :param op: the user option
        :param value: the updated version
        :return:
        """
        x = self.find_by_client_id(client_id)
        if x is not None:
            if op == '1':
                x.update_id(value)
            else:
                x.update_name(value)

    def find_by_client_id(self, _client_id):
        """
        Verify if a book is already rented
        :param _client_id: the client you search
        :return: the client if the book is already rented, None otherwise
        """
        for c in self.clients:
            if c.client_id == _client_id:
                return c
        return None

    def search(self, op, value):
        """
        search fot something given by the user
        :param op: the user's option
        :param value: what they search for
        :return:
        """
        _list = []
        ok = 0
        if op == '1':
            for client in self.clients:
                if client.client_id.find(value) != -1:
                    _list.append(client.tostring())
                    ok = 1
        elif op == '2':
            for client in self.clients:
                if client.name.find(value) != -1:
                    _list.append(client.tostring())
                    ok = 1

        else:
            for client in self.clients:
                if client.client_id.find(value) != -1 and self.find_in_list(_list, client.tostring()) == -1:
                    _list.append(client.tostring())
                    ok = 1
            for client in self.clients:
                if client.name.find(value) != -1 and self.find_in_list(_list, client.tostring()) == -1:
                    _list.append(client.tostring())
                    ok = 1

        return ok, _list

    @staticmethod
    def find_in_list(_list, value):
        """
        find an element in a list
        :param _list: -
        :param value: the searched element
        :return: the position if the element exists, -1 otherwise
        """
        for i in range(len(_list)):
            if _list[i] == value:
                return i
        return -1

    def client_id_name(self):
        """
        Create a list that contains the client ids and the names
        :return: the list
        """
        _list = []

        for c in self.clients:
            _list.append(c.client_id)
            _list.append(c.name)
        return _list
