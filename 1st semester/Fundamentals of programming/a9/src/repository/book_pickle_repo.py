import pickle
from src.repository.book_repo import BookRepo


class BookPickleRepo(BookRepo):
    def __init__(self, file_name):
        super().__init__()
        self._file_name = file_name
        self.load_from_binary()

    def load_from_binary(self):
        file = open(self._file_name, "rb")
        try:
            books = pickle.load(file)
        except EOFError:
            books = []
        file.close()

        for book in books:
            super().add_book(book)

    def save_to_binary(self):
        file = open(self._file_name, "wb")
        pickle.dump(super().books, file)
        file.close()

    def add_book(self, book):
        super().add_book(book)
        self.save_to_binary()

    def remove_book(self, book_id):
        book = super().remove_book(book_id)
        self.save_to_binary()
        return book

    def update_book(self, book_id, op, value):
        super().update_book(book_id, op, value)
        self.save_to_binary()