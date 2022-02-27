bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf, scanf               
import exit msvcrt.dll    
import printf msvcrt.dll
import scanf msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    fisrt dd 0
    n dd 0
    f db "%c", 0

; our code starts here
segment code use32 class=code
    start:
        ; ...
        ;Multiple strings of characters are being read. Determine whether the first appears as a subsequence in each of the others and give an appropriate message. 
        push dword first
        push f
        call [scanf]
        add esp, 4*2
        read_string:
            push dword n
            push f
            call [scanf]
            add esp, 4*2
        
            mov eax, [n]
            mov ebx, 30h
            cmp eax, ebx
            je the_end
                mov eax, 67
            
        
            push eax
            push f
            call [printf]
            add esp, 4*2
        jmp read_string
        the_end:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
