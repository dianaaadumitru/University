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
        if int(x) > int(self.get_number_of_vertices()) - 1 or int(y) > int(self.get_number_of_vertices()) - 1 or int(
                x) < 0 or int(y) < 0:
            raise Exception("No such pair of vertices in the graph.")
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

    def Ford(self, start, stop):
        dist = {}
        prev = {}
        for x in self.parseX():
            dist[x] = float("Inf")

        dist[start] = 0
        changed = True
        while changed:
            changed = False
            for (x, y) in self.__costs:
                if dist[x] != float("Inf") and dist[y] > dist[x] + self.__costs[(x, y)]:
                    dist[y] = dist[x] + self.__costs[(x, y)]
                    prev[y] = x
                    changed = True

        if stop not in prev.keys():
            return False, -1, [], {}

        path = [stop]
        while path[len(path) - 1] != start:
            path.append(prev[path[len(path) - 1]])
        path.reverse()
        return True, dist[stop], path


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
#
# print(g.Ford(3, 0))
