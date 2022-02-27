"""
This is the user interface module. These functions call functions from the domain and functions module.
"""
from src.functions.functions import remove_number, real_number, verif, mod_number, prod, insert_number, add_number, search_for_number, replace_number, sum, create_history, undo
from src.domain.entity import to_str, create_number


def filter_numbers_ui(command_params, history):
    if command_params =='':
        raise ValueError("Invalid input")

    x = history[len(history)-1].copy()
    _list = command_params.split()
    if _list[0] == 'real':
        if len(_list) > 1:
            raise ValueError('Invalid parameter count')
        for i in range(len(x)-1, -1, -1):
            if not real_number(x[i]):
                remove_number(x, i)
    else:
        if _list[0] == 'modulo':
            if len(_list) != 3 or not verif(_list[1]):
                raise ValueError('Invalid parameter count')
            if _list[1] == '<':
                for i in range(len(x)-1, -1, -1):
                    if mod_number(x[i]) >= int(_list[2]):
                        remove_number(x, i)
            elif _list[1] == '>':
                for i in range(len(x) - 1, -1, -1):
                    if mod_number(x[i]) <= int(_list[2]):
                        remove_number(x, i)
            else:
                for i in range(len(x) - 1, -1, -1):
                    if mod_number(x[i]) != int(_list[2]):
                        remove_number(x, i)

    create_history(x, history)


def prod_ui(command_params, history):
    _list = command_params.split('to')
    x = history[len(history) - 1]
    if len(_list) != 2:
        raise ValueError('Invalid parameter count')

    for i in range(len(_list)):
        _list[i] = _list[i].strip()

    if int(_list[0]) < 0 or int(_list[1]) > len(x) - 1:
        raise ValueError("One of the positions you chosen that not exist in our list")
    p = prod(x, int(_list[0]), int(_list[1]))
    print(to_str(p))


def sum_ui(command_params, history):
    _list = command_params.split('to')
    x = history[len(history)-1]
    if len(_list) != 2:
        raise ValueError('Invalid parameter count')

    for i in range(len(_list)):
        _list[i] = _list[i].strip()

    if int(_list[0]) < 0 or int(_list[1]) > len(x) - 1:
        raise ValueError("One of the positions you chosen that not exist in our list")

    s = sum(x, _list[0], _list[1])
    print(to_str(s))


def insert_number_ui(command_params, history):
    _list = command_params.split('at')
    if len(_list) != 2:
        raise ValueError('Invalid parameter count')

    for i in range(len(_list)):
        _list[i] = _list[i].strip()

    n = _list[0].split('+')
    a = n[0]
    if len(n) == 1:
        b = 0
    else:
        b = n[1].strip('i')

    number = create_number(a, b)
    x = history[len(history) - 1].copy()
    insert_number(x, number, int(_list[1]))
    create_history(x, history)


def add_number_ui(command_params, history):
    _list = command_params.split('+')
    if len(_list) == 1:
       if _list[0].find('i') == -1:
            number = create_number(_list[0], '0')
       else:
           _list[0] = _list[0].strip('i')
           number = create_number('0', _list[0])
    else:
        _list[1] = _list[1].strip('i')
        for i in range(len(_list)):
            _list[i] = _list[i].strip()

        number = create_number(_list[0], _list[1])
    x = history[len(history) - 1].copy()
    add_number(x, number)
    create_history(x, history)


def replace_number_ui(command_params, history):
    _list = command_params.split('with')
    x = history[len(history) - 1].copy()
    if len(_list) != 2:
        raise ValueError('Invalid parameter count')

    old_n = _list[0].split('+')
    a = old_n[0]
    if len(old_n) == 1:
        b = '0'
    else:
        old_n[1] = old_n[1].strip()
        b = old_n[1].strip('i')

    old_number = create_number(a, b)

    new_n = _list[1].split('+')
    a2 = new_n[0]
    if len(new_n) == 1:
        b2 = '0'
    else:
        new_n[1] = new_n[1].strip()
        b2 = new_n[1].strip('i')

    new_number = create_number(a2, b2)

    if not search_for_number(x, old_number):
        raise ValueError('The number does not exist in our list')

    replace_number(x, new_number, old_number)
    create_history(x, history)


def remove_number_ui(command_params, history):
    _list = command_params.split('to')
    x = history[len(history) - 1].copy()
    if len(_list) > 1:
        for i in range(int(_list[1]), int(_list[0])-1, -1):
            remove_number(x, i)
    else:
        remove_number(x, _list[0])

    create_history(x, history)


def show_all_numbers_ui(command_params, history):
    x = history[len(history) - 1]
    if command_params =='':
        for i in range(len(x)):
            print(i, '.', to_str(x[i]), sep='')
    else:
        _list = command_params.split()
        if _list[0] == 'real':
            if len(_list) != 4:
                raise ValueError('Invalid parameter count')
            for i in range(int(_list[1]), int(_list[3]) + 1):
                if real_number(x[i]):
                    print(i, '.', to_str(x[i]), sep='')
        else:
            if _list[0] == 'modulo':
                if len(_list) != 3 or not verif(_list[1]):
                    raise ValueError('Invalid parameter count')
                if _list[1] == '<':
                    for number in x:
                        if mod_number(number) < int(_list[2]):
                            print(to_str(number))
                elif _list[1] == '>':
                    for number in x:
                        if mod_number(number) > int(_list[2]):
                            print(to_str(number))
                else:
                    for number in x:
                        if mod_number(number) == int(_list[2]):
                            print(to_str(number))


def history_ui(command_params, history):
    if command_params != '':
        raise ValueError("Invalid input")

    if len(history) == 1:
        raise ValueError("no more undos")
    undo(history)
