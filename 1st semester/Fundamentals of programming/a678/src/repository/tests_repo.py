import unittest
from datetime import date
from src.domain.book import Book, CustomException
from src.domain.client import Client
from src.domain.rental import Rental
from src.repository.book_repo import BookRepo
from src.repository.client_repo import ClientRepo
from src.repository.rental_repo import RentalRepo


class TestBook(unittest.TestCase):
    def setUp(self):
        self.b = BookRepo()
        self.b.add_book(Book('2341', 'John Green', 'Paper Towns'))
        self.b.add_book(Book('2374', 'A234er', 'Lord of the rings'))

    def test_add_book(self):
        self.assertEqual(len(self.b), 2)

    def test_search(self):
        ok, _list = self.b.search('4', '234')
        self.assertEqual(ok, 1)
        self.assertEqual(len(_list), 2)

        ok, _list = self.b.search('4', 'o')
        self.assertEqual(ok, 1)
        self.assertEqual(len(_list), 2)

        ok, _list = self.b.search('1', '23')
        self.assertEqual(ok, 1)
        self.assertEqual(len(_list), 2)

        ok, _list = self.b.search('2', 'Jo')
        self.assertEqual(ok, 1)
        self.assertEqual(len(_list), 1)

        ok, _list = self.b.search('3', 'er To')
        self.assertEqual(ok, 1)
        self.assertEqual(len(_list), 1)

    def test_remove(self):
        self.b.remove_book('2374')
        self.assertEqual(len(self.b.books), 1)
        self.assertRaises(CustomException, self.b.remove_book, '111')

    def test_find_by_id(self):
        self.assertNotEqual(self.b.find_by_book_id('2341'), None)
        self.assertEqual(self.b.find_by_book_id('123'), None)

    def test_author_book_id(self):
        l = self.b.author_book_id()
        self.assertEqual(len(l), 4)

    def test_book_id_title_author(self):
        l = self.b.book_id_title_author()
        self.assertEqual(len(l), 6)

    def test_find_in_list(self):
        l = self.b.author_book_id()
        self.assertEqual(self.b.find_in_list(l, '2341'), 0)
        self.assertEqual(self.b.find_in_list(l, '241'), -1)

    def test_update(self):
        self.b.update_book('2341', '1', '5678')
        self.assertEqual(self.b.books[0].book_id, '5678')

        self.b.update_book('5678', '2', 'JK Rollins')
        self.assertEqual(self.b.books[0].author, 'JK Rollins')

        self.b.update_book('5678', '3', 'Harry Potter')
        self.assertEqual(self.b.books[0].title, 'Harry Potter')


class TestClient(unittest.TestCase):
    def setUp(self):
        self.s = ClientRepo()
        self.s.add_client(Client('2341', 'Andy Spellman'))
        self.s.add_client(Client('927', '23asc'))

    def test_add_client(self):
        self.assertEqual(len(self.s), 2)

    def test_remove(self):
        self.s.remove_client('927')
        self.assertEqual(len(self.s), 1)
        self.assertRaises(CustomException, self.s.remove_client, '123')

    def test_update(self):
        self.s.update_client('2341', '1', '7890')
        self.assertEqual(self.s.clients[0].client_id, '7890')

        self.s.update_client('7890', '2', 'Harry')
        self.assertEqual(self.s.clients[0].name, 'Harry')

    def test_search(self):
        ok, _list = self.s.search('3', '23')
        self.assertEqual(ok, 1)
        self.assertEqual(len(_list), 2)

        ok, _list = self.s.search('1', '34')
        self.assertEqual(ok, 1)
        self.assertEqual(len(_list), 1)

        ok, _list = self.s.search('2', 'And')
        self.assertEqual(ok, 1)
        self.assertEqual(len(_list), 1)

    def test_client_id_name(self):
        l = self.s.client_id_name()
        self.assertEqual(len(l), 4)

    def test_find_in_list(self):
        self.assertEqual(self.s.find_in_list(['2341', '67', '5'], '2341'), 0)


class TestRental(unittest.TestCase):
    def setUp(self):
        self.r = RentalRepo()
        self.rental2 = Rental('123', '1234', '12345', '12.11.2020', '')
        self.r.add_rental(self.rental2)

    def test_add_rental(self):
        self.assertEqual(len(self.r.rentals), 1)

    def test_rent_book(self):
        rental = Rental('1263', '1284', '12345', '12.11.2020', '')
        self.r.rent_book(rental)
        self.assertEqual(len(self.r.rentals), 2)

    def test_delete_ret_date(self):
        self.r.return_book('123', '23.11.2020')
        self.r.delete_ret_date('123')
        self.assertEqual(self.rental2.returned_date, '')

    def test_find_by_book_id(self):
        self.assertEqual(self.r.find_by_book_id(self.rental2.book_id), 0)
        self.assertEqual(self.r.find_by_book_id('567'), -1)

    def test_find_by_rental_id(self):
        self.assertEqual(self.r.find_by_rental_id('842'), None)
        self.assertNotEqual(self.r.find_by_rental_id(self.rental2.rental_id), None)

    def test_return_book(self):
        self.r.return_book('123', '23.11.2020')
        self.assertEqual(self.r.rentals[0].returned_date, '23.11.2020')

    def test_update_rental(self):
        self.r.update_rental(self.rental2, '12', '123', '45')
        self.assertEqual(self.rental2.book_id, '45')
        self.assertEqual(self.rental2.client_id, '45')

    def test_remove(self):
        self.r.remove_rental('123')
        self.assertEqual(len(self.r.rentals), 0)
        self.assertRaises(CustomException, self.r.remove_rental, '999')


if __name__ == '__main__':
    unittest.main()
