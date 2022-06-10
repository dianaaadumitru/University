from random import uniform

import numpy
from constants import *
from statistics import generate_centroid_to_class, plot_all


def getDomain(inputs):
    min1 = MAX
    max1 = MIN
    min2 = MAX
    max2 = MIN

    for value1, value2 in inputs:
        if value1 < min1:
            min1 = value1
        if value1 > max1:
            max1 = value1
        if value2 < min2:
            min2 = value2
        if value2 > max2:
            max2 = value2

    return (min1, min2), (max1, max2)


def generateCentroids(domain, number_of_centroids=4):
    centroids = set()
    (min1, min2), (max1, max2) = getDomain(domain)

    while len(centroids) < number_of_centroids:
        centroids.add((uniform(min1, max1), uniform(min2, max2)))

    return centroids


def euclideanDistance(left_position, right_position):
    return numpy.linalg.norm(numpy.array(left_position) - numpy.array(right_position))


def evaluateClassification(real, computed, label_names, count):
    accuracyCount = 0

    for realLabel, computedLabel in zip(real, computed):
        if realLabel == computedLabel:
            accuracyCount += 1
    accuracy = accuracyCount / count

    precisions = {}
    rappelData = {}
    score = {}

    for label in label_names:
        correctCount = 0
        precisionCount = 0
        rappelCount = 0

        for realLabel, computedLabel in zip(real, computed):

            if realLabel == computedLabel == label:
                correctCount += 1

            if computedLabel == label:
                precisionCount += 1

            if realLabel == label:
                rappelCount += 1

        precision = correctCount / precisionCount
        rappel = correctCount / rappelCount

        precisions[label] = precision
        rappelData[label] = rappel
        score[label] = 2 * precision * rappel / (precision + rappel)

    return {"accuracy": accuracy, "precision": precisions, "rappel": rappelData, "score": score}


def displayResults(centroid_to_inputs, inputs, outputs):
    centroid_to_class = generate_centroid_to_class(centroid_to_inputs)

    input_to_computed = {}
    for centroid, sets in centroid_to_inputs.items():
        for row in sets:
            input_to_computed[row] = centroid_to_class[centroid]
    computed_labels = [input_to_computed[row] for row in inputs]

    for val, result in evaluateClassification(outputs, computed_labels, COLORS.keys(), len(inputs)).items():
        print(f"{val}: {result}")


def solver(centroids_set, inputs, outputs):
    centroids = list(centroids_set)

    changed = True
    while changed:

        changed = False

        distancesFromInput = {}
        for row in inputs:
            distancesFromInput[row] = min(centroids, key=lambda c: euclideanDistance(row, c))

        distancesFromCentroids = {}
        newGeneratedList = []
        for centroid in centroids:
            sumX = 0
            sumY = 0
            count = 0

            distancesFromCentroids[centroid] = []
            for row, inputCentroid in distancesFromInput.items():
                if inputCentroid == centroid:
                    sumX += row[0]
                    sumY += row[1]
                    count += 1

                    distancesFromCentroids[centroid].append(row)
            newGeneratedList.append((sumX / count, sumY / count))

        for newCentroid in newGeneratedList:
            changed = changed or newCentroid not in centroids

        if changed:
            centroids = newGeneratedList
        else:
            displayResults(distancesFromCentroids, inputs, outputs)

        points = plot_all(distancesFromCentroids, COLORS)
        if changed:
            points.remove()


def runSolver(inputs, outputs):
    centroids = generateCentroids(inputs)
    solver(centroids, inputs, outputs)
