from timeit import default_timer


def gcd1(a, b):
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


def gcd2(a, b):
    # if one of the numbers is 0, gcd is the other number and stops the algorithm
    if a == 0:
        return b
    if b == 0:
        return a

    # as long as the 2 numbers have different values subtract from the greater number the smaller one
    while a != b:
        if a > b:
            a = a - b
        else:
            b = b - a

    return a


def gcd3(a, b):
    # if one of the numbers is 0, gcd is the other number and stops the algorithm
    if a == 0:
        return b
    if b == 0:
        return a

    # take the minimum from the 2 numbers
    r = min(a, b)

    # when a number smaller or equal with the min between the 2 parameters that divides both number is found
    # this function return it
    while r:
        if a % r == 0 and b % r == 0:
            break
        r -= 1

    return r


def main():
    data = [[5, 3], [10, 4], [0, 100], [100, 0], [254632, 123456], [7283648, 2938],
            [23648725342986, 238542834], [100000, 1000004000], [1234567890, 9876543210], [123, 1234]]
    for d in data:
        print("starting test with {} and {}".format(d[0], d[1]))
        start1 = default_timer()
        print(start1)
        r1 = gcd1(d[0], d[1])
        end1 = default_timer()
        print(end1)
        print("gcd is {}".format(r1))
        print("time is {} seconds".format(end1 - start1))

        start2 = default_timer()
        print(start1)
        r2 = gcd2(d[0], d[1])
        end2 = default_timer()
        print(end2)
        print("gcd is {}".format(r2))
        print("time is {} seconds".format(end2 - start2))

        start3 = default_timer()
        print(start3)
        r3 = gcd3(d[0], d[1])
        end3 = default_timer()
        print(end3)
        print("gcd is {}".format(r3))
        print("time is {} seconds\n".format(end3 - start3))


main()

