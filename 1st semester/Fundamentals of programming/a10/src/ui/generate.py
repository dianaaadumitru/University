import random

from src.domain.book import Book
from src.domain.client import Client
from src.domain.rental import Rental


class Generate:
    def __init__(self, book_repo, client_repo, rental_repo):
        self.book_repo = book_repo
        self.client_repo = client_repo
        self.rental_repo = rental_repo

    # @staticmethod
    # def generate_book_id():
    #     digits = '0123456789'
    #     book_id = ''
    #     for i in range(5):
    #         book_id = book_id + str(random.choice(digits))
    #     return book_id
    #
    # @staticmethod
    # def generate_author():
    #     surname = ['Sarah', 'John', 'Lemony', 'Ursula', 'Jennifer', 'Ibi', 'Sabaa', 'Joy', 'Jacqueline', 'Madeleine']
    #
    #     name = ['Maas', 'Knowles', 'Snicket', 'Le Guin', 'Niven', 'Zoboi', 'Tahir', 'McCullough', 'Woodson', 'Lengle']
    #
    #     sn = random.randint(0, len(surname) - 1)
    #     n = random.randint(0, len(name) - 1)
    #     rez = name[n] + ' ' + surname[sn]
    #     return rez
    #
    # @staticmethod
    # def generate_title():
    #     titles = ['A Court of Thorns and Roses', 'A Separate Peace', 'A Series of Unfortunate Events',
    #               'A Wizard of Earthsea', 'All the Bright Places', 'American Street', 'An Ember in the Ashes',
    #               'Blood Water Paint', 'Brown Girl Dreaming', 'A Wrinkle in Time']
    #     index = random.randint(0, len(titles) - 1)
    #     return titles[index]

    def add_books(self):
        # for i in range(10):
        #     self.book_repo.add_book(Book(self.generate_book_id(), self.generate_author(), self.generate_title()))
        self.book_repo.add_book(Book('56177', 'Sarah Maas', 'A Court of Thorns and Roses'))
        self.book_repo.add_book(Book('41365', 'John Knowles', 'A Separate Peace'))
        self.book_repo.add_book(Book('42009', 'Lemony Snicket', 'A Series of Unfortunate Events'))
        self.book_repo.add_book(Book('78130', 'Ursula K. Le Guin', 'A Wizard of Earthsea'))
        self.book_repo.add_book(Book('59462', 'Jennifer Niven', 'All the Bright Places'))
        self.book_repo.add_book(Book('44983', 'J.K. Rowling', 'Harry Potter'))
        self.book_repo.add_book(Book('35978', 'Jandy Nelson', 'I wll Give You the Sun'))
        self.book_repo.add_book(Book('88466', 'Kristin Cashore', 'Jane, Unlimited'))
        self.book_repo.add_book(Book('11694', 'Louisa May Alcott', 'Little Women'))
        self.book_repo.add_book(Book('45796', 'Jerry Spinelli', 'Stargirl'))

    # @staticmethod
    # def generate_client_id():
    #     digits = '0123456789'
    #     client_id = ''
    #     for i in range(6):
    #         client_id = client_id + str(random.choice(digits))
    #     return client_id
    #
    # @staticmethod
    # def generate_name():
    #     surname = ['Dwayne', 'Ryan', 'Nicole', 'Anne', 'Ben', 'Jennifer', 'Vin', 'Will', 'Sandra', 'Jackie']
    #
    #     name = ['Johnson', 'Reynolds', 'Kidman', 'Hathaway', 'Affleck', 'Aniston', 'Diesel', 'Smith', 'Bullock', 'Chan']
    #
    #     sn = random.randint(0, len(surname) - 1)
    #     n = random.randint(0, len(name) - 1)
    #     rez = surname[n] + ' ' + name[sn]
    #     return rez

    def add_clients(self):
        # for i in range(10):
        #     self.client_repo.add_client(Client(self.generate_client_id(), self.generate_name()))
        self.client_repo.add_client(Client('198456', 'Dwayne Johnson'))
        self.client_repo.add_client(Client('159846', 'Ryan Reynolds'))
        self.client_repo.add_client(Client('985547', 'Nicole Kidman'))
        self.client_repo.add_client(Client('159987', 'Anne Hathaway'))
        self.client_repo.add_client(Client('116002', 'Ben Affleck'))
        self.client_repo.add_client(Client('554203', 'Jennifer Aniston'))
        self.client_repo.add_client(Client('778851', 'Vin Diesel'))
        self.client_repo.add_client(Client('666286', 'Will Smith'))
        self.client_repo.add_client(Client('210645', 'Sandra Bullock'))
        self.client_repo.add_client(Client('874225', 'Jackie Chan'))

    def add_rentals(self):
        self.rental_repo.add_rental(Rental('11', '56177', '874225', '12.10.2020', '12.12.2020'))
        self.rental_repo.add_rental(Rental('22', '11694', '874225', '19.11.2020', '24.11.2020'))
        self.rental_repo.add_rental(Rental('33', '56177', '554203', '04.04.2020', '08.10.2020'))
