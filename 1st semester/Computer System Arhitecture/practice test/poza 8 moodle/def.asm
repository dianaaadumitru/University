bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, scanf, fopen, fclose, fread, fprintf               
import exit msvcrt.dll    
import scanf msvcrt.dll  
import fopen msvcrt.dll  
import fclose msvcrt.dll  
import fread msvcrt.dll  
import fprintf msvcrt.dll  

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    file_name_1 db "input.txt", 0
    file_name_2 db "output.txt", 0
    access_mode_1 db "r", 0
    access_mode_2 db "a", 0
    file_descriptor_1 dd -1
    file_descriptor_2 dd -1
    len equ 100
    text times len db 0
    format db "%s", 0
    text2 times len db 0
    real_len db 0
    

; our code starts here
segment code use32 class=code
    start:
        ; ...

        push access_mode_1
        push file_name_1
        call [fopen]
        add esp, 8
        
        mov [file_descriptor_1], eax
        
        cmp eax, 0
        je error_fopen
        
        push dword [file_descriptor_1]
        push dword len
        push dword 1 
        push text
        call [fread]
        add esp, 16
        
        mov [real_len], al
        
        push dword [file_descriptor_1]
        call [fclose]
        add esp, 4
        error_fopen:
        
        push access_mode_2
        push file_name_2
        call [fopen]
        
        mov [file_descriptor_2], eax
        
        cmp eax, 0
        je error_fopen2
        
        mov esi, text
        mov edi, 0
        mov ecx, 0
        mov cl, [real_len]
        mov dl, 0
        
        jecxz the_end
            start_loop:
                lodsb
                cmp al, "!"
                je end_sen
                    mov [text2+edi], al
                    inc edi
                    jmp here
                end_sen:
                mov [text2+edi], al
                inc dl
                mov dh, 0
                mov ax, dx
                mov bl, 2
                div bl
                cmp ah, 1
                jne not_odd
                    pushad
                    push text2
                    push dword [file_descriptor_2]
                    call [fprintf]
                    add esp, 8
                    popad
                
                not_odd:
                mov edi, 0 
               here:
            loop start_loop
        
        
        the_end:
        
        
        push dword [file_descriptor_2]
        call [fclose]
        error_fopen2:
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
