from src.domain.client import Client
from src.repository.client_repo import ClientRepo


class ClientTextRepo(ClientRepo):
    def __init__(self, file_name):
        ClientRepo.__init__(self)
        self._file_name = file_name
        self.load_file()

    def load_file(self):
        """
        read data from a file and add it to the repository
        """
        try:
            f = open(self._file_name, "r")
            line = f.readline().strip()
            while len(line) > 0:
                line = line.split(",")
                ClientRepo.add_client(self, Client(int(line[0]), line[1]))
                line = f.readline().strip()
            f.close()
        except IOError as e:
            """
                Here we 'log' the error, and throw it to the outer layers 
            """
            print("An error occured - " + str(e))

    def store(self):
        """
        stores to the file data
        :return:
        """
        try:
            f = open(self._file_name, "w")
        except IOError as e:
            print("An error occurred -" + str(e))

        for client in self.clients:
            c = str(client.client_id) + ", "
            c += str(client.name) + "\n"
            f.write(c)

    def update_client(self, client_id, op, value):
        ClientRepo.update_client(self, client_id, op, value)
        self.store()

    def add_client(self, client):
        ClientRepo.add_client(self, client)
        self.store()

    def remove_client(self, client_id):
        client = ClientRepo.remove_client(self, client_id)
        self.store()
        return client




