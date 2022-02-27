"""
    UI class.

    Calls between program modules
    ui -> service -> entity
    ui -> entity
"""
import random

from src.services.service import Service
from src.domain.entity import Book


class UI:
    """
    Class that implements the UI functionalities
        - read a Book and adding it to the list
        - display the list of Books
        - call the filter function from services
        - call the undo function from services
    """
    def __init__(self):
        self._service = Service()

    @property
    def service(self):
        return self._service

    def add_book_ui(self):
        isbn = input("Enter the isbn: ")
        author = input("Enter the author: ")
        title = input("Enter the title: ")
        self.service.update_list(self.service.history[self.service.len_h() - 1])

        if len(isbn) == 0 or len(author) == 0 or len(title) == 0:
            raise ValueError("Invalid input")

        book = Book(isbn.strip(), author.strip(), title.strip())
        if self.service.find_by_isbn(book):
            raise ValueError("ISBN already exists")

        self.service.add_book(book)
        self.service.add_history()

    def display_book_ui(self):
        x = 1
        l = self.service.len_h()
        book_list = self.service.history[l - 1]
        for book in book_list:
            print(x, book.tostring(), sep='. ')
            x += 1

    def filter_list_ui(self):
        title = input("The title which the books are filtered by: ")
        self.service.update_list(self.service.history[self.service.len_h() - 1])
        self.service.remove_book(title)
        self.service.add_history()

    def undo_ui(self):
        if self.service.len_h() == 1:
            raise ValueError("You cannot undo anymore")
        self.service.undo()

    @staticmethod
    def print_menu(self):
        print('1. Display the list of books')
        print('2. Add a book')
        print('3. Filter the list of books by title')
        print('4. Undo')
        print('0. Exit')

    @staticmethod
    def generate_isbn(self):
        digits = '0123456789'
        isbn = ''
        for i in range(5):
             isbn = isbn + str(random.choice(digits))
        return isbn

    @staticmethod
    def generate_author(self):
        surename = ['Sarah', 'John', 'Lemony', 'Ursula', 'Jennifer', 'Ibi', 'Sabaa', 'Joy', 'Jacqueline', 'Madeleine']

        name = ['Maas', 'Knowles', 'Snicket', 'Le Guin', 'Niven', 'Zoboi', 'Tahir', 'McCullough', 'Woodson', 'Lengle']

        sn = random.randint(0, len(surename) - 1)
        n = random.randint(0, len(name) - 1)
        rez = name[n] + ' ' + surename[sn]
        return rez

    @staticmethod
    def generate_title(self):
        titles = ['A Court of Thorns and Roses', 'A Separate Peace', 'A Series of Unfortunate Events', 'A Wizard of Earthsea', 'All the Bright Places', 'American Street', 'An Ember in the Ashes', 'Blood Water Paint', 'Brown Girl Dreaming', 'A Wrinkle in Time']
        index = random.randint(0, len(titles)-1)
        return titles[index]

    def test_init(self):

        for i in range(10):
            self.service.books.append(Book(self.generate_isbn(self), self.generate_author(self), self.generate_title(self)))
        self.service.add_history()

    def start(self):
        done = False
        command_dict = {'2': self.add_book_ui, '1': self.display_book_ui, '3': self.filter_list_ui, '4': self.undo_ui}
        while not done:
            print()
            self.print_menu(self)
            nr = input("Enter the number of the operation you want: ")
            nr = nr.strip()
            if nr in command_dict:
                try:
                    command_dict[nr]()
                except ValueError as valerr:
                    print(str(valerr))
            elif nr == '0':
                done = True
            else:
                print("Invalid input")


ui = UI()
ui.test_init()
ui.start()
