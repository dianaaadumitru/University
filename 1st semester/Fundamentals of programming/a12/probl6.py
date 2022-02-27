from copy import deepcopy


def _print(_list, n):
    for i in range(n):
        if _list[i] == 0:
            print("(", end="")
        elif _list[i] == 1:
            print(")", end="")
    print("\n")


def valid(_list, index, n):
    open_p = 0
    close_p = 0
    for i in range(index):
        if _list[i] == 0:
            open_p += 1
        elif _list[i] == 1:
            close_p += 1

        if open_p < close_p or open_p > n / 2:
            return False
    if _list[n - 1] == 0:
        return False
    if _list[0] == 1:
        return False
    return True


def back_tracking_rec(_list, index, n):
    l = deepcopy(_list)

    for i in [0, 1]:
        l[index] = i
        if index == n - 1:
            if valid(l, n, n):
                _print(l, n)
        elif index < n - 1:
            back_tracking_rec(l, index + 1, n)


def back_tracking_iter(_list, index, n):
    _list[index] = -1

    while index > -1:
        candidate = False
        while not candidate and _list[index] < 1:
            _list[index] += 1
            candidate = valid(_list, index, n)

        if candidate is False:
            index -= 1
        else:
            if index == n-1:
                _print(_list, n)
            else:
                index += 1
                _list[index] = -1


def start():
    try:
        n = int(input("Enter a number "))
        if n % 2 == 1:
            raise Exception("you should choose an even number")

        _list = [2] * n
        choice = input("Choose method: recursive(rec)/ iterative(iter): ")
        if choice == "rec":
            back_tracking_rec(_list, 0, n)
        elif choice == "iter":
            back_tracking_iter(_list, 0, n)
        else:
            print("Bad command")

    except Exception as e:
        print(e)


start()
