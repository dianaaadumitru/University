from src.repository.book_repo import BookRepo
from src.repository.client_repo import ClientRepo
from src.repository.rental_repo import RentalRepo
from src.services.book_service import BookService
from src.services.client_service import ClientService
from src.domain.book import CustomException, Book
from src.domain.client import Client
from src.domain.rental import Rental
from src.services.rental_service import RentalService
import random
from datetime import date
from src.services.undo_service import UndoService


class UI:
    """
    Class that implements the UI functionalities
        - read a Book/Client and adding it to the list
        - display the list of Books/Clients
        - remove a book/client from the list
        - update the list of Books/Clients
        - rent a book
        - return a book
    """

    def __init__(self):
        self.undo_service = UndoService()
        self.client_repo = ClientRepo()
        self.book_repo = BookRepo()
        self.client_repo = ClientRepo()
        self.rental_repo = RentalRepo()
        self._rental_service = RentalService(self.undo_service, self.rental_repo, self.book_repo, self.client_repo)
        self._book_service = BookService(self.undo_service, self._rental_service, self.book_repo)
        self._client_service = ClientService(self.undo_service, self._rental_service, self.client_repo)

    @property
    def book(self):
        return self._book_service

    @property
    def client(self):
        return self._client_service

    @property
    def rental(self):
        return self._rental_service

    #DISPLAY###########################################################

    def display_books(self):
        x = 1
        for book in self.book.book.books:
            print(x, book.tostring(), sep='. ')
            x += 1

    def display_clients(self):
        x = 1
        for client in self.client.client.clients:
            print(x, client.tostring(), sep='. ')
            x += 1

    def display_rentals(self):
        x = 1
        self.rental.verif_list()
        for rental in self.rental.rental.rentals:
            print(x, rental.tostring(), sep='. ')
            x += 1

    def display_ui(self):
        option = input("What would you like to display?\n   1. Book\n   2. Customer\n   3. Rentals\n~:")
        if option not in ['1', '2', '3']:
            raise CustomException("you have to choose between 1, 2 and 3")
        if option == '1':
            """
            The user has selected to display the books
            """
            self.display_books()
        elif option == '2':
            """
            The user has selected to display the customers
            """
            self.display_clients()
        else:
            """
            The user has selected to display the rentals
            """
            self.display_rentals()

     #ADD########################################################################

    def get_data_for_book(self):
        book_id = input("Enter the book_id: ")
        author = input("Enter the author: ")
        title = input("Enter the title: ")
        self.book.verif_data(book_id, author, title)
        book = Book(book_id.strip(), author.strip(), title.strip())
        return book

    def get_data_for_customer(self):
        client_id = input("Enter the client_id: ")
        name = input("Enter the customer name: ")
        self.client.verif_data(client_id, name)
        client = Client(client_id.strip(), name.strip())
        return client

    def add_ui(self):
        option = input("What would you like to add?\n   1. Book\n   2. Customer\n ~:")
        if option not in ['1', '2']:
            raise CustomException("you have to choose between 1 and 2")
        if option == '1':
            """
            The user has selected to add a book
            """
            book = self.get_data_for_book()
            self.book.add_book(book)
        else:
            """
            The user has selected to add a customer
            """
            customer = self.get_data_for_customer()
            self.client.add_client(customer)

    #REMOVE###################################################################################

    def remove_ui(self):
        option = input("What would you like to remove?\n   1. Book\n   2. Customer\n ~:")
        if option not in ['1', '2']:
            raise CustomException("you have to choose between 1 and 2")
        if option == '1':
            """
            The user has selected to remove a book
            """
            book_id = input("The book id of the book you want to remove: ")
            self.book.remove_book(book_id)
        else:
            """
            The user has selected to remove a customer
            """
            client_id = input("The client id of the client you want to remove: ")
            self.client.remove_client(client_id)

    #UPDATE##################################################################################

    def update_ui(self):
        option = input("What would you like to update?\n   1. Book\n   2. Customer\n ~:")
        if option not in ['1', '2']:
            raise CustomException("you have to choose between 1 and 2")
        if option == '1':
            """
            The user has selected to update a book
            """
            old_id = input("The ID of the book you want to update: ")
            op = input("What would you like to update?\n   1. Book_id\n   2. Author\n   3. Title\n  ~:")
            if op not in ['1', '2', '3']:
                raise CustomException("you have to choose between 1, 2 and 3")
            if op == '1':
                """
                The user selected to update the ID
                """
                value = input("New book_id: ")
            elif op == '2':
                """
                The user selected to update the author
                """
                value = input("New author: ")
            else:
                """
                The user selected to update the title
                """
                value = input("New title: ")
            self.update_book(old_id, op, value)
        else:
            """
            The user has selected to update a client
            """
            old_id = input("The ID of the customer you want to update: ")
            op = input("What would you like to update?\n   1. Client_id\n   2. Name\n  ~:")
            if op not in ['1', '2']:
                raise CustomException("you have to choose between 1 and 2")
            if op == '1':
                """
                The user selected to update the ID
                """
                value = input("New client_id: ")
            else:
                """
                The user selected to update the name
                """
                value = input("New name: ")

            self.update_customer(old_id, op, value)

    def update_book(self, book_id, op, value):
        return self.book.update_book(book_id, op, value)

    def update_customer(self, client_id, op, value):
        return self.client.update_client(client_id, op, value)

    #RENT#####################################################################

    def get_data_for_rentals(self):
        rental_id = input("Rental_id: ")
        if self.rental.rental.find_by_rental_id(rental_id) is not None:
            raise CustomException("This id already exists")
        book_id = input("Book_id: ")
        client_id = input("Client_id: ")
        option = input("What would you like to use?\n   1. Today's date\n   2. Custom date\n ~: ")
        if option not in ['1', '2']:
            raise CustomException("you have to choose between 1 and 2")
        if option == '1':
            today = date.today()
            rented_date = today.strftime("%d.%m.%Y")
            print(rented_date)
        else:
            day = input("Insert the day: ")
            month = input("Insert the month: ")
            year = input("Insert the year: ")
            rented_date = str(day) + '.' + str(month) + '.' + str(year)
            print(rented_date)

        rental = Rental(rental_id, book_id, client_id, rented_date, '')
        return rental

    def rent_ui(self):
        rental = self.get_data_for_rentals()
        if self.book.book.find_by_book_id(rental.book_id) is None:
            raise CustomException("The book you want to rent does not exist")
        if self.client.client.find_by_client_id(rental.client_id) is None:
            raise CustomException("This customer does not exist")

        self.rental.rent_book(rental)
        print("Rental successful")

    #RETURN######################################################################################

    def return_ui(self):
        rental_id = input("the rental_id for the book you want to return: ")
        option = input("What would you like to use?\n   1. Today's date\n   2. Custom date\n ~: ")
        if option not in ['1', '2']:
            raise CustomException("you have to choose between 1 and 2")
        if option == '1':
            today = date.today()
            returned_date = today.strftime("%d.%m.%Y")
            print(returned_date)
        else:
            day = input("Insert the day: ")
            month = input("Insert the month: ")
            year = input("Insert the year: ")
            returned_date = str(day) + '.' + str(month) + '.' + str(year)
            print(returned_date)

        self.rental.return_book(rental_id, returned_date)
        print('Returned successful')

    #SEARCH#######################################################################################

    def search_ui(self):
        option = input("What would you like to search for?\n   1. Book\n   2. Customer\n ~:")
        if option not in ['1', '2']:
            raise CustomException("you have to choose between 1 and 2")
        if option == '1':
            """
            The user has selected to search for a book
            """
            op = input("Would you like to search by:?\n   1. Book_id?\n   2. Author?\n   3. Title?\n   4. All of them\n  ~:")
            if op not in ['1', '2', '3', '4']:
                raise CustomException("you have to choose between 1, 2 and 3")
            if op == '1':
                """
                The user has selected to search by id
                """
                value = input("Book_id: ")
            elif op == '2':
                """
                The user has selected to search by author
                """
                value = input("Author: ")
            elif op == '3':
                """
                The user has selected to search by title
                """
                value = input("Title: ")
            else:
                """
                The user has selected to search in all fields
                """
                value = input("The word: ")

            _list = self.book.search(op, value.strip())
            x = 1
            for i in range(len(_list)):
                print(x, _list[i], sep='. ')
                x += 1
        else:
            """
            The user has selected to search for a client
            """
            op = input("Would you like to search by:?\n   1. Client_id?\n   2. Name?\n   3. All of them\n ~:")
            if op not in ['1', '2', '3']:
                raise CustomException("you have to choose between 1and 2")
            if op == '1':
                """
                The user has selected to search by id
                """
                value = input('Client id: ')
            elif op == '2':
                """
                The user has selected to search by name
                """
                value = input('Name: ')
            else:
                """
                The user has selected to search in all fields
                """
                value = input("The word: ")

            _list = self.client.search(op, value.strip())
            x = 1
            for i in range(len(_list)):
                print(x, _list[i], sep='. ')
                x += 1

    #STATISTICS####################################################################################

    def statistics_ui(self):
        option = input("Statistics: ?\n   1. Most rented books\n   2. Most active clients\n   3. Most rented author\n ~:")
        if option not in ['1', '2', '3']:
            raise CustomException("you have to choose between 1, 2 and 3")
        if option == '1':
            """
            most rented books
            """
            _list = self.book.book_id_title_author()
            data = self.rental.most_rented_books(_list)
        elif option == '2':
            """
            Most active clients
            """
            _list = self.client.client_id_name()
            data = self.rental.most_active_clients(_list)
        else:
            """
            Most rented author
            """
            _list = self.book.author_book_id()
            data = self.rental.most_rented_author(_list)

        if len(data) == 0:
            print('No rentals recorded.')
        else:
            for cr in data:
                print(cr)

    #UNDO###########################################################################################

    def undo_ui(self):
        self.undo_service.undo()

    #REDO###########################################################################################

    def redo_ui(self):
        self.undo_service.redo()

    @staticmethod
    def print_menu():
        s = 'Available commands:\n'
        s += '   1. Display (books, customers or rentals)\n'
        s += '   2. Add (books or customers)\n'
        s += '   3. Remove (books or customers)\n'
        s += '   4. Update (books or customers)\n'
        s += '   5. Rent a book\n'
        s += '   6. Return a book\n'
        s += '   7. Search (for book or customer)\n'
        s += '   8. Statistics\n'
        s += '   9. Undo\n'
        s += '   10. Redo\n'
        s += '   0. Exit\n'
        print(s)

    # def test_init(self):
    #     self.book.books.append(Book('56177', 'Sarah Maas', 'A Court of Thorns and Roses'))
    #     self.book.books.append(Book('41365', 'John Knowles', 'A Separate Peace'))
    #     self.book.books.append(Book('42009', 'Lemony Snicket', 'A Series of Unfortunate Events'))
    #     self.book.books.append(Book('78130', 'Ursula K. Le Guin', 'A Wizard of Earthsea'))
    #     self.book.books.append(Book('59462', 'Jennifer Niven', 'All the Bright Places'))
    #     self.book.books.append(Book('44983', 'J.K. Rowling', 'Harry Potter'))
    #     self.book.books.append(Book('35978', 'Jandy Nelson', 'I wll Give You the Sun'))
    #     self.book.books.append(Book('88466', 'Kristin Cashore', 'Jane, Unlimited'))
    #     self.book.books.append(Book('11694', 'Louisa May Alcott', 'Little Women'))
    #     self.book.books.append(Book('45796', 'Jerry Spinelli', 'Stargirl'))
    #
    #     self.client.clients.append(Client('198456', 'Dwayne Johnson'))
    #     self.client.clients.append(Client('159846', 'Ryan Reynolds'))
    #     self.client.clients.append(Client('985547', 'Nicole Kidman'))
    #     self.client.clients.append(Client('159987', 'Anne Hathaway'))
    #     self.client.clients.append(Client('116002', 'Ben Affleck'))
    #     self.client.clients.append(Client('554203', 'Jennifer Aniston'))
    #     self.client.clients.append(Client('778851', 'Vin Diesel'))
    #     self.client.clients.append(Client('666286', 'Will Smith'))
    #     self.client.clients.append(Client('210645', 'Sandra Bullock'))
    #     self.client.clients.append(Client('874225', 'Jackie Chan'))

    @staticmethod
    def generate_book_id():
        digits = '0123456789'
        book_id = ''
        for i in range(5):
            book_id = book_id + str(random.choice(digits))
        return book_id

    @staticmethod
    def generate_author():
        surname = ['Sarah', 'John', 'Lemony', 'Ursula', 'Jennifer', 'Ibi', 'Sabaa', 'Joy', 'Jacqueline', 'Madeleine']

        name = ['Maas', 'Knowles', 'Snicket', 'Le Guin', 'Niven', 'Zoboi', 'Tahir', 'McCullough', 'Woodson', 'Lengle']

        sn = random.randint(0, len(surname) - 1)
        n = random.randint(0, len(name) - 1)
        rez = name[n] + ' ' + surname[sn]
        return rez

    @staticmethod
    def generate_title():
        titles = ['A Court of Thorns and Roses', 'A Separate Peace', 'A Series of Unfortunate Events',
                  'A Wizard of Earthsea', 'All the Bright Places', 'American Street', 'An Ember in the Ashes',
                  'Blood Water Paint', 'Brown Girl Dreaming', 'A Wrinkle in Time']
        index = random.randint(0, len(titles) - 1)
        return titles[index]

    @staticmethod
    def generate_client_id():
        digits = '0123456789'
        client_id = ''
        for i in range(6):
            client_id = client_id + str(random.choice(digits))
        return client_id

    @staticmethod
    def generate_name():
        surname = ['Dwayne', 'Ryan', 'Nicole', 'Anne', 'Ben', 'Jennifer', 'Vin', 'Will', 'Sandra', 'Jackie']

        name = ['Johnson', 'Reynolds', 'Kidman', 'Hathaway', 'Affleck', 'Aniston', 'Diesel', 'Smith', 'Bullock', 'Chan']

        sn = random.randint(0, len(surname) - 1)
        n = random.randint(0, len(name) - 1)
        rez = surname[n] + ' ' + name[sn]
        return rez

    def test_init(self):
        for i in range(10):
            self.book.book.books.append(Book(self.generate_book_id(), self.generate_author(), self.generate_title()))
            self.client.client.clients.append(Client(self.generate_client_id(), self.generate_name()))
        self.rental.rental.rentals.append(Rental('12', self.book.book.books[0].book_id, self.client.client.clients[6].client_id, '06.11.2020', '03.12.2020'))
        self.rental.rental.rentals.append(Rental('12', self.book.book.books[1].book_id, self.client.client.clients[7].client_id, '12.10.2020', '03.12.2020'))
        self.rental.rental.rentals.append(Rental('12', self.book.book.books[8].book_id, self.client.client.clients[6].client_id, '06.11.2020', '29.11.2020'))

    def start(self):
        done = False
        command_dict = {'1': self.display_ui, '2': self.add_ui, '3': self.remove_ui, '4': self.update_ui, '5': self.rent_ui, '6': self.return_ui, '7': self.search_ui, '8': self.statistics_ui, '9': self.undo_ui, '10': self.redo_ui}
        while not done:
            print()
            self.print_menu()
            nr = input("Enter the number of the operation you want: ")
            nr = nr.strip()
            if nr in command_dict:
                try:
                    command_dict[nr]()
                except CustomException as valerr:
                    print(str(valerr))
            elif nr == '0':
                done = True
            else:
                print("Invalid input")


ui = UI()
ui.test_init()
ui.start()
