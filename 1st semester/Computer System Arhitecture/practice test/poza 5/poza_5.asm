bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fopen, fclose, scanf, fread, fprintf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    
import fopen msvcrt.dll
import fclose msvcrt.dll
import scanf msvcrt.dll
import fread msvcrt.dll
import fprintf msvcrt.dll


; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    file_name_1 times 10 db 0
    file_name_2 db "output.txt", 0
    access_mode_1 db "r", 0
    access_mode_2 db "w", 0
    file_descriptor dd -1
    n dd 0
    char db 0
    len equ 100
    text times len db 0
    format_1 db "%s", 0
    format_2 db "%s", 0
    format_3 db "%d", 0
    text2 times len db 0

; our code starts here
segment code use32 class=code
    start:
        ; ...
        push file_name_1
        push dword format_1
        call [scanf]
        add esp, 8
        
        push dword n
        push format_3
        call [scanf]
        add esp, 8
        
        push char
        push format_2
        call [scanf]
        add esp, 8
        
        
        push dword access_mode_1
        push file_name_1
        call [fopen]
        add esp, 8
        
        mov [file_descriptor], eax
        
        cmp eax, 0
        je error_fopen
            push dword [file_descriptor]
            push len
            push 1
            push text
            call [fread]
            add esp, 16
            
            mov ecx, eax
            mov esi, text
            mov edi, 0
            mov ebx, [n]
            
            jecxz the_end
                start_loop:
                    lodsb
                    cmp al, " "
                    je space
                        cmp ebx, 0
                        je enough
                            mov [text2 + edi], al
                            inc edi
                            dec ebx
                            jmp here_2
                        
                    space:
                    enough:
                    cmp ebx, 0
                    je enough_2
                        here:
                        mov dl, [char]
                        mov [text2 + edi], dl
                        inc edi
                        dec ebx
                        cmp ebx, 0
                        je enough_2
                        jmp here
                    
                    enough_2:
                    cmp al, " "
                    jne jump
                        mov ebx, [n]
                        mov al, " "
                        mov [text2 + edi], al
                        inc edi
                    jump:
                    here_2:
                    
                loop start_loop
            
            the_end:
            
            
            push dword [file_descriptor]
            call [fclose]
            add esp, 4
        error_fopen:
        mov dword [file_descriptor], -1
        
        push access_mode_2
        push file_name_2
        call [fopen]
        add esp, 8
        
        mov [file_descriptor], eax
        
        cmp eax, 0
        je errorr
            push dword text2
            push dword [file_descriptor]
            call [fprintf]
            add esp, 8
            
            push dword [file_descriptor]
            call [fclose]
            add esp, 4
        
        errorr:
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
