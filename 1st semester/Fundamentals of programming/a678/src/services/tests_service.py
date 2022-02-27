import unittest
from datetime import date
from src.domain.book import Book, CustomException
from src.domain.client import Client
from src.domain.rental import Rental
from src.repository.book_repo import BookRepo
from src.repository.client_repo import ClientRepo
from src.repository.rental_repo import RentalRepo
from src.services.book_service import BookService
from src.services.client_service import ClientService
from src.services.rental_service import RentalService, MostRentedAuthor, ActiveClients, BookRentalDays
from src.services.undo_service import UndoService


class TestBook(unittest.TestCase):
    def setUp(self):
        self._book = BookRepo()
        self._client = ClientRepo()
        self._rental = RentalRepo()
        self._undo_service = UndoService()
        self._rental_service = RentalService(self._undo_service, self._rental, self._book, self._client)
        self.b = BookService(self._undo_service, self._rental_service, self._book)
        self.book = Book('2341', 'John Green', 'Paper Towns')
        self.b.add_book(self.book)

    def test_create(self):
        self.b.create('8906', 'JK Rollins', 'Harry Potter')

    def test_add_book(self):
        self.assertRaises(CustomException, self.b.add_book, Book('2341', 'John Green', 'Paper Towns'))

    def test_verif_data(self):
        self.assertRaises(CustomException, self.b.verif_data, '', 'John Green', 'Paper Towns')
        self.assertRaises(CustomException, self.b.verif_data, '2345', '', 'Paper Towns')
        self.assertRaises(CustomException, self.b.verif_data, '2345', 'John Green', '')

    def test_remove(self):
        self.assertRaises(CustomException, self.b.remove_book, Book('4441', 'John Green', 'Paper Towns'))
        self.b.create('8906', 'JK Rollins', 'Harry Potter')
        self._rental.rent_book(Rental('11', '2341', '123', '12.11.2020', ''))
        self.b.remove_book('2341')

    def test_update(self):

        self.assertRaises(CustomException, self.b.update_book, '665', '3', 'Harry Potter')
        self._rental.rent_book(Rental('11', '2301', '2341', '12.11.2020', ''))
        self.b.update_book('2341', '1', '583')
        self.b.update_book('583', '2', 'JK Rollins')
        self.b.update_book('583', '3', 'Harry Potter')

    def test_search(self):
        self.assertRaises(CustomException, self.b.search, '1', '678')
        self.assertEqual(self.b.search('1', '23'), ['book_id:2341, author:John Green, title: Paper Towns'])

    def test_book_author_id(self):
        self.b.author_book_id()

    def test_book_id_title_author(self):
        self.b.book_id_title_author()


class TestClient(unittest.TestCase):
    def setUp(self):
        self._book = BookRepo()
        self._client = ClientRepo()
        self._rental = RentalRepo()
        self._undo_service = UndoService()
        self._rental_service = RentalService(self._undo_service, self._rental, self._book, self._client)
        self.c = ClientService(self._undo_service, self._rental_service, self._client)
        self.client = Client('2341', 'Andy Spellman')
        self.c.add_client(self.client)

    def test_create(self):
        self.c.create('1', '2')

    def test_add(self):
        self.assertRaises(CustomException, self.c.add_client, Client('2341', 'Andy Spellman'))

    def test_verif(self):
        self.assertRaises(CustomException, self.c.verif_data, '', 'Harry')
        self.assertRaises(CustomException, self.c.verif_data, '123', '')

    def test_remove(self):
        self.assertRaises(CustomException, self.c.remove_client, '8341')
        self._rental.rent_book(Rental('11', '2301', '2341', '12.11.2020', ''))
        self.c.remove_client('2341')

    def test_update(self):
        self.assertRaises(CustomException, self.c.update_client, '8341', '2', 'Harry')
        self.c.update_client('2341', '2', 'Harry')
        self._rental.rent_book(Rental('11', '2301', '2341', '12.11.2020', ''))
        self.c.update_client('2341', '1', '4600')

    def test_search(self):
        self.assertEqual(self.c.search('1', '23'), ['client_id: 2341, name: Andy Spellman'])
        self.assertRaises(CustomException, self.c.search, '1', '567')

    def test_client_id_name(self):
        self.c.client_id_name()


class TestRental(unittest.TestCase):
    def setUp(self):
        self._book = BookRepo()
        self._client = ClientRepo()
        self._rental = RentalRepo()
        self._undo_service = UndoService()
        self.r = RentalService(self._undo_service, self._rental, self._book, self._client)

    def test_create(self):
        self.r.create('123', '1234', '12345', '12.11.2020', '')

    def test_rent(self):
        self.r.rent_book(Rental('123', '1234', '12345', '12.11.2020', ''))
        self.assertRaises(CustomException, self.r.rent_book, Rental('123', '1234', '12345', '12.11.2020', ''))

    def test_return(self):
        self.assertRaises(CustomException, self.r.return_book, '1235', '12.12.2020')
        self.r.rent_book(Rental('123', '1234', '12345', '12.11.2020', ''))
        self.r.return_book('123', '25.12.2020')

    def test_verif(self):
        self.assertRaises(CustomException, self.r.verif_list)

    def test_most_rented_books(self):
        self._book.add_book(Book('1234', 'John Green', 'Paper Towns'))
        self.r.rent_book(Rental('123', '1234', '12345', '12.11.2020', ''))
        _list = self._book.book_id_title_author()
        x = self.r.most_rented_books(_list)
        self.assertEqual(len(x), 1)

    def test_most_active_clients(self):
        self._client.add_client(Client('2341', 'Andy Spellman'))
        self._book.add_book(Book('1234', 'John Green', 'Paper Towns'))
        self.r.rent_book(Rental('123', '1234', '1234', '12.11.2020', ''))
        _list = self._client.client_id_name()
        x = self.r.most_active_clients(_list)
        self.assertEqual(len(x), 1)

    def test_most_rented_author(self):
        self._book.add_book(Book('1234', 'John Green', 'Paper Towns'))
        self.r.rent_book(Rental('123', '1234', '12345', '12.11.2020', ''))
        _list = self._book.author_book_id()
        x = self.r.most_rented_author(_list)
        self.assertEqual(len(x), 1)

    def test_filter(self):
        self.r.rent_book(Rental('123', '1234', '12345', '12.11.2020', ''))
        x = self.r.filter_rentals(None, '1234')
        self.assertEqual(len(x), 1)
        self.r.filter_rentals(None, '1')

        x = self.r.filter_rentals('12345', None)
        self.assertEqual(len(x), 1)
        self.r.filter_rentals('4', None)

    def test_delete(self):
        self.r.rent_book(Rental('123', '1234', '12345', '12.11.2020', ''))
        self.r.remove_rental('123')
        self.assertEqual(len(self.r.rental.rentals), 0)

    def test_diff_dates(self):
        rental = Rental('123', '1234', '12345', '12.11.2020', '')
        d1, d2, d3 = rental.get_date('12.11.2020')
        d4, d5, d6 = rental.get_date('13.11.2020')
        ren_day = date(int(d3), int(d2), int(d1))
        ret_day = date(int(d6), int(d5), int(d4))
        self.assertEqual(self.r.diff_dates(ren_day, ret_day), 1)


class TestBookRentalDays(unittest.TestCase):
    def test(self):
        b = BookRentalDays('123', 'Paper Towns', 'John Green', '4')
        self.assertEqual(b.book_id, '123')
        self.assertEqual(b.rental_times, '4')
        self.assertEqual(b.title, 'Paper Towns')
        self.assertEqual(b.author, 'John Green')
        self.assertEqual(b.__str__(), '123: Paper Towns by John Green - 4 time(s)')


class TestActiveClients(unittest.TestCase):
    def test(self):
        c = ActiveClients('34', 'Andy Spellman', '53')
        self.assertEqual(c.client_id, '34')
        self.assertEqual(c.rental_days, '53')
        self.assertEqual(c.name, 'Andy Spellman')
        self.assertEqual(c.__str__(), '34: Andy Spellman - 53 day(s)')


class TestMostRentedAuthor(unittest.TestCase):
    def test(self):
        a = MostRentedAuthor('John Green', '2')
        self.assertEqual(a.author, 'John Green')
        self.assertEqual(a.rental_times, '2')
        self.assertEqual(a.__str__(), 'John Green - 2 time(s)')


class TestUndo(unittest.TestCase):
    def setUp(self):
        self.u = UndoService()

    def test_record(self):
        self.u.record('remove')

    def test_undo(self):
        self.assertRaises(CustomException, self.u.undo)

    def test_redo(self):
        self.assertRaises(CustomException, self.u.redo)


if __name__ == '__main__':
    unittest.main()
