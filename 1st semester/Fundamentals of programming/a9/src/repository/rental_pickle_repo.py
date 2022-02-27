import pickle
from src.repository.rental_repo import RentalRepo


class RentalPickleRepo(RentalRepo):
    def __init__(self, file_name):
        super().__init__()
        self._file_name = file_name
        self.load_from_binary()

    def load_from_binary(self):
        file = open(self._file_name, "rb")
        try:
            rentals = pickle.load(file)
        except EOFError:
            rentals = []
        file.close()

        for rental in rentals:
            super().add_rental(rental)

    def save_to_binary(self):
        file = open(self._file_name, "wb")
        pickle.dump(super().rentals, file)
        file.close()

    def add_rental(self, rental):
        super().add_rental(rental)
        self.save_to_binary()

    def remove_rental(self, rental_id):
        super().remove_rental(rental_id)
        self.save_to_binary()

    def rent_book(self, rental):
        super().rent_book(rental)
        self.save_to_binary()

    def return_book(self, ren_id, ret_date):
        super().return_book(ren_id, ret_date)
        self.save_to_binary()
