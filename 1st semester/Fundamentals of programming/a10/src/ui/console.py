from src.repository.book_repo import BookRepo
from src.repository.book_text_repo import BookTextRepo
from src.repository.client_repo import ClientRepo
from src.repository.client_text_repo import ClientTextRepo
from src.repository.rental_repo import RentalRepo
from src.repository.rental_text_repo import RentalTextRepo
from src.services.book_service import BookService
from src.services.client_service import ClientService
from src.domain.book import CustomException, Book
from src.domain.client import Client
from src.domain.rental import Rental
from src.services.rental_service import RentalService
from src.ui.generate import Generate
from datetime import date
from src.services.undo_service import UndoService
from src.settings.settings import Settings
from os import path
from src.repository.book_pickle_repo import BookPickleRepo
from src.repository.client_pickle_repo import ClientPickleRepo
from src.repository.rental_pickle_repo import RentalPickleRepo


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

    def __init__(self, book_repo, client_repo, rental_repo):
        self.undo_service = UndoService()
        self.client_repo = client_repo
        self.book_repo = book_repo
        self.rental_repo = rental_repo
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

    # DISPLAY###########################################################

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

    # ADD########################################################################

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

    # REMOVE###################################################################################

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

    # UPDATE##################################################################################

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

    # RENT#####################################################################

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

    # RETURN######################################################################################

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

    # SEARCH#######################################################################################

    def search_ui(self):
        option = input("What would you like to search for?\n   1. Book\n   2. Customer\n ~:")
        if option not in ['1', '2']:
            raise CustomException("you have to choose between 1 and 2")
        if option == '1':
            """
            The user has selected to search for a book
            """
            op = input(
                "Would you like to search by:?\n   1. Book_id?\n   2. Author?\n   3. Title?\n   p~:")
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
                print(x, _list[i].tostring(), sep='. ')
                x += 1
        else:
            """
            The user has selected to search for a client
            """
            op = input("Would you like to search by:?\n   1. Client_id?\n   2. Name?\n ~:")
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
                print(x, _list[i].tostring(), sep='. ')
                x += 1

    # STATISTICS####################################################################################

    def statistics_ui(self):
        option = input(
            "Statistics: ?\n   1. Most rented books\n   2. Most active clients\n   3. Most rented author\n ~:")
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

    # UNDO###########################################################################################

    def undo_ui(self):
        self.undo_service.undo()

    # REDO###########################################################################################

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

    def start(self):
        done = False
        command_dict = {'1': self.display_ui, '2': self.add_ui, '3': self.remove_ui, '4': self.update_ui,
                        '5': self.rent_ui, '6': self.return_ui, '7': self.search_ui, '8': self.statistics_ui,
                        '9': self.undo_ui, '10': self.redo_ui}
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


if __name__ == "__main__":
    settings = Settings("settings.properties")

    if settings.get_repository_type() == "inmemory":
        b = BookRepo()
        c = ClientRepo()
        r = RentalRepo()

    elif settings.get_repository_type() == "text":
        settings.file_decisions()
        b = BookTextRepo(settings.books_file)
        c = ClientTextRepo(settings.clients_file)
        r = RentalTextRepo(settings.rentals_file)

    else:
        settings.file_decisions()
        b = BookPickleRepo(settings.books_file)
        c = ClientPickleRepo(settings.clients_file)
        r = RentalPickleRepo(settings.rentals_file)

    generate = Generate(b, c, r)
    if len(b.books._list) == 0:
        generate.add_books()
    if len(c.clients._list) == 0:
        generate.add_clients()
    if len(r.rentals._list) == 0:
        generate.add_rentals()

    ui = UI(b, c, r)
    ui.start()
