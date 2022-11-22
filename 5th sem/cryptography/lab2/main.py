# The number of generators of a cyclic group of order 'n' is the number
# of elements less than n but greater than or equal to 1, which are also
# coprime to n.

def gcd(a, b):
    # if one of the numbers is 0, gcd is the other number and stops the algorithm
    if a == 0:
        return b
    if b == 0:
        return a

    # save the reminder of the division of the given numbers as long as the second number is greater than 0
    while b:
        r = a % b
        a = b
        b = r
    return a


def generators(n):
    # list with all elements from Zn
    elements = []
    # list of orders of elements
    ord = []
    # list of result elements
    result = []
    # add elements to elements list (from 0 to n)
    for i in range(n):
        elements.append(i)

    print("The elements are: \n", elements)

    # go through the list of elements and if there is an element that is coprime to n it added to the result list
    # used permutations to do that by determining the order of the group. If order == n, then the number is a generator
    for i in range(len(elements)):
        k = 1
        while True:
            val = elements[i] * k
            # check if there exists a k such that (elem[i] * k) % n == 0
            if val % n == 0:
                # save the orders for each number
                ord.append(k)
                print("order(" + str(elements[i]) + ") = " + str(k))
                break
            k += 1

    for i in range(len(ord)):
        # if the order == n then the number is a generator and is added to the result list
        if ord[i] == n:
            result.append(i)

    return result


print("generators: ", generators(6))
