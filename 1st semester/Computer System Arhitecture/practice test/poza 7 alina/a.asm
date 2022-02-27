bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, scanf, fopen, fclose, fprintf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll
import scanf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import fprintf msvcrt.dll    

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    file_name db "abc.txt", 0
    access_mode db "w", 0
    file_descriptor dd -1
    n dd 0
    format db "%c", 0
    text times 50 db 0

; our code starts here
segment code use32 class=code
    start:
        ; ...
        push access_mode
        push file_name
        call [fopen]
        add esp, 8
        
        mov [file_descriptor], eax
        
        cmp eax, 0
        je error_fopen
        mov edi, 0
        
        here:
        
        push dword n
        push format
        call [scanf]
        add esp, 8
        
        mov eax, [n]
        cmp eax, "!"
        je end_read
            sub eax, "0"
            mov [n], eax
            mov ax, [n]
            mov dx, [n+2]
            mov bx, 2
            div bx
            cmp dx, 0
            jne not_even
                mov ecx, [n]
                add cl, "0"
                mov [text+edi], cl
                inc edi
            
            not_even:
        
        jmp here
        
        end_read:
        
        push text
        push dword [file_descriptor]
        call [fprintf]
        add esp, 8
        
       
        push dword [file_descriptor]
        call [fclose]
        add esp, 4
        error_fopen:
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
