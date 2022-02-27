import unittest
from src.domain.client import Client
from src.struct.iterable_structure import sort_list, IterableStructure, filter_list


class testStructure(unittest.TestCase):
    """
    Unit tests for the iterable structure
    """

    def setUp(self):
        self.structure = IterableStructure()

    def testIt(self):
        self.structure.append(1)
        self.assertEqual(self.structure[0], 1)
        self.structure[0] = 0
        self.assertEqual(self.structure[0], 0)

        del self.structure[0]
        self.assertEqual(self.structure.getList(), [])
        self.structure.append(1)
        self.structure.append(1)
        self.structure.append(1)
        self.structure.append(1)
        for index in range(len(self.structure)):
            self.assertEqual(self.structure[index], 1)

        it1 = iter(self.structure)
        self.assertEqual(next(it1), 1)
        self.assertEqual(next(it1), 1)
        self.assertEqual(next(it1), 1)
        self.assertEqual(next(it1), 1)
        self.assertRaises(StopIteration, next, it1)


class testSortFilter(unittest.TestCase):
    """
    Unit tests for the gnome sort and filter methods
    """

    def setUp(self):
        self.list = [2, 4, 1, 3, 5]

    @staticmethod
    def less_than(c1, c2):
        """
        name descending,id descending
        """
        if c1.name == c2.name:
            if c1.client_id > c2.client_id:
                return True
            else:
                return False
        return c1.name > c2.name

    def testSort(self):
        sort_list(self.list, lambda a, b: a > b)
        self.assertEqual(self.list, [5, 4, 3, 2, 1])

        sort_list(self.list, lambda a, b: a < b)
        self.assertEqual(self.list, [1, 2, 3, 4, 5])

        sort_list(self.list, None)
        self.assertEqual(self.list, self.list)

        c1 = Client('2', 'Diana')
        c2 = Client('5', 'Maria')
        c3 = Client('4', 'David')
        c4 = Client('1', 'Elena')
        c5 = Client('3', 'Ianis')
        list2 = [c1, c2, c3, c4, c5]
        sort_list(list2, lambda a, b: a.client_id < b.client_id)
        self.assertEqual(list2, [c4, c1, c5, c3, c2])

        c6 = Client('6', 'Diana')
        c7 = Client('0', 'Diana')
        list3 = [c1, c2, c3, c4, c5, c6, c7]
        sort_list(list3, lambda a, b: self.less_than(a, b))
        self.assertEqual(list3, [c2, c5, c4, c6, c1, c7, c3])

    def testFilter(self):
        x = filter_list(self.list, None)
        self.assertEqual(x, [2, 4, 1, 3, 5])

        x = filter_list(self.list, lambda a: a != 4)
        self.assertEqual(x, [2, 1, 3, 5])

        c1 = Client('2', 'Diana')
        c2 = Client('5', 'Maria')
        c3 = Client('4', 'David')
        c4 = Client('1', 'Elena')
        c5 = Client('3', 'Ianis')
        c6 = Client('6', 'Diana')

        list2 = [c1, c2, c3, c4, c5, c6]
        new_list = filter_list(list2, lambda a: self.keep_person(a))
        self.assertEqual(new_list, [c2, c3, c4, c5])

    @staticmethod
    def keep_person(c):
        return 'ana' not in c.name
