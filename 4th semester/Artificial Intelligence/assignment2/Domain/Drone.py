

class Drone:
    def __init__(self, x, y):
        self.x = x
        self.y = y

        # stack and visited used for DFS
        self.stack = [(self.x, self.y)]
        self.visited = set()
        self.visited.add((self.x, self.y))

    def getX(self):
        return self.x

    def getY(self):
        return self.y

    def getStack(self):
        return self.stack

    def getVisited(self):
        return self.visited
