"""
Domain file includes code for entity management
entity = number, transaction, expense etc.
"""


def get_real(number):
    """
    Return the real part
    :param number: -
    :return: -
    """
    return number['real']


def get_imag(number):
    """
    Return the imaginary part
    :param number: -
    :return: -
    """
    return number['imag']


def create_number(real_part, imag_part):
    """
    Create a complex number with given attributes
    :param real_part: the real part of a complex number
    :param imag_part: the imaginary part of a complex number
    :return: The whole number
    """
    return {'real': real_part, 'imag': imag_part}


def to_str(number):
    if int(get_imag(number)) > 0:
        return str(get_real(number)).rjust(3) + '+' + str(get_imag(number)) + 'i'
    elif int(get_imag(number)) < 0:
        return str(get_real(number)).rjust(3) + str(get_imag(number)).rjust(2) + 'i'
    else:
        return str(get_real(number)).rjust(3)


def search_for_number(number_list, number):
    """
    :param number_list: the list where we are searching
    :param number: the number we are searching for
    :return: true if the number exists in the list, false otherwise
    """
    for number2 in number_list:
        if int(get_real(number)) == int(get_real(number2)) and int(get_imag(number)) == int(get_imag(number2)):
            return True
    return False


def find_by_position(number_list, pos):
    """
    Search for student using id field
    :param number_list: The list of numbers
    :param pos: The pos we are searching for
    :return: the position if it exists, -1 if it doesn't
    """
    if int(pos) < len(number_list):
        return pos
    return -1

def verif_if_it_is_a_number(number):
    """
    Verif if a number contains only didgts
    :param number: the number we verify
    :return 1 if the number contains only digits, -1 otherwise
    """
    if not str(get_real(number)).isdigit() or not str(get_imag(number)).isdigit():
        return 0
    return 1
