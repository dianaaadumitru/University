import random


class Graph:
    def __init__(self, vertices):
        self.__vertices = vertices
        self.__costs = {}
        self.__dictin = {}
        self.edges = []
        for i in range(vertices):
            self.__dictin[i] = []

    @property
    def costs(self):
        return self.__costs

    def get_number_of_vertices(self):
        return len(self.parseX())

    def is_edge(self, x, y):
        # if int(x) > int(self.get_number_of_vertices()) - 1 or int(y) > int(self.get_number_of_vertices()) - 1 or int(
        #         x) < 0 or int(y) < 0:
        #     raise Exception("No such pair of vertices in the graph.")
        return [int(x), int(y)] in self.edges

    def addEdge(self, x, y, cost):
        self.__costs[(x, y)] = cost
        self.__dictin[y].append(x)
        self.edges.append([x, y])

    def randomGraph(self, x, y):
        added_edges = 0
        if y > x * x:
            raise ValueError("You cannot have this many edges!")
        while added_edges < y:
            n = random.randrange(0, x)
            m = random.randrange(0, x)
            cost = random.randrange(-50, 50)

            try:
                self.addEdge(n, m, cost)
                added_edges += 1
            except Exception:
                pass

    def parseX(self):
        """
        :return: a copy of all the vertex keys
        """
        return list(self.__dictin.keys())

    def add_vertex(self, x):
        """
        add a new vertex to the graph
        :return:
        """
        if x not in self.parseX():
            # self.__dictout[x] = []
            self.__dictin[x] = []
        else:
            raise Exception("this vertex already exists")

    def Ford(self, start, stop):
        dist = {}
        prev = {}
        # initialize distances from start to all other vertices as infinite
        # and also prev with -1
        for x in self.parseX():
            dist[x] = float("Inf")
            prev[x] = -1

        changed = True
        dist[start] = 0

        # the shortest path from a vertex to another can have at most (no. of vertices - 1) edges
        while changed:
            changed = False
            # update dist value and parent index of the adjacent vertices of the picked vertex
            # construct prev dictionary
            for (x, y) in self.__costs:
                if dist[x] != float("Inf") and dist[y] > dist[x] + self.__costs[(x, y)]:
                    dist[y] = dist[x] + self.__costs[(x, y)]
                    prev[y] = x
                    changed = True

        ok = True
        # if there is no predecessor of the vertex stop it means that there is
        # no path from start to stop an the function returns False
        if prev[stop] == -1:
            ok = False
            return ok, -1, []

        for (x, y) in self.edges:
            if dist[y] > dist[x] + self.__costs[(x, y)]:
                message = "negative cycle from " + str(x) + " to " + str(y)
                print(message)

        # find the path using prev dictionary
        path = [stop]
        while path[len(path) - 1] != start:
            path.append(prev[path[len(path) - 1]])
        path.reverse()
        return ok, dist[stop], path


# g = Graph(5)
# g.addEdge(0, 1, -1)
# g.addEdge(0, 2, 4)
# g.addEdge(1, 2, 3)
# g.addEdge(1, 3, 2)
# g.addEdge(1, 4, 2)
# g.addEdge(3, 2, 5)
# g.addEdge(3, 1, 1)
# g.addEdge(4, 3, -3)
#
# print(g.Ford(4, 2))
