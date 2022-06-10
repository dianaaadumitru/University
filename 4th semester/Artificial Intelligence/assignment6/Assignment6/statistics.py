from matplotlib import pyplot


def plot_data(inputs, outputs, colors):
    for color in colors:
        ox = []
        oy = []
        for index, (x, y) in enumerate(inputs):
            if outputs[index] == color:
                ox.append(x)
                oy.append(y)

        pyplot.scatter(ox, oy, color=colors[color], marker=".", s=10)


def plot_centroids(centroids, color="black"):
    ox = []
    oy = []
    for x, y in centroids:
        ox.append(x)
        oy.append(y)

    return pyplot.scatter(ox, oy, color=color, marker="X", s=100)


def plot_computed_data(centroid_to_inputs, centroid_to_class, colors):
    for centroid, inputs in centroid_to_inputs.items():
        ox = []
        oy = []
        for x, y in inputs:
            ox.append(x)
            oy.append(y)

        pyplot.scatter(ox, oy, color=colors[centroid_to_class[centroid]], marker=".", s=10)

    return plot_centroids(centroid_to_inputs.keys())


def generate_centroid_to_class(inp):
    centroids = list(inp.keys())

    leftMost = min(centroids, key=lambda x: x[0])
    centroids.remove(leftMost)

    rightMost = max(centroids, key=lambda x: x[0])
    centroids.remove(rightMost)

    topMost= max(centroids, key=lambda x: x[1])
    centroids.remove(topMost)

    bottomMost = centroids[0]
    return {leftMost: 'A', topMost: 'B', rightMost: 'C', bottomMost: 'D'}


def plot_all(centroid_to_inputs, colors):
    centroid_to_class = generate_centroid_to_class(centroid_to_inputs)
    points = plot_computed_data(centroid_to_inputs, centroid_to_class, colors)
    pyplot.title('Computed outputs')
    pyplot.pause(0.5)

    return points