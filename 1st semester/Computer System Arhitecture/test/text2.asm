bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start   
     
;Se da in data segment numele unui fisier. Acesta contine cifre separate prin spatiu (cifrele sunt din multimea cifrelor bazei 16 separate).
;Sa se afiseze fiecare cifra citita din fisier si numarul de biti 1 din reprezentarea binara a acesteia.

extern exit, fopen, fclose, fread, printf               
import exit msvcrt.dll 
import fclose msvcrt.dll
import fopen msvcrt.dll
import fread msvcrt.dll
import printf msvcrt.dll   


; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    file_name db "input.txt", 0
    access_mode db "r", 0
    file_descriptor dd -1
    len equ 100
    text times len db 0
    format db "%s", 0
    text2 times len db 0
    nr1 db 0
    nr db 0
    nr3 db 0

    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        
        push access_mode
        push file_name
        call [fopen]
        add esp, 4*2
        
        mov [file_descriptor], eax
        
        cmp eax, 0
        je error_fopen
        
        push dword [file_descriptor]
        push dword len
        push dword 1
        push text
        call [fread]
        add esp, 4*4
        
        pushad
        
        push dword [file_descriptor]
        call [fclose]
        add esp, 4
        
        popad
        
        error_fopen:
        
        mov ecx, eax
        mov esi, text
        mov edi, 0
        lodsb
        jecxz the_end
            start_loop:
            mov bl, al
                lodsb
                cmp bl, " "
                je space
                cmp al, " "
                je two_digits
                    shr bl, 4
                    add bl, al
                two_digits:
                    mov dl, 0
                    mov [nr1], dl
                    mov [nr], bl
                    mov [nr3], al
                    
                    mov al, bl
                    sub al, byte "0"
                    mov ah, 0

                    conv2:
                    mov bl, 2
                    div bl
                    cmp ah, 1
                    jne not_1
                    mov bl, dl
                        mov dl, [nr1]
                        inc dl
                        mov [nr1], dl
                        
                    not_1:
                    mov ah, 0
                    cmp al, 0
                    je end_transf
                    jmp conv2
                    end_transf:
                    mov al, [nr3]
                    
                    
                    jmp here
                
                shl bl, 4
            
                here:
                mov dl, byte [nr]
                mov [text2+edi], dl
                inc edi
                mov [text2+edi], byte " "
                inc edi
                mov dl, [nr1]
                add dl, byte "0"
                mov [text2+edi], dl
                inc edi
                mov [text2+edi], byte " "
                inc edi
                mov [text2+edi], byte " "
                inc edi
                
            space: 
            loop start_loop
        
        
                    
        the_end:
                
    push text2
    push format
    call [printf]
    add esp, 8
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
