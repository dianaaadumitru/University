from graph4 import Graph


class Main:
    def __init__(self, fileName):
        self.__fileName = fileName

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
                self.__graph.addEdge(x, y, cost)
            file.close()
            print("Graph loaded.")
        except IOError:
            raise Exception("File Reading Error")

    def print_graph(self):
        for (x, y) in self.__graph.costs:
            print(x, y, self.__graph.costs[(x, y)], sep=" ")

    def add_vertex(self):
        x = int(input("New vertex: "))
        self.__graph.add_vertex(x)
        print("new vertex added to the graph")

    def fordUI(self):
        start = int(input("start: "))
        end = int(input("end: "))
        ok, c, path = self.__graph.Ford(start, end)
        if ok is False:
            print("There is no path from %d to %d" % (start, end))
        else:
            print("There is a cycle from %d to %d" % (start, end))
            print("cost: ", c)
            print("path: ", str(path)[1:-1])
            # else:
            #     print("negative cycle from %d to %d" % (start, end))

    def run(self):
        done = 1
        while done:
            print("\n1. Generate random graph\n2. Read from file\n3. Print graph\n4. Find the lowest cost walk between 2 vertices\n5.Add vertex\n0. Exit")
            choice = input(">> ")
            if choice == "3":
                self.print_graph()
            elif choice == "2":
                self.load_from_file()
            elif choice == "1":
                print("Generate a random graph")
                n = int(input("Number of vertices: "))
                self.__graph = Graph(n)
                m = int(input("Number of edges: "))
                self.__graph.randomGraph(n, m)
            elif choice == "4":
                self.fordUI()
            elif choice == "5":
                self.add_vertex()
            elif choice == "0":
                done = 0
            else:
                print("Invalid input!")


m = Main("graph100k.txt")
m.run()
