bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, scanf, fopen, fwrite, printf, fclose               
import exit msvcrt.dll    
import scanf msvcrt.dll
import fopen msvcrt.dll
import fwrite msvcrt.dll
import printf msvcrt.dll
import fclose msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    file_name times 5 dd 0
    format db "%s", 0
    text times 30 dd 0
    mes1 db "file name: ", 0
    mes2 db "text: ", 0
    access_mode db "w", 0
    file_descriptor dd -1
; our code starts here
segment code use32 class=code
    start:
        ; ...
        push mes1
        call [printf]
        add esp, 4
        
        push file_name
        push format
        call [scanf]
        add esp, 4*3
        
        push mes2
        call [printf]
        add esp, 4
        
        push dword text
        push format
        call [scanf]
        add esp, 4*2
        
        push access_mode
        push file_name
        call [fopen]
        add esp, 8
        
        mov [file_descriptor], eax
        
        cmp eax, 0
        je errorr
            push dword text
            push dword [file_descriptor]
            call [fwrite]
            add esp, 8
            
            push dword [file_descriptor]
            call [fclose]
            add esp, 4
        errorr:
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
