import random


class Graph:

    def __init__(self, vertices):
        self.__vertices = vertices  # No. of vertices
        self.graph = {}
        self.edges = []
        for i in range(self.__vertices):
            self.graph[i] = []

    def parseX(self):
        """
        :return: a copy of all the vertex keys
        """
        return list(self.graph.keys())

    def get_number_of_vertices(self):
        """
        :return: the number of vertices of the graph
        """
        return len(self.parseX())

    def is_edge(self, x, y):
        """
        :param x: start of the edge
        :param y: end of the edge
        :return: true if there is an edge from x to y, false otherwise
        """
        if int(x) > int(self.get_number_of_vertices()) - 1 or int(y) > int(self.get_number_of_vertices()) - 1 or int(x) < 0 or int(y) < 0:
            raise Exception("No such pair of vertices in the graph.")
        return [int(x), int(y)] in self.edges

    # function to add an edge to graph
    def addEdge(self, start, end):
        if self.is_edge(start, end):
            raise Exception("this edge already exists")
        self.graph[end].append(start)
        ed = [start, end]
        self.edges.append(ed)

    # Use BFS to check path between s and d
    def BFS(self, start, end):
        # Mark all the vertices as not visited
        visited = [False] * self.__vertices

        prev = {}
        dist = {}
        # Create a queue for BFS
        queue = [end]
        # Mark the source node as visited and enqueue it
        visited[end] = True
        dist[end] = 0

        while len(queue) != 0:
            # Dequeue a vertex from queue
            n = queue.pop(0)
            # If this adjacent node is the destination node,
            # then return true
            if n == start:
                path = [start]
                while path[len(path) - 1] != end:
                    path.append(prev[path[len(path) - 1]])
                path.reverse()
                print(path)
                return dist[start], path

        #  Else, continue to do BFS
            for i in self.graph[n]:
                if visited[i] is False:
                    queue.append(i)
                    visited[i] = True
                    dist[i] = dist[n] + 1
                    prev[i] = n

        # If BFS is complete without visiting start
        return False, []

    def randomGraph(self, x, y):
        added_edges = 0
        if y > x * x:
            raise ValueError("You cannot have this many edges!")
        while added_edges < y:
            n = random.randrange(0, x)
            m = random.randrange(0, x)

            try:
                self.addEdge(n, m)
                added_edges += 1
            except Exception:
                pass

    def print_graph(self):
        for k in self.edges:
            print(k[0], k[1], sep=" ")

        print(self.graph)


g = Graph(8)
g.addEdge(0, 3)
g.addEdge(1, 0)
g.addEdge(1, 2)
g.addEdge(1, 4)
g.addEdge(2, 7)
g.addEdge(3, 4)
g.addEdge(3, 5)
g.addEdge(4, 3)
g.addEdge(4, 6)
g.addEdge(5, 6)
g.addEdge(6, 7)

# u = 1
# v = 7
#
# x, path = g.BFS(u, v)
#
# if x is not False:
#     print("There is a path from %d to %d\n length: " % (u, v),  x, path)
# else:
#     print("There is no path from %d to %d" % (u, v))


if __name__ == "__main__":

    done = 1
    while done:
        print("\n1. Generate random graph\n2. Print graph\n3. Find the lowest length path between 2 vertices\n0. Exit")
        choice = int(input(">> "))
        if choice == 2:
            g.print_graph()
        elif choice == 1:
            print("Generate a random graph")
            n = int(input("Number of vertices: "))

            m = int(input("Number of edges: "))
            g = Graph(n)
            g.randomGraph(n, m)
        elif choice == 3:
            start = int(input("start: "))
            end = int(input("end: "))
            x, path = g.BFS(start, end)
            if x is not False:
                print("There is a path from %d to %d" % (start, end))
                print("length: ", x)
                print("path: ", str(path)[1:-1])
                # for i in path:
                #     print(i, sep=" ")
            else:
                print("There is no path from %d to %d" % (start, end))
        elif choice == 0:
            done = 0
        else:
            print("Invalid input!")

