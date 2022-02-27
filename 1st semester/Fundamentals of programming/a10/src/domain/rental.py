from src.domain.book import CustomException
from datetime import date


class Rental:
    def __init__(self, rental_id, book_id, client_id, rented_date, returned_date):
        """
        create rental
        :param rental_id:
        :param book_id:
        :param client_id:
        :param rented_date:
        :param returned_date:
        """
        if not self.verif_dates(rented_date, returned_date):
            raise CustomException("returned date must be after rent date")
        self._rental_id = rental_id
        self._book_id = book_id
        self._client_id = client_id
        self._rented_date = rented_date
        self._returned_date = returned_date

    @staticmethod
    def get_date(_date):
        """
        separate a date into day, month and year
        :param _date: te given date
        :return:
        """
        if _date == '':
            today = date.today()
            _date = today.strftime("%d.%m.%Y")
        _list = _date.split('.')
        if len(_list) == 3:
            if _list[0] > '31':
                raise CustomException("the day must be <= 31")
            if _list[1] > '12':
                raise CustomException("the month must be <= 12")
            return _list[0], _list[1], _list[2]

    def verif_dates(self, rented_date, returned_date):
        """
        verify if rented date is before returned date
        :param rented_date: -
        :param returned_date: -
        :return: true if it does not raise an exception
        """
        ren_day, ren_month, ren_year = self.get_date(rented_date)
        ret_day, ret_month, ret_year = self.get_date(returned_date)
        if ren_year > ret_year:
            return False

        if ren_year == ret_year and ren_month > ret_month:
            return False

        if ren_year == ret_year and ren_month == ret_month and ren_day > ret_day:
            return False

        return True

    @property
    def rental_id(self):
        return self._rental_id

    @property
    def book_id(self):
        return self._book_id

    @property
    def client_id(self):
        return self._client_id

    @property
    def rented_date(self):
        return self._rented_date

    @property
    def returned_date(self):
        return self._returned_date

    @returned_date.setter
    def returned_date(self, value):
        self._returned_date = value

    @client_id.setter
    def client_id(self, value):
        self._client_id = value

    @book_id.setter
    def book_id(self, value):
        self._book_id = value

    def tostring(self):
        return 'rental id: ' + str(self.rental_id) + ', book id: ' + str(self.book_id) + ', client id: ' + str(self.client_id) + ', rented date: ' + str(self.rented_date) + ', returned date: ' + str(self.returned_date)
