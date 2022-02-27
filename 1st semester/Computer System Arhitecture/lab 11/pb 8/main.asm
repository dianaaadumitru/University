bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf               
import exit msvcrt.dll 
import printf msvcrt.dll

; the code defined in print.asm will be written here
extern print

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    format db "number=%d ", 0
    format2 db "character=%c", 10, 13, 0
; our code starts here
segment code use32 class=code
    start:
    ;Show for each number from 32 to 126 the value of the number (in base 8) and the character with that ASCII code.
        ; ...
        mov eax, 32
        next_number:
            pushad
            push eax
            call print
            
            push eax
            push format
            call [printf] ;print the number in base 8
            add esp, 4*2
            
            popad
            pushad
            
            push eax
            push format2
            call [printf]   ;print the ascii character with that ASCII code.
            add esp, 4*2 
            popad
            
            inc ax
            cmp ax, 127
        jne next_number
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
