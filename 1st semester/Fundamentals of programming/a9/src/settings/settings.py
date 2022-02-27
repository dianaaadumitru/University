
class Settings:
    def __init__(self, file_name):
        self.file_name = file_name
        self.type = ''
        self.books_file = ''
        self.clients_file = ''
        self.rentals_file = ''
        self.content = self.read_properties_file()

    def read_properties_file(self):
        file_name_separator = '"'

        with open(self.file_name) as file:
            content = []
            for line in file:
                if file_name_separator in line:
                    line_content = line.split(file_name_separator, 2)
                    current_information = line_content[1].strip()
                    content.append(current_information)
                else:
                    if '=' in line:
                        line_content = line.split('=', 1)
                        current_information = line_content[1].strip()
                        content.append(current_information)
            return content

    def get_repository_type(self):
        return self.content[0]

    def set_repository_type(self):
        self.type = self.content[0]

    def get_book_file_name(self):
        return self.content[1]

    def set_book_file_name(self):
        self.books_file = self.content[1]

    def get_client_file_name(self):
        return self.content[2]

    def set_client_file_name(self):
        self.clients_file = self.content[2]

    def get_rental_file_name(self):
        return self.content[3]

    def set_rental_file_name(self):
        self.rentals_file = self.content[3]

    def file_decisions(self):
        self.set_repository_type()
        self.set_book_file_name()
        self.set_client_file_name()
        self.set_rental_file_name()

# import configparser
#
#
# class Settings:
#     def __init__(self):
#         self.config = configparser.ConfigParser()
#         self.config.read("settings.properties")
#
#     def get_repository_type(self):
#         return str(self.config["Settings"]["type"])
#
#     def get_book_file_name(self):
#         return str(self.config["Settings"]["book"])
#
#     def get_client_file_name(self):
#         return str(self.config["Settings"]["client"])
#
#     def get_rental_file_name(self):
#         return str(self.config["Settings"]["rentals"])
