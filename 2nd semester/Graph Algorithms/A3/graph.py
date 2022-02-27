# class Graph:
#     def __init__(self, vertices):
#         self.__vertices = vertices
#         self.__costs = {}
#         self.__dictin = {}
#         for i in range(vertices):
#             self.__dictin[i] = []
#
#     def addEdge(self, x, y, cost):
#         self.__costs[(x, y)] = cost
#         self.__dictin[y].append(x)
#
#     def printGraph(self):
#         print(self.__vertices)
#         for (x, y) in self.__costs:
#             print(x, y, self.__costs[(x, y)])
#
#         # print(self.__dictin)
#
#     def parseX(self):
#         """
#         :return: a copy of all the vertex keys
#         """
#         return list(self.__dictin.keys())
#
#     def Ford(self, start, stop):
#         dist = {}
#         prev = {}
#         for x in self.parseX():
#             dist[x] = "inf"
#
#         dist[start] = 0
#         changed = True
#         while changed:
#             changed = False
#             for (x, y) in self.__costs:
#                 if dist[x] != float("inf") and dist[y] > dist[x] + self.__costs[(x, y)]:
#                     dist[y] = dist[x] + self.__costs[(x, y)]
#                     prev[y] = x
#                     changed = True
#
#         return prev

class Graph:

    def __init__(self, vertices):
        self.V = vertices  # No. of vertices
        # self.graph = []
        self.__costs = {}

    # function to add an edge to graph
    def addEdge(self, u, v, w):
        # self.graph.append([u, v, w])
        self.__costs[(u, v)] = w

    # utility function used to print the solution
    def printArr(self, dist):
        print("Vertex Distance from Source")
        for i in range(self.V):
            print("{0}\t\t{1}".format(i, dist[i]))

    # The main function that finds shortest distances from src to
    # all other vertices using Bellman-Ford algorithm. The function
    # also detects negative weight cycle
    def BellmanFord(self, src, dest):

        # Step 1: Initialize distances from src to all other vertices
        # as INFINITE
        dist = [float("Inf")] * self.V
        dist[src] = 0
        prev = {}

        # Update dist value and parent index of the adjacent vertices of
        # the picked vertex. Consider only those vertices which are still in
        # queue
        for _ in range(self.V - 1):
            for (u, v) in self.__costs:
                if dist[u] != float("Inf") and dist[u] + self.__costs[(u, v)] < dist[v]:
                    dist[v] = dist[u] + self.__costs[(u, v)]
                    prev[u] = v

        # Step 3: check for negative-weight cycles. The above step
        # guarantees shortest distances if graph doesn't contain
        # negative weight cycle. If we get a shorter path, then there
        # is a cycle.

        # for (u, v) in self.__costs:
        #     if dist[u] != float("Inf") and dist[u] + self.__costs[(u, v)] < dist[v]:
        #         print("Graph contains negative weight cycle")
        #         return
        # for i in range(self.V):
        if dist[dest] < 0:
            print("negative cost")
        else:
            print(dist[dest])
            path = [dest]
            print(prev)
            while path[len(path) - 1] != src:
                path.append(prev[path[len(path) - 1]])
            path.reverse()
            print(path)

        # print all distance
        # self.printArr(dist)


g = Graph(5)
g.addEdge(0, 1, -1)
g.addEdge(0, 2, 4)
g.addEdge(1, 2, 3)
g.addEdge(1, 3, 2)
g.addEdge(1, 4, 2)
g.addEdge(3, 2, 5)
g.addEdge(3, 1, 1)
g.addEdge(4, 3, -3)
g.addEdge(2, 1, 100)

g.BellmanFord(0, 2)
# print(g.Ford(0, 3))
# print(g.printGraph())