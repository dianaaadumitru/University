import unittest

from computer.computer import RandomMove, AI
from domain.board import Board
from service.game import Game


class Tests(unittest.TestCase):
    def test_board(self):
        x = Board()
        x.move(5, 'X')
        x.move(3, 'X')
        x.move(3, 'X')
        self.assertEqual(x.have_player_won(3), False)
        x.move(3, 'X')
        x.move(3, 'X')
        self.assertEqual(x.winning_move(3), True)
        self.assertEqual(x.have_player_won(3), True)

        y = Board()
        y.move(0, 'X')
        y.move(0, 'X')
        y.move(0, 'X')
        y.move(0, 'Y')
        y.move(0, 'Y')
        y.move(0, 'Y')

        y.move(1, 'Y')
        y.move(1, 'Y')
        y.move(1, 'Y')
        y.move(1, 'X')
        y.move(1, 'X')
        y.move(1, 'X')

        y.move(2, 'X')
        y.move(2, 'X')
        y.move(2, 'X')
        y.move(2, 'Y')
        y.move(2, 'Y')
        y.move(2, 'Y')

        y.move(3, 'Y')
        y.move(3, 'Y')
        y.move(3, 'Y')
        y.move(3, 'X')
        y.move(3, 'X')
        y.move(3, 'X')

        y.move(4, 'X')
        y.move(4, 'X')
        y.move(4, 'X')
        y.move(4, 'Y')
        y.move(4, 'Y')
        y.move(4, 'Y')

        y.move(5, 'Y')
        y.move(5, 'Y')
        y.move(5, 'Y')
        y.move(5, 'X')
        y.move(5, 'X')
        y.move(5, 'X')

        y.move(6, 'X')
        y.move(6, 'X')
        y.move(6, 'X')
        y.move(6, 'Y')
        y.move(6, 'Y')
        y.move(6, 'Y')
        self.assertEqual(y.is_tie(3), True)

    def test_computer(self):
        r = RandomMove()
        x = Board()
        self.assertNotEqual(r.move(x._data), -1)

        ai = AI()
        self.assertNotEqual(ai.computer_move(x._data), -1)
        self.assertNotEqual(ai.best_move(x._data), -1)

    def test_game(self):
        x = Board()
        ai = AI()
        g = Game(x, ai)
        g.player_move(0)

        self.assertNotEqual(g.computer_move(x._data), -1)
