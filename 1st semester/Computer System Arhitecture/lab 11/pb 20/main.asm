bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf, scanf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll 
import scanf msvcrt.dll
import printf msvcrt.dll 


extern prim 

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    n dd 0
    format1 db "%d", 0
    format2 db "%d  ", 0

; our code starts here
segment code use32 class=code
    start:
        ; ...
        read:
            push dword n
            push format1
            call [scanf]
            add esp, 8
            mov eax, [n]
            cmp al, byte 0
            je end_read
            
            push eax
            call prim
            
            cmp eax, 1
            jne not_prime

            push dword [n]
            push format2
            call [printf]
            add esp, 8
            
            not_prime:
        
        jmp read
        end_read:
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
