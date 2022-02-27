from src.domain.book import CustomException
from src.struct.iterable_structure import IterableStructure


class ClientRepo:
    """
    Functionalities for clients
    """

    def __init__(self):
        self._clients = IterableStructure()

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

        for i in range(len(self.clients)):
            if self.clients[i] == x:
                pos = i
        del self.clients[pos]
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
