from src.domain.book import CustomException, Book
from src.services.undo_service import FunctionCall, Operation, CascadedOperation


class BookService:
    """
    Validation of book functionalities
    """
    def __init__(self, undo_service, rental_service, book_repo):
        self._book = book_repo
        self._rental_service = rental_service
        self._undo_service = undo_service

    @property
    def book(self):
        return self._book

    @property
    def rental(self):
        return self._rental_service

    def create(self, book_id, book_author, book_title):
        book = Book(book_id, book_author, book_title)
        self.book.add_book(book)
        return book

    def add_book(self, book):
        """
        Add a book
        :param book: the new book
        :return: -
        Raise BookException if the id already exists
        """
        if self.book.find_by_book_id(book.book_id) is not None:
            raise CustomException("Book_id already exists")
        self.book.add_book(book)
        undo = FunctionCall(self.book.remove_book, book.book_id)
        redo = FunctionCall(self.book.add_book, book)
        op = Operation(undo, redo)

        self._undo_service.record(op)

    def verif_data(self, book_id, author, title):
        """
        Verifies if the input is correct
        :param book_id:
        :param author:
        :param title:
        :return: -
        """
        if len(book_id) == 0:
            raise CustomException("Book_id not given")

        if len(author) == 0:
            raise CustomException("author not given")

        if len(title) == 0:
            raise CustomException("title not given")

    def remove_book(self, book_id):
        """
        Remove a certain book from the list
        :param book_id: the given book
        :return: -
        """
        x = self.book.find_by_book_id(book_id)
        if x is None:
            raise CustomException("the book does not exist")
        book = self.book.remove_book(book_id)
        undo = FunctionCall(self.create, book.book_id, book.author, book.title)
        redo = FunctionCall(self.book.remove_book, book.book_id)
        op = Operation(undo, redo)

        '''
        2. Delete their rentals
        NB! This implementation is not transactional, i.e. the two delete operations are performed separately
        '''
        # TODO Also cascade the rentals !!!!
        rentals = self.rental.filter_rentals(None, book.book_id)
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

    def update_book(self, book_id, op, value):
        """
        Updates a book and it verifies if it exist the id in the list
        :param book_id: the book we are searching for
        :param op: the user option
        :param value: the updated version
        :return:
        """
        x = self.book.find_by_book_id(book_id)
        if x is None:
            raise CustomException("This book does not exist")
        if op == '1':
            val2 = x.book_id
        elif op == '2':
            val2 = x.author
        else:
            val2 = x.title
        id2 = x.book_id
        self.book.update_book(book_id, op, value)
        undo = FunctionCall(self.book.update_book, x.book_id, op, val2)
        redo = FunctionCall(self.book.update_book, id2, op, value)
        op = Operation(undo, redo)

        rentals = self.rental.filter_rentals(None, id2)
        for rent in rentals:
            self._rental_service.rental.update_rental(rent, rent.book_id, None, x.book_id)

        cascade_list = [op]
        for rent in rentals:
            undo = FunctionCall(self._rental_service.rental.update_rental, rent, rent.book_id, None, id2)
            redo = FunctionCall(self._rental_service.rental.update_rental, rent, rent.book_id, None, x.book_id)
            cascade_list.append(Operation(undo, redo))

        cop = CascadedOperation(*cascade_list)
        self._undo_service.record(cop)

    def search(self, op, value):
        """
        search for something given by the user
        :param op: the user's option
        :param value: what they search for
        :return:
        """
        ok, _list = self.book.search(op, value)
        if ok == 0:
            raise CustomException("No result!")
        return _list

    def author_book_id(self):
        return self.book.author_book_id()

    def book_id_title_author(self):
        return self.book.book_id_title_author()
