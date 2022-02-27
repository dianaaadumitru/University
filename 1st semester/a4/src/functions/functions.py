"""
Functions that implement program features. They should call each other, or other functions from the domain
"""


from src.domain.entity import verif_if_it_is_a_number, get_real, get_imag, find_by_position, create_number, search_for_number
import math


def insert_number(number_list, number, pos):
    """
    inserts a number at a given position
    :param number_list: the list of numbers
    :param number: the number we want to insert
    :param pos: the position where we want to insert the number
    :return: -
    """
    if int(pos) > int(len(number_list)) - 1:
        raise ValueError("position doesn't exist")
    if not verif_if_it_is_a_number(number):
        raise ValueError("you can only insert numbers")
    number_list.insert(pos, number)


def add_number(number_list, number):
    """
    :param number_list: the list where we add a number
    :param number: the number we add
    :return: -
    """
    if not verif_if_it_is_a_number(number):
        raise ValueError('You can only add numbers')
    number_list.append(number)


def replace_number(number_list, new_number, old_number):
    """
    :param number_list: the list of numbers
    :param new_number: the number that we replace with
    :param old_number: the number that is replaced
    :return: -

    """
    for i in range(len(number_list)):
        if int(get_real(number_list[i])) == int(get_real(old_number)) and int(get_imag(number_list[i])) == int(get_imag(old_number)):
            number_list[i] = new_number


def remove_number(number_list, pos):
    """
    Function deletes student given by id
    :param number_list: list of numbers
    :param pos: position of number to be deleted
    :return: -
    Raises ValueError if pos not found
    """
    pos_number = int(find_by_position(number_list, pos))
    if pos_number == -1:
        raise ValueError('position does not exist')
    number_list.pop(int(pos_number))


def real_number(number):
    """
    Verifies if a number is real or not
    :param number: the number we verify
    :return: True if the number is real, False otherwise
    """
    if int(get_imag(number)) == 0:
        return True
    return False


def mod_number(number):
    """
    returns the module
    :param number: the complex number
    :return: the module of a complex number rounded with 2 digits
    """
    x = math.sqrt(int(get_real(number)) * int(get_real(number)) + int(get_imag(number)) * int(get_imag(number)))
    return round(x, 2)


def verif(x):
    """
    verifies if x is one of these signs <, >, =
    :param x: the sign we ar searching for
    :return: true if it belongs, false if it doesn't
    """
    if x == '<':
        return True
    if x == '>':
        return True
    if x == '=':
        return True
    return False


def split_command(command):
    """
    Separate user command into command word and parameters
    :param command: User command
    :return: (command word, command parameters)
    """
    tokens = command.strip().split(' ', 1)
    command_word = tokens[0].lower().strip()
    command_params = tokens[1].strip() if len(tokens) == 2 else ''

    return command_word, command_params


def sum(number_list, start, end):
    """
    sum the numbers between start and end
    :param number_list: the list of numbers we sum
    :param start: the start position
    :param end: the end position
    :return: the sum of numbers
    """
    if int(start) < 0 or int(end) > len(number_list) - 1:
        raise ValueError("One of the positions you chosen that not exist in our list")

    sum_real = 0
    sum_imag= 0
    for i in range(int(start), int(end)+1):
        sum_real += get_real(number_list[i])
        sum_imag +=get_imag(number_list[i])
    return create_number(sum_real, sum_imag)


def prod(number_list, start, end):
    """
    product the numbers between start and end
    :param number_list: the list of numbers we sum
    :param start: the start position
    :param end: the end position
    :return: the product of numbers
    """
    if int(start) < 0 or int(end) > len(number_list) - 1:
        raise ValueError("One of the positions you chosen that not exist in our list")

    prod = create_number(get_real(number_list[start]), get_imag(number_list[start]))
    for i in range(int(start)+1, int(end)+1):
        prod_real = get_real(prod) * get_real(number_list[i]) - get_imag(prod) * get_imag(number_list[i])
        prod_imag = get_real(prod) * get_imag(number_list[i]) + get_imag(prod) * get_real(number_list[i])
        prod = create_number(prod_real, prod_imag)
    return prod


def create_history(number_list, history):
    history.append(number_list)


def undo(history):
    history.pop()

def test_init(number_list):
    number_list.append(create_number(1, 3))
    number_list.append(create_number(2, 11))
    number_list.append(create_number(1, 1))
    number_list.append(create_number(5, 0))
    number_list.append(create_number(1, 12))
    number_list.append(create_number(14, 0))
    number_list.append(create_number(1, 0))
    number_list.append(create_number(3, 4))
    number_list.append(create_number(1, 1))
    number_list.append(create_number(9, 3))


def test_prod():
    number_list = []
    test_init(number_list)
    try:
        prod(number_list, -1, 4)
        assert False
    except ValueError:
        assert True

    try:
        prod(number_list, 1, 14)
        assert False
    except ValueError:
        assert True


def test_sum():
    number_list = []
    test_init(number_list)
    try:
        sum(number_list, -1, 4)
        assert False
    except ValueError:
        assert True

    try:
        sum(number_list, 1, 14)
        assert False
    except ValueError:
        assert True


def test_add_number():
    number_list = []
    test_init(number_list)
    number = create_number('1', '1')
    list_len = len(number_list)
    add_number(number_list, number)
    assert find_by_position(number_list, list_len+1) == -1
    n = create_number('1', '6a')
    try:
        add_number(number_list, n)
        assert False
    except ValueError:
        assert True

    p = create_number('1abc', '12i')
    try:
        add_number(number_list, p)
        assert False
    except ValueError:
        assert True


def test_remove_number():
    number_list = []
    test_init(number_list)
    list_len = len(number_list)

    # Test removing a number
    remove_number(number_list, '2')
    assert len(number_list) == list_len - 1 and find_by_position(number_list, int(list_len) - 1) == -1


def test_insert_number():
    number_list = []
    test_init(number_list)
    n = create_number(4, 5)
    try:
        insert_number(number_list, n, '12')
        assert False
    except ValueError:
        assert True


def test_replace_number():
    number_list = []
    test_init(number_list)
    n = create_number(4, 5)
    n2 = create_number(1, 12)

    # test replacing a number
    replace_number(number_list, n, n2)
    assert search_for_number(number_list, n) or not search_for_number(number_list, n2)


def test_undo():
    number_list = []
    history = []
    test_init(number_list)
    history.append(number_list)
    create_history(number_list, history)
    l = len(history)
    undo(history)
    assert len(history) != l



def test_split_command():
    for cmd in ['add 1+2i', '  aDD    1+2i', 'aDd      1+2i    ']:
        cmd_word, cmd_params = split_command(cmd)
        assert cmd_word == 'add' and cmd_params == '1+2i'

    for cmd in ['remove 3', '  REMOve    3 ', 'ReMoVe  3    ']:
        cmd_word, cmd_params = split_command(cmd)
        assert cmd_word == 'remove' and cmd_params == '3'
    cmd_word, cmd_params = split_command('exit')
    assert cmd_word == 'exit' and cmd_params == ''


test_undo()
test_prod()
test_sum()
test_add_number()
test_replace_number()
test_remove_number()
test_insert_number()
test_split_command()