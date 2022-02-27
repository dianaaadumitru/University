# Determine if a certain element is member in a list


class Nod:
    def __init__(self, e):
        self.e = e
        self.urm = None


class Lista:
    def __init__(self):
        self.prim = None


'''
crearea unei liste din valori citite pana la 0
'''


def creareLista():
    lista = Lista()
    lista.prim = creareLista_rec()
    return lista


def creareLista_rec():
    x = int(input("x="))
    if x == 0:
        return None
    else:
        nod = Nod(x)
        nod.urm = creareLista_rec()
        return nod


def check(nod, elem):
    if nod is None:
        return 0
    if nod.e == elem:
        return 1
    else:
        return check(nod.urm, elem)


def isMember(_list, elem):
    return check(_list.prim, elem)


def main1():
    _list = creareLista()
    elem = int(input("Element you are searching for: "))
    if isMember(_list, elem) == 1:
        print("the element is a member in the list")
    else:
        print("the element is NOT a member in the list")


# Determine the length of a list


def lenghthList(nod):
    if nod is None:
        return 0
    else:
        return 1 + lenghthList(nod.urm)


def length(_list):
    return lenghthList(_list.prim)


def main2():
    _list = creareLista()
    # _list = list(map(int, input("Enter the numbers : ").strip().split()))
    print("List length is: ", length(_list))


def main():
    # main1()
    main2()


main()
