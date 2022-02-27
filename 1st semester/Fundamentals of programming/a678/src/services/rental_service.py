from datetime import date
from src.domain.rental import Rental
from src.domain.book import CustomException
from src.services.undo_service import FunctionCall, Operation


class RentalService:
    """
    Validation for Rental functionalities
    """
    def __init__(self, undo_service, rental_repo, book_repo, client_repo):
        self._rental = rental_repo
        self._book = book_repo
        self._client = client_repo
        self._undo_controller = undo_service

    @property
    def undo_controller(self):
        return self._undo_controller

    @property
    def book(self):
        return self._book

    @property
    def client(self):
        return self._client

    @property
    def rental(self):
        return self._rental

    def create(self, rental_id, book_id, client_id, rented_date, returned_date):
        rental = Rental(rental_id, book_id, client_id, rented_date, returned_date)
        self.rental.add_rental(rental)

    def rent_book(self, ren):
        """
        Rent a book
        :param ren: The client that wants to rent the book
        :return: -
        Raise CustomError if the book is already rented or the book does not exist
        """
        if self.rental.find_by_rental_id(ren.rental_id) is not None:
            raise CustomException("This book is already rented")

        self.rental.rent_book(ren)
        undo = FunctionCall(self.remove_rental, ren.rental_id)
        redo = FunctionCall(self.rental.add_rental, ren)
        op = Operation(undo, redo)
        self.undo_controller.record(op)

    def remove_rental(self, rental_id):
        rental = self.rental.remove_rental(rental_id)
        return rental

    def return_book(self, rental_id, ret_date):
        """
        returns a book
        :param rental_id: the book returned
        :param ret_date: the date of return
        :return:
        Raises CustomException if the book cannot be found
        """
        if self.rental.find_by_rental_id(rental_id) is None:
            raise CustomException("this book is not rented")

        self.rental.return_book(rental_id, ret_date)
        undo = FunctionCall(self.rental.delete_ret_date, rental_id)
        redo = FunctionCall(self.rental.return_book, rental_id, ret_date)
        op = Operation(undo, redo)
        self.undo_controller.record(op)

    def verif_list(self):
        if len(self.rental.rentals) == 0:
            raise CustomException("No rentals yet!")

    @staticmethod
    def diff_dates(date1, date2):
        return abs(date2 - date1).days

    def most_rented_books(self, _list):
        """
        The list of most rented books
        :return: the list
        """
        result = []

        # Compute stat using an interim dictionary
        rentals_dict = {}

        for rental in self.rental.rentals:
            if rental.book_id not in rentals_dict:
                rentals_dict[rental.book_id] = 0
            rentals_dict[rental.book_id] += 1

        # Move data to sorted list of values
        for entry in rentals_dict:
            a = self.book.find_in_list(_list, entry)
            result.append(BookRentalDays(entry, _list[a + 1], _list[a + 2], rentals_dict[entry]))
        # Sort the list
        # lambda <parameter list> : <function body>
        result.sort(key=lambda x: x.rental_times, reverse=True)
        return result

    def most_active_clients(self, _list):
        """
        The list of most active clients
        :return: the list
        """
        result = []

        # Compute stat using an interim dictionary
        rentals_dict = {}

        for rental in self.rental.rentals:
            if rental.client_id not in rentals_dict:
                rentals_dict[rental.client_id] = 0
            d1, d2, d3 = rental.get_date(rental.rented_date)
            d4, d5, d6 = rental.get_date(rental.returned_date)
            ren_day = date(int(d3), int(d2), int(d1))
            ret_day = date(int(d6), int(d5), int(d4))
            d = self.diff_dates(ren_day, ret_day)
            rentals_dict[rental.client_id] += d

        # Move data to sorted list of values
        for entry in rentals_dict:
            a = self.client.find_in_list(_list, entry)
            result.append(ActiveClients(entry, _list[a + 1], rentals_dict[entry]))
        # Sort the list
        # lambda <parameter list> : <function body>
        result.sort(key=lambda x: x.rental_days, reverse=True)
        return result

    def most_rented_author(self, _list):
        """
        The list of most rented author
        :return: the list
        """
        result = []

        # Compute stat using an interim dictionary
        author_dict = {}

        for rental in self.rental.rentals:
            a = self.book.find_in_list(_list, rental.book_id)
            if a != -1:
                if _list[a + 1] not in author_dict:
                    author_dict[_list[a + 1]] = 0
                author_dict[_list[a + 1]] += 1
        # Move data to sorted list of values
        for entry in author_dict:
            result.append(MostRentedAuthor(entry, author_dict[entry]))
        # Sort the list
        # lambda <parameter list> : <function body>
        result.sort(key=lambda x: x.rental_times, reverse=True)
        return result

    def filter_rentals(self, client, book):
        """
        Return a list of rentals performed by the provided client for the provided car
        client - The client performing the rental. None means all clients
        cars - The rented car. None means all cars
        """
        result = []
        for rental in self.rental.get_all():
            if client is not None and rental.client_id != client:
                continue
            if book is not None and rental.book_id != book:
                continue
            result.append(rental)
        return result

    def delete_rental(self, rental_id):
        rental = self.rental.remove_rental(rental_id)
        return rental


class MostRentedAuthor:
    """
    Data Transfer Object for statistics
    SRP: Move data between application layers
    """

    def __init__(self, author, rental_times):
        self._author = author
        self._rental_times = rental_times

    @property
    def author(self):
        return self._author

    @property
    def rental_times(self):
        return self._rental_times

    def __str__(self):
        return str(self.author) + ' - ' + str(self.rental_times) + ' time(s)'


class BookRentalDays:
    """
    Data Transfer Object for statistics
    SRP: Move data between application layers
    """

    def __init__(self, book_id, title, author, book_rental_times):
        self._book_id = book_id
        self._book_rental_times = book_rental_times
        self._title = title
        self._author = author

    @property
    def title(self):
        return self._title

    @property
    def author(self):
        return self._author

    @property
    def book_id(self):
        return self._book_id

    @property
    def rental_times(self):
        return self._book_rental_times

    def __str__(self):
        return str(self.book_id) + ': ' + str(self.title) + ' by ' + str(self.author) + ' - ' + str(
            self.rental_times) + ' time(s)'


class ActiveClients:
    """
    Data Transfer Object for statistics
    SRP: Move data between application layers
    """

    def __init__(self, client_id, name, rental_days):
        self._client_id = client_id
        self._rental_days = rental_days
        self._name = name

    @property
    def name(self):
        return self._name

    @property
    def client_id(self):
        return self._client_id

    @property
    def rental_days(self):
        return self._rental_days

    def __str__(self):
        return str(self.client_id) + ': ' + str(self.name) + ' - ' + str(self.rental_days) + ' day(s)'