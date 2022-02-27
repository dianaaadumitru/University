"""
Assemble the program and start the user interface here
"""

from src.ui.console import show_all_numbers_ui, remove_number_ui, replace_number_ui, add_number_ui, insert_number_ui, sum_ui, prod_ui, filter_numbers_ui, history_ui
from src.functions.functions import test_init, split_command


def start_command_ui():
    number_list = []
    history = []
    test_init(number_list)
    history.append(number_list)
    command_dict = {'list': show_all_numbers_ui, 'remove': remove_number_ui, 'replace': replace_number_ui, 'add': add_number_ui, 'insert': insert_number_ui, 'sum': sum_ui, 'prod': prod_ui, 'filter': filter_numbers_ui, 'undo': history_ui}
    done = False
    while not done:
        command = input('command> ')
        try:
            command_word, command_params = split_command(command)
            if command_word in command_dict:
                command_dict[command_word](command_params, history)
            elif command_word == 'exit':
                done = True
                print('Goodbye!')
            else:
                print('bad command')
        except ValueError as ve:
            print(str(ve))

start_command_ui()