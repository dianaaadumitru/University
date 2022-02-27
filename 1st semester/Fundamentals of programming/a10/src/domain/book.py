class CustomException(Exception):
    def __init__(self, msg):
        self._msg = msg


class Book:
    """
    Represent one book
    """

    def __init__(self, book_id, author, title):
        """
        create book
        :param book_id:
        :param author:
        :param title:
        """
        self._book_id = book_id
        self._author = author
        self._title = title

    @property
    def book_id(self):
        return self._book_id

    @property
    def author(self):
        return self._author

    @property
    def title(self):
        return self._title

    def tostring(self):
        return 'book_id:' + str(self.book_id) + ', author:' + str(self._author) + ', title: ' + str(self._title)

    def update_id(self, id):
        self.book_id = id

    def update_author(self, author):
        self.author = author

    def update_title(self, title):
        self.title = title

    @author.setter
    def author(self, value):
        self._author = value

    @title.setter
    def title(self, value):
        self._title = value

    @book_id.setter
    def book_id(self, value):
        self._book_id = value
