from graph import Graph
from exception import MyException
import random


class RandomGraph:
    """
    class for the random graph generator
    """
    def __init__(self, x, y, fileName):
        """
        :param x: the number of vertices
        :param y: the number of edges
        """
        self.__graph = Graph(x)
        self.__x = x
        self.__edges = y
        self.__fileName = fileName
        # self.random_graph(x,y)

    @property
    def fileName(self):
        return self.__fileName

    def random_graph(self, x, y):
        added_edges = 0
        if y > x * x:
            raise ValueError("You cannot have this many edges!")
        while added_edges < y:
            n = random.randrange(0, x)
            m = random.randrange(0, x)
            cost = random.randrange(0, 200)

            try:
                self.__graph.add_edge(n, m, cost)
                added_edges += 1
            except MyException:
                pass
        self.store()

    def store(self):
        try:
            file = open(self.__fileName, "w")
        except IOError:
            raise MyException("An error occurred")
        g = str(self.__x) + " "
        g += str(self.__edges) + "\n"
        file.write(g)
        for k in self.__graph.dcosts():
            g = str(k[0]) + " "
            g += str(k[1]) + " "
            g += str(self.__graph.retrieve_cost(k[0], k[1])) + "\n"
            file.write(g)

    def print_graph(self):
        for k in self.__graph.dcosts():
            print(k[0], k[1], self.__graph.retrieve_cost(k[0], k[1]), sep=" ")


g = RandomGraph(6, 40, "random_graph2.txt")
try:
    g.random_graph(6, 40)
except ValueError:
    try:
        file = open(g.fileName, "w")
    except IOError:
        raise MyException("An error occurred")
    file.write("You cannot have this many edges!")
