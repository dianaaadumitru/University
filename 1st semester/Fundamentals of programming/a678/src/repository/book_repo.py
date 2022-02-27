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

    def search(self, op, value):
        """
        search fot something given by the user
        :param op: the user's option
        :param value: what they search for
        :return:
        """
        _list = []
        ok = 0
        if op == '1':
            for book in self.books:
                if book.book_id.find(value) != -1:
                    _list.append(book.tostring())
                    ok = 1
        elif op == '2':
            for book in self.books:
                if book.author.find(value) != -1:
                    _list.append(book.tostring())
                    ok = 1
        elif op == '3':
            for book in self.books:
                if book.title.find(value) != -1:
                    _list.append(book.tostring())
                    ok = 1
        else:
            for book in self.books:
                if book.book_id.find(value) != -1:
                    _list.append(book.tostring())
                    ok = 1
            for book in self.books:
                if book.author.find(value) != -1 and self.find_in_list(_list, book.tostring()) == -1:
                    _list.append(book.tostring())
                    ok = 1
            for book in self.books:
                if book.title.find(value) != -1 and self.find_in_list(_list, book.tostring()) == -1:
                    _list.append(book.tostring())
                    ok = 1

        return ok, _list

    def author_book_id(self):
        """
        Create a list that contains the book ids and the authors
        :return: the list
        """
        _list = []
        for b in self.books:
            _list.append(b.book_id)
            _list.append(b.author)
        return _list

    def book_id_title_author(self):
        """
        Create a list that contains the book ids, the titles and the authors
        :return: the list
        """
        _list = []
        for b in self.books:
            _list.append(b.book_id)
            _list.append(b.title)
            _list.append(b.author)
        return _list

    @staticmethod
    def find_in_list(_list, value):
        """
        find an element in a list
        :param _list: -
        :param value: the searched element
        :return: the position if the element exists, -1 otherwise
        """
        for i in range(len(_list)):
            if _list[i] == value:
                return i
        return -1
