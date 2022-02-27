
# import tkinter as tk
#
#
# class Application(tk.Frame):
#     def __init__(self, master=None):
#         super().__init__(master)
#         self.master = master
#         self.pack()
#         self.create_widgets()
#
#     def create_widgets(self):
#         self.hi_there = tk.Button(self)
#         self.hi_there["text"] = "Hello World\n(click me)"
#         self.hi_there["command"] = self.say_hi
#         self.hi_there.pack(side="top")
#
#         self.quit = tk.Button(self, text="QUIT", fg="red",
#                               command=self.master.destroy)
#         self.quit.pack(side="bottom")
#
#     def say_hi(self):
#         print("hi there, everyone!")
#
# root = tk.Tk()
# app = Application(master=root)
# app.mainloop()

# from tkinter import *
#
# window = Tk()
#
# window.title("Welcome to LikeGeeks app")
#
# window.geometry('350x200')
#
# lbl = Label(window, text="Hello")
#
# lbl.grid(column=0, row=0)
#
# def clicked():
#
#     lbl.configure(text="Button was clicked !!")
#
# btn = Button(window, text="Click Me", command=clicked)
#
# btn.grid(column=1, row=0)
#
# window.mainloop()

from tkinter import *


def print_menu():
    s = 'Available commands:\n'
    s += '   1. Display (books, customers or rentals)\n'
    s += '   2. Add (books or customers)\n'
    s += '   3. Remove (books or customers)\n'
    s += '   4. Update (books or customers)\n'
    s += '   5. Rent a book\n'
    s += '   6. Return a book\n'
    s += '   7. Search (for book or customer)\n'
    s += '   8. Statistics\n'
    s += '   9. Undo\n'
    s += '   10. Redo\n'
    s += '   0. Exit\n'
    return s


class MyWindow:
    def __init__(self, win):
        lbl = Label(window, text="This is Label widget", fg='red', font=("Helvetica", 16))
        lbl.place(x=10, y=10)
        lbl2 = Label(window, text=print_menu(), fg='blue', font=("Helvetica", 16))
        lbl2.place(x=10, y=40)

        self.lbl1=Label(win, text='First number')
        self.lbl2=Label(win, text='Second number')
        self.lbl3=Label(win, text='Result')
        self.t1=Entry(bd=3)
        self.t2=Entry()
        self.t3=Entry()
        self.btn1 = Button(win, text='Add')
        self.btn2=Button(win, text='Subtract')
        self.lbl1.place(x=10, y=360)
        self.t1.place(x=200, y=360)
        self.lbl2.place(x=10, y=400)
        self.t2.place(x=200, y=400)
        self.b1=Button(win, text='Add', command=self.add)
        self.b2=Button(win, text='Subtract')
        self.b2.bind('<Button-1>', self.sub)
        self.b1.place(x=10, y=440)
        self.b2.place(x=200, y=440)
        self.lbl3.place(x=10, y=480)
        self.t3.place(x=200, y=480)
    def add(self):
        self.t3.delete(0, 'end')
        num1=int(self.t1.get())
        num2=int(self.t2.get())
        result=num1+num2
        self.t3.insert(END, str(result))
    def sub(self, event):
        self.t3.delete(0, 'end')
        num1=int(self.t1.get())
        num2=int(self.t2.get())
        result=num1-num2
        self.t3.insert(END, str(result))

window=Tk()
mywin=MyWindow(window)
window.title('Hello Python')
window.geometry("700x600+10+10")
window.mainloop()