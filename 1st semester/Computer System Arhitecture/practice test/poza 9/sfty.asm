bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, scanf, fopen, fclose, fread, printf               
import exit msvcrt.dll   
import scanf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import fread msvcrt.dll
import printf msvcrt.dll
 
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    file_name times 15 db 0
    access_mode db "r", 0
    file_descriptor dd -1
    len equ 100
    text times len db 0
    cuv times 50 db 0
    format db "%s", 0

; our code starts here
segment code use32 class=code
    start:
        ; ...
        
        push file_name
        push format
        call [scanf]
        add esp, 8
        
        push access_mode
        push file_name
        call [fopen]
        
        mov [file_descriptor], eax
        
        cmp eax, 0
        je error_fopen
        
        push dword [file_descriptor]
        push dword len
        push dword 1
        push text
        call [fread]
        add esp, 16
        
        
        mov ecx, eax
        mov esi, text
        mov dl, 0
        mov edi, 0
        
        jecxz the_end
            start_loop:
                lodsb
                cmp al, byte " "
                je end_word
                    mov [cuv+edi], al
                    inc edi
                    jmp here
                
                end_word:
                mov dh, 0
                mov ax, dx
                mov bl, 4
                div bl
                cmp ah, 0
                jne not_multiple
                    mov [cuv+edi], byte " "
                    inc edi
                    
                    pushad
                    push cuv
                    push format
                    call [printf]
                    add esp, 8
                    popad
                
                not_multiple:
                inc dl
                not_finish:
                mov [cuv+edi],byte 0
                dec edi
                cmp edi, 0
                je free_cuv
                jmp not_finish
                free_cuv:
            
                here:
            loop start_loop
        mov dh, 0
        mov ax, dx
        mov bl, 4
        div bl
        cmp ah, 0
        jne not_multiple2
            mov [cuv+edi], byte " "
            inc edi
                    
            pushad
            push cuv
            push format
            call [printf]
            add esp, 8
            popad
                
        not_multiple2:
        
        the_end:
        
        
        push dword [file_descriptor]
        call [fclose]
        add esp, 4
        error_fopen:
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
