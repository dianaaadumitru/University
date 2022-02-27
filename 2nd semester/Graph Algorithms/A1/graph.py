import itertools
import math

from exception import MyException
import copy


class Graph:
    def __init__(self, vertices):
        self.__dictin = {}
        self.__dictout = {}
        self.__costs = {}

        self.__vertices = vertices  # No. of vertices
        self.edges = []

        for i in range(vertices):
            self.__dictin[i] = []
            self.__dictout[i] = []

    def parseX(self):
        """
        :return: a copy of all the vertex keys
        """
        return list(self.__dictin.keys())

    def parse_iterable_in(self, x):
        """
        :param x: the vertex
        :return: a list of in neighbours of x
        """
        try:
            return list(self.__dictin[x])
        except KeyError:
            raise MyException("no such vertex")

    def parse_iterable_out(self, x):
        """
        :param x: the vertex
        :return: a list of all out neighbours of x
        """
        try:
            return list(self.__dictout[x])
        except KeyError:
            raise MyException("no such vertex")

    def get_number_of_vertices(self):
        """
        :return: the number of vertices of the graph
        """
        return len(self.parseX())

    def get_number_of_edges(self):
        return len(self.__costs)

    def is_edge(self, x, y):
        """
        :param x: start of the edge
        :param y: end of the edge
        :return: true if there is an edge from x to y, false otherwise
        """
        # if int(x) > int(self.get_number_of_vertices()) - 1 or int(y) > int(self.get_number_of_vertices()) - 1 or int(
        #         x) < 0 or int(y) < 0:
        #     raise MyException("No such pair of vertices in the graph.")
        return [int(x), int(y)] in self.edges

    def add_edge(self, x, y, cost):
        """
        add an edge (x,y) having the cost, 'cost' to the graph
        precondition: the edge must not exist in the graph and the vertices must be valid
                      in case we already have that edge in the graph, or the vertices are not valid  the error is handled and the user is informed
        """
        # if self.is_edge(x, y):
        #     raise MyException("this edge already exists")

        self.__dictin[y].append(x)
        self.__dictout[x].append(y)
        self.__costs[(x, y)] = cost
        self.edges.append([x, y])

    def in_degree(self, vertex):
        """
        :param vertex:
        :return: the in degree of a given vertex
        precondition: x needs to be a valid vertex in the graph, in case it isn't, the error is handled and the user is informed
        """
        return len(self.parse_iterable_in(vertex))

    def out_degree(self, vertex):
        """
        :param vertex:
        :return: the out degree of a given vertex
        precondition: x needs to be a valid vertex in the graph, in case it isn't, the error is handled and the user is informed
        """
        return len(self.parse_iterable_out(vertex))

    def change_cost(self, x, y, value):
        """
        modify the cost of a given edge
        precondition: the edge must exist, otherwise errors are handled
        """
        self.__costs[(x, y)] = value

    def retrieve_cost(self, x, y):
        """
        :return: returns the cost of the edge (x, y)
        precondition: (x, y) must exist, if it doesn't errors are handled and the user is informed
        """
        if self.is_edge(x, y):
            return self.__costs[(x, y)]

    def remove_edge(self, x, y):
        """
        remove the edge (x,y)
        precondition: (x,y) needs to be a valid edge in the graph, if it isn't, the error is handled and the user is informed
        """
        self.__dictin[y].remove(x)
        self.__dictout[x].remove(y)
        del self.__costs[(x, y)]

    def add_vertex(self, x):
        """
        add a new vertex to the graph
        :return:
        """
        if x not in self.parseX():
            self.__dictout[x] = []
            self.__dictin[x] = []
        else:
            raise MyException("this vertex already exists")

    def remove_vertex(self, vertex):
        """
        remove a given vertex
        precondition: the vertex must exist in the graph, it it doesn't, the error is handled and the user is informed
        """
        if vertex not in self.parseX():
            raise MyException("no such vertex in the graph")
        x = self.get_number_of_vertices()
        for i in range(x):
            if (i, vertex) in self.__costs:
                del self.__costs[(i, vertex)]
            if (vertex, i) in self.__costs:
                del self.__costs[(vertex, i)]
            if vertex in self.__dictin[i]:
                self.__dictin[i].remove(vertex)
            if vertex in self.__dictout[i]:
                self.__dictout[i].remove(vertex)

        del self.__dictin[vertex]
        del self.__dictout[vertex]

    def isolated_vertices(self):
        vertices = []
        for x in self.parseX():
            if self.__dictin[x] == [] and self.__dictout[x] == []:
                vertices.append(x)
        return vertices[:]

    def din(self):
        return self.__dictin

    def dout(self):
        return self.__dictout

    def dcosts(self):
        return self.__costs

    def copy_graph(self):
        """
        returns a copy of the graph
        :return:
        """
        new_graph = Graph(10)
        new_graph.__dictin = copy.deepcopy(self.__dictin)
        new_graph.__dictout = copy.deepcopy(self.__dictout)
        new_graph.__costs = copy.deepcopy(self.__costs)
        return new_graph

        # Use BFS to check path between s and d

    def BFS(self, start, end):
        # Mark all the vertices as not visited
        visited = [False] * len(self.parseX())
        # visited = []
        # for a in self.parseX():
        #     print(a)
        #     visited[a] = False
        next_dict = {}
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
                    path.append(next_dict[path[len(path) - 1]])
                # path.reverse()
                return dist[start], path

            #  Else, continue to do BFS
            for i in self.__dictin[n]:
                if visited[i] is False:
                    queue.append(i)
                    visited[i] = True
                    dist[i] = dist[n] + 1
                    next_dict[i] = n

        # If BFS is complete without visiting start
        return False, []

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
        # relax all edges
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
            return ok, -1, [], 0
        nr = 0
        for (x, y) in self.edges:
            if dist[y] >= dist[x] + self.__costs[(x, y)] and (dist[x] + self.__costs[(x, y)] < 0):
                nr += 1

        # find the path using prev dictionary
        path = [stop]
        while path[len(path) - 1] != start:
            path.append(prev[path[len(path) - 1]])
        path.reverse()
        return ok, dist[stop], path, nr

    # A recursive function used by DAG
    def topologicalSortWithDFS(self, x, sorted, fullyProcessed, inProcess):
        inProcess.add(x)
        for inBoundNeighbour in self.parse_iterable_in(x):
            if inBoundNeighbour in inProcess:
                return False
            else:
                # Recur for all the vertices adjacent to this vertex
                if inBoundNeighbour not in fullyProcessed:
                    ok = self.topologicalSortWithDFS(inBoundNeighbour, sorted, fullyProcessed, inProcess)
                    if not ok:
                        return False
        inProcess.remove(x)
        # Push current vertex to sorted which stores result
        sorted.append(x)
        fullyProcessed.add(x)
        return True

    def DAG(self):
        sorted = []
        fullyProcessed = set()
        inProcess = set()
        # print("sorted: ", sorted)
        # print("fully: ", fullyProcessed)
        # print("in: ", inProcess)

        # Call the recursive helper function to store Topological
        # Sort starting from all vertices one by one
        for vertex in self.parseX():
            if vertex not in fullyProcessed:
                ok = self.topologicalSortWithDFS(vertex, sorted, fullyProcessed, inProcess)

                # if ok is False it means that the graph it's not DAG and it returns an empty list
                if not ok:
                    return []
        return sorted[:]

    def highestCostPath(self, sorted, start, stop):
        # initialize distances from start to all other vertices as infinite
        # and also prev with -1
        distances = [-math.inf] * len(sorted)
        prev = [-1] * len(sorted)
        distances[start] = 0
        for vertex in sorted:
            # stop when you gind the path with the highest cost
            if vertex == stop:
                break

            # calculates the maximal cost between 2 given vertices
            # construct prev dictionary
            for outBoundNeighbours in self.parse_iterable_out(vertex):
                if distances[outBoundNeighbours] < distances[vertex] + self.retrieve_cost(vertex, outBoundNeighbours):
                    distances[outBoundNeighbours] = distances[vertex] + self.retrieve_cost(vertex, outBoundNeighbours)
                    prev[outBoundNeighbours] = vertex

        # if there is no predecessor of the vertex stop it means that there is
        # no path from start to stop an the function returns False
        if prev[stop] == -1:
            return False, distances[stop], []
        # find the path using prev dictionary
        path = [stop]
        while path[len(path) - 1] != start:
            path.append(prev[path[len(path) - 1]])
        path.reverse()
        return True, distances[stop], path

    def Hamiltonian(self):
        listt = []
        minim = 100000 # declare the minimum a large number
        result = []
        # save all vertices in a list
        for x in self.parseX():
            listt.append(x)

        # provide all the combinations of all vertices
        for perm in itertools.permutations(listt, len(listt)):
            ok = True
            sum = 0
            if len(perm) > 2:
                k = 0
                # go through all vertices from e permutation and check if there is an edge
                # between 2 consecutive vertices or not (if it is a cycle or not)
                while k < len(perm) - 1 and ok is True:
                    if not self.is_edge(perm[k], perm[k + 1]):
                        ok = False
                    k += 1

                # check if there is an edge between the last and the first vertex from permutation
                if not self.is_edge(perm[len(perm) - 1], perm[0]):
                    ok = False

                # if the permutation is a cycle, compute the sum
                if ok:
                    for k in range(0, len(perm) - 1):
                        sum = sum + self.retrieve_cost(perm[k], perm[k + 1])

                    sum = sum +self.retrieve_cost(perm[len(perm) - 1], perm[0])

                    # save in the minim variable the minimum sum of all cycles and
                    # in result the cycle
                    if sum < minim:
                        minim = sum
                        result = perm
        return result, minim


# g = Graph(6)
# g.add_edge(0, 3, 3)
# g.add_edge(3, 1, 4)
# g.add_edge(1, 2, 5)
# g.add_edge(2, 5, 6)
# g.add_edge(5, 4, 5)
# g.add_edge(4, 0, 14)
# g.add_edge(4, 1, 2)
# g.add_edge(2, 3, 3)
# g.add_edge(3, 0, 4)
# g.add_edge(0, 5, 5)
# g.add_edge(4, 3, 6)
# print(g.Hamiltonian())
