import pickle
from src.repository.client_repo import ClientRepo


class ClientPickleRepo(ClientRepo):
    def __init__(self, file_name):
        super().__init__()
        self._file_name = file_name
        self.load_from_binary()

    def load_from_binary(self):
        file = open(self._file_name, "rb")
        try:
            clients = pickle.load(file)
        except EOFError:
            clients = []
        file.close()

        for client in clients:
            super().add_client(client)

    def save_to_binary(self):
        file = open(self._file_name, "wb")
        pickle.dump(super().clients, file)
        file.close()

    def add_client(self, client):
        super().add_client(client)
        self.save_to_binary()

    def remove_client(self, client_id):
        client = super().remove_client(client_id)
        self.save_to_binary()
        return client

    def update_client(self, client_id, op, value):
        super().update_client(client_id, op, value)
        self.save_to_binary()
