"""
    Entity class should be coded here
"""

class Book:
    """
    Represent one book
    """

    def __init__(self, isbn, author, title):
        """
        create book
        :param isbn:
        :param author:
        :param title:
        Raises Exception on incorrect data
        """
        self._isbn = isbn
        self._author = author
        self._title = title

    @property
    def isbn(self):
        return self._isbn





    @property
    def author(self):
        return self._author

    @property
    def title(self):
        return self._title

    def tostring(self):
        return 'isbn:' + str(self._isbn) + ', author:' + str(self._author) + ', title: ' + str(self._title)


def test_book():
    b = Book('2341', 'John Green', 'Paper Towns')
    assert '2341' == b.isbn
    assert b.title == 'Paper Towns'
    assert b.author == 'John Green'



test_book()
