
class IterableStructure:
    """
    Class for the iterable structure
    """

    def __init__(self):
        self._index = 0
        self._list = []

    def __iter__(self):
        """
        Returns an iterator over this data structure
        """
        self._index = 0
        return self

    def __next__(self):
        """
        structure method to get the next element from itself
        """
        if self._index > len(self._list) - 1:
            raise StopIteration
        else:
            x = self._list[self._index]
            self._index += 1
        return x

    def __len__(self):
        return len(self._list)

    def __getitem__(self, index):
        """
        gets the item from the list at the positon index
        :param index:
        :return:
        """
        return self._list[index]

    def __setitem__(self, index, value):
        """
        sets the item from the list at the position index to value
        :param index:
        :param value:
        :return:
        """

        self._list[index] = value

    def __delitem__(self, index):
        """
        deletes the item at position index
        :param index:
        :return:
        """
        del self._list[index]

    def append(self, element):
        """
        appends an element into the list
        :param element:
        :return:
        """
        self._list.append(element)

    def getList(self):
        return self._list[:]


def sort_list(_list, function):
    """
    Gnome Sort
    Three steps:
        1. If you are at the start of the array then go to the right element (from list[0] to list[1])
        2. If the current list element is smaller or equal to the previous list element then go one step right
        3. If the current array element is smaller than the previous array element then swap these two elements and go one step backwards

    list: the list to be sorted
    :param _list: the sorted list
    :param function: should return true if the first parameter is smaller then the first one
    :return:
    """
    i = 1
    if function is None:
        return
    while i < len(_list):
        if i > 0 and not function(_list[i - 1], _list[i]):
            _list[i - 1], _list[i] = _list[i], _list[i - 1]
            i -= 1
        else:
            i += 1


def filter_list(_list, function):
    """
    The method simply filters the elements of a list by a given function passed as a parameter. This is done by creating a new list and returning it.
    :param _list: the filtered list
    :param function: should return true if the condition applies
    :return:
    """
    if function is None:
        return _list
    new_list = []
    i = 0
    while i < len(_list):
        if function(_list[i]):
            new_list.append(_list[i])

        i += 1
    return new_list
