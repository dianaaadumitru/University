bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fopen, fclose, fprintf, scanf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll 
import fopen msvcrt.dll
import fclose msvcrt.dll
import fprintf msvcrt.dll
import scanf msvcrt.dll
 
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    n dd 0
    format db "%d", 0
    cuv times 30 db 0
    format_cuv db "%s", 0
    file_name db "abcd.txt", 0
    access_mode db "a", 0
    file_descriptor dd -1
    format_2 db "%s ", 0
    first_l db 0
    last_l db 0
    l db 0
    spa db " ", 0 

; our code starts here
segment code use32 class=code
    start:
        ; ...
        push dword n
        push format
        call [scanf]
        add esp, 8
        
        push dword access_mode
        push dword file_name
        call [fopen]
        add esp, 8
        
        
        mov [file_descriptor], eax
        cmp eax, 0
        je fopen_error
        
            here:
            push cuv
            push format_cuv
            call [scanf]
            add esp, 8
            
            mov ecx, 30
            mov esi, cuv
            mov edi, 0
            mov bl, 1
            jecxz the_end
                start_loop:
                    lodsb
                    inc edi
                    cmp bl, 1
                    jne next
                        mov [first_l], al
                        dec bl
                    next:
                    cmp al, 0
                    je end_word
                    mov [last_l], al
                    mov [l], al
                loop start_loop
                end_word:
            the_end:
            dec edi
            mov ebx, [n]
            cmp edi, ebx
            jb not_print
            
            mov dl, [first_l]
            mov bl, [last_l]
            cmp dl, bl
            jne not_print
            
                push cuv
                push dword [file_descriptor]
                call[fprintf]
                add esp, 8
                
                push spa
                push dword [file_descriptor]
                call[fprintf]
                add esp, 8
            
            not_print:
            mov al, [l]
            cmp al, "#"
            je final
            jmp here
            
            final:
            push dword [file_descriptor]
            call [fclose]
            add esp, 4
        
        fopen_error:
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
