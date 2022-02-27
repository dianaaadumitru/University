from src.domain.rental import Rental
from src.repository.rental_repo import RentalRepo

class RentalTextRepo(RentalRepo):
    def __init__(self, file_name):
        RentalRepo.__init__(self)
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
                self.add_rental(Rental(line[0], line[1], line[2], line[3], line[4]))
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

        for rental in self.rentals:
            r = str(rental.rental_id) + ', '
            r += str(rental.book_id) + ', '
            r += str(rental.client_id) + ', '
            r += str(rental.rented_date) + ', '
            r += str(rental.returned_date) + '\n'
            f.write(r)

    def add_rental(self, rental):
        RentalRepo.add_rental(self, rental)
        self.store()

    def remove_rental(self, rental_id):
        RentalRepo.remove_rental(self, rental_id)
        self.store()

    def rent_book(self, rental):
        RentalRepo.rent_book(self, rental)
        self.store()

    def return_book(self, ren_id, ret_date):
        RentalRepo.return_book(self, ren_id, ret_date)
        self.store()
