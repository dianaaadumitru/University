from src.domain.book import CustomException


class BookRepo:
    """
    Functionalities for books
    """

    def __init__(self):
        self._books = []

    @property
    def books(self):
        return self._books

    def __len__(self):
        return len(self.books)

    def add_book(self, book):
        """
        Add a book
        :param book: the new book
        :return: -
        """
        self.books.append(book)

    def remove_book(self, book_id):
        """
        Remove the object with given objectId from repository
        book_id - The objectId that will be removed
        Returns the object that was removed
        """
        x = self.find_by_book_id(book_id)
        if not x:
            raise CustomException("book_id does not exist")
        self.books.remove(x)
        return x

    def update_book(self, book_id, op, value):
        """
        Updates a book and it verifies if it exist the id in the list
        :param book_id: the book we are searching for
        :param op: the user's option
        :param value: the updated version
        :return:
        """
        x = self.find_by_book_id(book_id)
        if x is not None:
            if op == '1':
                x.update_id(value)
            elif op == '2':
                x.update_author(value)
            else:
                x.update_title(value)
        return x

    def find_by_book_id(self, _book_id):
        """
        Verify if a book is already rented
        :param _book_id: the rental you want to do
        :return: the book if the book exists, None otherwise
        """
        for b in self.books:
            if b.book_id == _book_id:
                return b
        return None
