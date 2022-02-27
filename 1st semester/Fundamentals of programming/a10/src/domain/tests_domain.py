import unittest
from datetime import date
from src.domain.book import Book, CustomException
from src.domain.client import Client
from src.domain.rental import Rental


class TestBook(unittest.TestCase):
    def test_book(self):
        b = Book('2341', 'John Green', 'Paper Towns')
        self.assertEqual(b.book_id, '2341')
        self.assertEqual(b.author, 'John Green')
        self.assertEqual(b.title, 'Paper Towns')
        b.update_id('8906')
        self.assertEqual(b.book_id, '8906')

        b.update_author('JK Rollins')
        self.assertEqual(b.author, 'JK Rollins')

        b.update_title('Harry Potter')
        self.assertEqual(b.title, 'Harry Potter')

        self.assertEqual(b.tostring(), 'book_id:8906, author:JK Rollins, title: Harry Potter')


class TestClient(unittest.TestCase):
    def test_client(self):
        c = Client('123', 'Andy')
        self.assertEqual(c.name, 'Andy')
        self.assertEqual(c.client_id, '123')

        c.update_id('345')
        self.assertEqual(c.client_id, '345')

        c.update_name('Daniel')
        self.assertEqual(c.name, 'Daniel')

        self.assertEqual(c.tostring(), 'client_id: 345, name: Daniel')


class TestRental(unittest.TestCase):
    def test_rental(self):
        r = Rental('gt5rd', '96409', '886490', '12.11.2020', '02.12.2020')

        self.assertEqual(r.rental_id, 'gt5rd')
        self.assertEqual(r.book_id, '96409')
        self.assertEqual(r.client_id, '886490')
        self.assertEqual(r.rented_date, '12.11.2020')
        self.assertEqual(r.returned_date, '02.12.2020')
        self.assertEqual(r.tostring(), 'rental id: gt5rd, book id: 96409, client id: 886490, rented date: 12.11.2020, returned date: 02.12.2020')

        r2 = Rental('gt5rd', '96409', '886490', '12.11.2020', '')
        today = date.today()
        _date = today.strftime("%d.%m.%Y")
        _list = _date.split('.')
        self.assertEqual(r2.get_date(''), (_list[0], _list[1], _list[2]))
        r2.returned_date = '12.12.2030'
        self.assertEqual(r2.returned_date, '12.12.2030')
        r2.client_id = '11'
        self.assertEqual(r2.client_id, '11')

        r2.book_id = '890'
        self.assertEqual(r2.book_id, '890')

        self.assertRaises(CustomException, Rental, 'gt5rd', '96409', '886490', '04.12.2020', '02.12.2020')
        self.assertRaises(CustomException, Rental, 'gt5rd', '96409', '886490', '12.17.2020', '02.12.2020')
        self.assertRaises(CustomException, Rental, 'gt5rd', '96409', '886490', '38.11.2020', '02.12.2020')

        self.assertEqual(r.verif_dates('05.11.2020', '04.11.2020'), False)
        self.assertEqual(r.verif_dates('05.12.2020', '04.11.2020'), False)
        self.assertEqual(r.verif_dates('05.11.2023', '04.11.2021'), False)


if __name__ == '__main__':
    unittest.main()
