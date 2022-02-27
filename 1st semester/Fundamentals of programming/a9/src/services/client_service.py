from src.domain.client import Client
from src.domain.book import CustomException
from src.services.undo_service import FunctionCall, Operation, CascadedOperation


class ClientService:
    """
    Validation for client functionalities
    """

    def __init__(self, undo_service, rental_service, client_repo):
        self._client = client_repo
        self._rental_service = rental_service
        self._undo_service = undo_service

    @property
    def client(self):
        return self._client

    @property
    def rental(self):
        return self._rental_service

    def create(self, client_id, name):
        client = Client(client_id, name)
        self.client.add_client(client)
        return client

    def add_client(self, client):
        """
        Add a client
        :param client: the new client
        :return: -
        Raise CustomException if the id already exists
        """
        if self.client.find_by_client_id(client.client_id) is not None:
            raise CustomException("Client_id already exists")
        self.client.add_client(client)
        undo = FunctionCall(self.client.remove_client, client.client_id)
        redo = FunctionCall(self.client.add_client, client)
        op = Operation(undo, redo)

        self._undo_service.record(op)

    def verif_data(self, client_id, name):
        """
        Verifies if the input is correct
        :param client_id:
        :param name:
        :return: -
        """
        if len(client_id) == 0:
            raise CustomException("Client_id not given")

        if len(name) == 0:
            raise CustomException("name not given")

    def remove_client(self, client_id):
        """
        Remove a certain book from the list
        :param client_id: the given book
        :return: -
        """
        x = self.client.find_by_client_id(client_id)
        if x is None:
            raise CustomException("the client does not exist")
        client = self.client.remove_client(client_id)
        undo = FunctionCall(self.client.add_client, Client(client.client_id, client.name))
        redo = FunctionCall(self.client.remove_client, client.client_id)
        op = Operation(undo, redo)

        '''
        2. Delete their rentals
        NB! This implementation is not transactional, i.e. the two delete operations are performed separately
        '''
        # TODO Also cascade the rentals !!!
        rentals = self.rental.filter_rentals(client.client_id, None)
        for rent in rentals:
            self._rental_service.remove_rental(rent.rental_id)

        # Record for cascaded undo/redo
        cascade_list = [op]
        for rent in rentals:
            undo = FunctionCall(self._rental_service.create, rent.rental_id, rent.book_id, rent.client_id, rent.rented_date, rent.returned_date)
            redo = FunctionCall(self._rental_service.rental.remove_rental, rent.rental_id)
            cascade_list.append(Operation(undo, redo))

        cop = CascadedOperation(*cascade_list)
        self._undo_service.record(cop)

    def update_client(self, client_id, op, value):
        """
       Updates a book and it verifies if it exist the id in the list
       :param client_id: the client we are searching for
       :param op: the user option
       :param value: the updated version
       :return:
        """
        x = self.client.find_by_client_id(client_id)
        if x is None:
            raise CustomException("This client does not exist")
        if op == '1':
            val2 = x.client_id
        else:
            val2 = x.name
        id2 = x.client_id
        self.client.update_client(client_id, op, value)
        undo = FunctionCall(self.client.update_client, x.client_id, op, val2)
        redo = FunctionCall(self.client.update_client, id2, op, value)
        op = Operation(undo, redo)
        self._undo_service.record(op)

        # rentals = self.rental.filter_rentals(id2, None)
        # for rent in rentals:
        #     self._rental_service.rental.update_rental(rent, None, rent.client_id, x.client_id)
        #
        # cascade_list = [op]
        # for rent in rentals:
        #     undo = FunctionCall(self._rental_service.rental.update_rental, rent, None, rent.client_id, id2)
        #     redo = FunctionCall(self._rental_service.rental.update_rental, rent, None, rent.client_id, x.client_id)
        #     cascade_list.append(Operation(undo, redo))
        #
        # cop = CascadedOperation(*cascade_list)
        # self._undo_service.record(cop)

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
            for client in self.client.clients:
                if client.client_id.find(value) != -1:
                    _list.append(client.tostring())
                    ok = 1
        elif op == '2':
            for client in self.client.clients:
                if client.name.find(value) != -1:
                    _list.append(client.tostring())
                    ok = 1

        else:
            for client in self.client.clients:
                if client.client_id.find(value) != -1 and self.find_in_list(_list, client.tostring()) == -1:
                    _list.append(client.tostring())
                    ok = 1
            for client in self.client.clients:
                if client.name.find(value) != -1 and self.find_in_list(_list, client.tostring()) == -1:
                    _list.append(client.tostring())
                    ok = 1

        if ok == 0:
            raise CustomException("No result!")

        return _list

    def client_id_name(self):
        """
        Create a list that contains the client ids and the names
        :return: the list
        """
        _list = []

        for c in self.client.clients:
            _list.append(c.client_id)
            _list.append(c.name)
        return _list

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