from src.domain.book import Book
from src.repository.book_repo import BookRepo


class BookTextRepo(BookRepo):
    def __init__(self, file_name):
        BookRepo.__init__(self)
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
                BookRepo.add_book(self, Book(int(line[0]), line[1], line[2]))
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

        for book in self.books:
            b = str(book.book_id) + ", "
            b += str(book.author) + ", "
            b += str(book.title) + "\n"
            f.write(b)

    def update_book(self, book_id, op, value):
        BookRepo.update_book(self, book_id, op, value)
        self.store()

    def add_book(self, book):
        BookRepo.add_book(self, book)
        self.store()

    def remove_book(self, book_id):
        book = BookRepo.remove_book(self, book_id)
        self.store()
        return book
