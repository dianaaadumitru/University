import math

from exception import MyException
from menu import print_menu
from graph import Graph
from random_graph import RandomGraph


class Console:
    def __init__(self, fileName, fileNameModify):
        self.__fileName = fileName
        self.__fileNameModify = fileNameModify

    def load_from_file(self):
        try:
            file = open(self.__fileName, "r")
            first_line = file.readline()
            first_line = first_line.strip().split()
            vertices, edges = int(first_line[0]), int(first_line[1])
            self.__graph = Graph(vertices)
            for times in range(edges):
                line = file.readline()
                line = line.strip().split()
                x, y, cost = int(line[0]), int(line[1]), int(line[2])
                self.__graph.add_edge(x, y, cost)
            file.close()
            print("Graph loaded.")
        except IOError:
            raise MyException("File Reading Error")

    def store(self):
        try:
            file = open(self.__fileNameModify, "w")
        except IOError:
            raise MyException("An error occurred")
        g = str(self.__graph.get_number_of_vertices()) + " "
        g += str(self.__graph.get_number_of_edges()) + "\n"
        file.write(g)
        for x in self.__graph.dcosts():
            g = str(x[0]) + " "
            g += str(x[1]) + " "
            g += str(self.__graph.retrieve_cost(x[0], x[1])) + "\n"
            file.write(g)

    def get_number_of_vertices(self):
        print(self.__graph.get_number_of_vertices())

    def print_vertices(self):
        print(self.__graph.parseX())

    def is_edge(self):
        x = input("start: ")
        y = input("end: ")
        if self.__graph.is_edge(x, y):
            print("there is an edge from " + str(x) + " to " + str(y))
        else:
            print("there is no edge from " + str(x) + " to " + str(y))

    def degrees(self):
        vertex = int(input("vertex: "))
        print("in degree: " + str(self.__graph.in_degree(vertex)))
        print("out degree: " + str(self.__graph.out_degree(vertex)))

    def change_cost(self):
        x = int(input("start: "))
        y = int(input("end: "))
        if self.__graph.is_edge(x, y):
            cost = int(input("new cost: "))
            old_cost = self.__graph.retrieve_cost(x, y)
            self.__graph.change_cost(x, y, cost)
            print("the cost of the edge from " + str(x) + " to " + str(y) + ' has changed from ' + str(old_cost) + " to " + str(cost))
        else:
            print("there is no edge from " + str(x) + " to " + str(y))

    def add_edge(self):
        x = int(input("start: "))
        y = int(input("end: "))
        cost = int(input("cost: "))
        self.__graph.add_edge(x, y, cost)
        print("new edge added to the graph")

    def remove_edge(self):
        x = int(input("start: "))
        y = int(input("end: "))
        if self.__graph.is_edge(x, y):
            self.__graph.remove_edge(x, y)
            print("edge removed")
        else:
            print("there is no edge from " + str(x) + " to " + str(y))

    def add_vertex(self):
        x = int(input("New vertex: "))
        self.__graph.add_vertex(x)
        print("new vertex added to the graph")

    def remove_vertex(self):
        vertex = int(input("the vertex you want to remove: "))
        self.__graph.remove_vertex(vertex)
        print("vertex removed")

    def isolated_vertices(self):
        print(self.__graph.isolated_vertices())

    def store_to_file(self):
        self.store()
        print("New graph saved to file!")

    def print_graph(self):
        for x in self.__graph.dcosts():
            print(x[0], x[1], self.__graph.retrieve_cost(x[0], x[1]), sep=" ")

    def copy_graph(self):
        print("Copying the graph...")
        self.__graph_copy = self.__graph.copy_graph()
        print("The graph is now copied")

    def print_copy_graph(self):
        for x in self.__graph_copy.dcosts():
            print(x[0], x[1], self.__graph_copy.retrieve_cost(x[0], x[1]), sep=" ")

    def print_inbound(self):
        x = input("Vertex: ")
        x = int(x)
        for i in self.__graph.parse_iterable_in(x):
            print(i)

    def print_outbound(self):
        x = input("Vertex: ")
        x = int(x)
        for i in self.__graph.parse_iterable_out(x):
            print(i)

    @staticmethod
    def random_graph1():
        g = RandomGraph(7, 20, "random_graph1.txt")
        try:
            g.random_graph(7, 20)
        except ValueError as e:
            print(e)
            try:
                file = open(g.fileName, "w")
            except IOError:
                raise MyException("An error occurred")
            file.write("You cannot have this many edges!")
        print("Graph loaded!")

    @staticmethod
    def random_graph2():
        g = RandomGraph(6, 40, "random_graph2.txt")
        try:
            g.random_graph(6, 40)
        except ValueError as e:
            print(e)
            try:
                file = open(g.fileName, "w")
            except IOError:
                raise MyException("An error occurred")
            file.write("You cannot have this many edges!")

    @staticmethod
    def random_graph():
        n = int(input("Number of vertices: "))
        m = int(input("Number of edges: "))
        g = RandomGraph(n, m, "random_graph.txt")
        try:
            g.random_graph(n, m)
        except ValueError as e:
            print(e)
            try:
                file = open(g.fileName, "w")
            except IOError:
                raise MyException("An error occurred")
            file.write("You cannot have this many edges!")
        print("Graph loaded!")

    def BFS(self):
        start = int(input("start: "))
        end = int(input("end: "))
        x, path = self.__graph.BFS(start, end)
        if x is not False:
            print("There is a path from %d to %d" % (start, end))
            print("length: ", x)
            print("path: ", str(path)[1:-1])
        else:
            print("There is no path from %d to %d" % (start, end))

    def fordUI(self):
        start = int(input("start: "))
        end = int(input("end: "))
        ok, c, path, nr = self.__graph.Ford(start, end)
        if ok is False:
            print("There is no path from %d to %d" % (start, end))
        else:
            if nr == 0:
                print("There is a cycle from %d to %d" % (start, end))
                print("cost: ", c)
                print("path: ", str(path)[1:-1])
            else:
                print("There are negative cycles")

    def DAGUI(self):
        sortedDAG = self.__graph.DAG()
        if sortedDAG != []:
            print(str(sortedDAG)[1:-1])
            print("Would you like to see the highest cost path between two vertices?\n 1. yes\n 2. No")
            choice = int(input(">> "))
            if choice == 1:
                start = int(input("start: "))
                stop = int(input("end: "))
                ok, cost, path = self.__graph.highestCostPath(sortedDAG, start, stop)
                if ok is False:
                    print("There is no path!")
                    return
                else:
                    print("cost: ", cost)
                    print("path: ", str(path)[1:-1])
        else:
            print("Graph is not DAG")

    def Hamiltonian(self):
        result, sum = self.__graph.Hamiltonian()
        if len(result) == 0:
            raise  MyException("There is no Hamiltonian cycle!")
        print("The minimum cost Hamiltonian cycle is: ", str(result)[1:-1], sep=' ')
        print("having the cost: ", sum, sep=' ')

    def start(self):
        command_dict = {'0': self.load_from_file,
                        '-1': self.store_to_file,
                        '1': self.get_number_of_vertices,
                        '2': self.print_vertices,
                        '3': self.is_edge,
                        '4': self.degrees,
                        '5': self.change_cost,
                        '6': self.isolated_vertices,
                        '7': self.add_edge,
                        '8': self.remove_edge,
                        '9': self.add_vertex,
                        '10': self.remove_vertex,
                        '11': self.print_graph,
                        '12': self.copy_graph,
                        '13': self.print_copy_graph,
                        '14': self.print_inbound,
                        '15': self.print_outbound,
                        '16': self.random_graph1,
                        '17': self.random_graph2,
                        '18': self.random_graph,
                        '19': self.BFS,
                        '20': self.fordUI,
                        '21': self.DAGUI,
                        '22': self.Hamiltonian
                        }
        self.load_from_file()
        done = False
        while not done:
            print_menu()
            cmd = input(">> ")
            if cmd in command_dict:
                try:
                    command_dict[cmd]()
                except MyException as excep:
                    print(excep)
            elif cmd == "exit":
                done = True
            else:
                print("invalid input")


c = Console("graphA5_2.txt", "graph10k_modify.txt")
c.start()
