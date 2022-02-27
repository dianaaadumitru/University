bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fread, fopen, fclose, printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import fread msvcrt.dll 
import fopen msvcrt.dll
import fclose msvcrt.dll  
import printf msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    file_name db "r2.txt", 0
    access_mode db "r", 0
    file_descriptor db - 1
    len equ 100
    text times len db 0
    format db "%s", 0
    text_decod times len db 0

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
        je fopen_error
            push dword [file_descriptor]
            push dword len
            push dword 1
            push dword text
            call [fread]
            add esp, 16
            
            mov ecx, eax
            mov esi, text
            mov edi, 0
            cld
            jecxz the_end
                start_loop:
                    lodsb
                    cmp al, " "
                    je space
                        sub al, 3
                        mov [text_decod+edi], al
                        inc edi
                        jmp here
                    space:
                    mov [text_decod+edi], al
                    inc edi
                    here:
                
                loop start_loop
        
            the_end:
            push dword text_decod
            push format
            call [printf]
            add esp, 8
            
            push dword [file_descriptor]
            call [fclose]
            add esp, 4
        fopen_error:
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
