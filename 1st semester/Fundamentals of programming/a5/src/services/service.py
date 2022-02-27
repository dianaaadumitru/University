"""
    Service class includes functionalities for implementing program features
"""


from src.domain.entity import Book


class Service:
    """
    Functionalities for books
    """

    def __init__(self):
        self._books = []
        self._history = []

    @property
    def books(self):
        return self._books


    def update_list(self, list):
        self._books = list.copy()



    @property
    def history(self):
        return self._history

    def find_by_isbn(self, book):
        """
        verify if the isbn of a given book already exists
        :param book: the given book
        :return: True if it exists, False otherwise
        """
        for b in self.books:
            if b.isbn == book.isbn:
                return True
        return False

    def add_book(self, book):
        """
        Add a book
        :param book: the new book
        :return: -
        Raise BookException on error
        """
        if self.find_by_isbn(book):
            raise ValueError("ISBN already exists")
        self.books.append(book)

    def remove_book(self, name):
        """
        Remove the books which titles start with a given word
        :param name: the word we are searching for to delete
        :return: -
        """
        for i in range(len(self.books) - 1, -1, -1):
            _name = self.books[i].title.split()
            if _name[0] == name:
                self.books.pop(i)

    def __len__(self):
        return len(self.books)

    def add_history(self):
        """
        Saves all the lists of books and how are they modified
        :return: -
        """
        list = self.books.copy()
        self.history.append(list)

    def undo(self):
        self.history.pop()

    def len_h(self):
        return len(self.history)


def test_service():
    s = Service()
    assert len(s) == 0
    s.add_book(Book('2341', 'John Green', 'Paper Towns'))
    s.add_history()
    assert len(s) == 1 and s.len_h() == 1

    s.undo()
    assert s.len_h() == 0

    try:
        s.add_book(Book('2341', 'Sarah J. Maas', 'A Court of Thorns and Roses'))
        assert False
    except ValueError:
        assert True
    assert len(s) == 1

    s.remove_book('Paper')
    assert len(s) == 0




test_service()
