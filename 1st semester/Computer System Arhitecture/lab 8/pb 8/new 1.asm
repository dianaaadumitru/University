bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...

; our code starts here
a db  48h, 61h, 70h, 70h, 79h, 20h, 68h, 6fh, 6ch, 69h, 64h, 61h, 79h, 20h, 61h, 6eh, 64h, 20h, 4dh, 65h, 72h, 72h, 79h, 20h, 43h, 68h, 72h, 69h, 73h, 74h, 6dh, 61h, 73h, 21h, 0
segment code use32 class=code
    start:
        ; ...
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
