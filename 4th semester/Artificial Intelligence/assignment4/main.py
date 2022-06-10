from gui import *
from controller import Controller


def main():
    service = Controller()
    ui = GUI(service)
    ui.start()
    # service.run()


if __name__ == "__main__":
    main()
