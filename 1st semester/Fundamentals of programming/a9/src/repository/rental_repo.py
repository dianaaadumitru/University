from src.domain.book import CustomException
from src.repository.book_repo import BookRepo
from src.repository.client_repo import ClientRepo


class RentalRepo:
    """
    Functionalities for rentals
    """
    def __init__(self):
        self._book = BookRepo()
        self._rentals = []
        self._client = ClientRepo()

    @property
    def client(self):
        return self._client

    @property
    def book(self):
        return self._book

    @property
    def rentals(self):
        return self._rentals

    def add_rental(self, rental):
        self.rentals.append(rental)

    def remove_rental(self, rental_id):
        """
        Remove the object with given objectId from repository
        rental_id - The objectId that will be removed
        Returns the object that was removed
        """
        x = self.find_by_rental_id(rental_id)
        if x is None:
            raise CustomException("")
        self.rentals.remove(x)
        return x

    def find_by_book_id(self, _book_id):
        """
        Verify if a book is already rented
        :param _book_id: the rental you want to do
        :return: the position if the book is already rented, -1 otherwise
        """
        for i in range(len(self.rentals)):
            if self.rentals[i].book_id == _book_id and self.rentals[i].returned_date == '':
                return i
        return -1

    def find_by_rental_id(self, _rental_id):
        """
        Verify if a book is already rented
        :param _rental_id: the rental you want to do
        :return: the book if the book exists, None otherwise
        """
        for r in self.rentals:
            if r.rental_id == _rental_id:
                return r
        return None

    def rent_book(self, rental):
        """
        Rent a book
        :param rental: The client that wants to rent the book
        :return: -
        """
        self.rentals.append(rental)

    def return_book(self, ren_id, ret_date):
        """
        returns a book
        :param ren_id: the book returned
        :param ret_date: the date of return
        :return:
        """
        x = self.find_by_rental_id(ren_id)
        x.returned_date = ret_date

    # @staticmethod
    # def update_rental(rental, book, client, value):
    #     if book is not None:
    #         rental.book_id = value
    #     if client is not None:
    #         rental.client_id = value
