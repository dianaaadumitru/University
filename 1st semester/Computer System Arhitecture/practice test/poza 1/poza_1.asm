bits 32 ; assembling for the 32 bits architecture

global start        

; declare external functions needed by our program
extern exit, fopen, printf, fread               
import exit msvcrt.dll    
import fopen msvcrt.dll
import printf msvcrt.dll
import fread msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    file_name db "abc.txt", 0
    access_mode db "r", 0
    file_descriptor db -1
    len equ 100
    text times len db 0
    real_len dd -1
    format_1 db "nr cuvinte: %d ", 0
    format_2 db "propozitia: %s ", 0
    new_line db 10,10,0
    space db 0
    text2 times len db 0
    

; our code starts here
segment code use32 class=code
    start:
        ; ...
        push dword access_mode
        push dword file_name
        call [fopen]
        add esp, 4*2
        
        mov [file_descriptor], eax
        mov esi, text
        cmp eax, 0
        je error_fopen
            push dword [file_descriptor]
            push dword len
            push dword 1
            push dword text
            call [fread]
            add esp, 4*4
            
            mov [real_len], eax
            mov ecx, eax
            mov esi, text
            mov edx, 0
            mov bl, 1
            mov edi, esi
            cld
            jecxz the_end
                start_loop:
                    lodsb
                    cmp al, "."
                    je end_sentence
                        cmp al, " "
                        jne next_
                            inc dl
                        next_:
                        jmp here
                    end_sentence:
                        cmp bl , 1
                        jne here_2
                        inc dl
                        dec bl
                        here_2:
                        cmp ecx, 1
                        jne not_end
                            mov esi, 0
                        not_end:
                        mov dh, 0
                        mov bx, 0
                        push bx
                        push dx
                        pop edx
                        mov eax, esi
                        cmp esi, 0
                        je abcd
                        sub esi, edi
                        abcd:
                        add edi, esi
                        mov esi, eax
                        pushad
                        push dword edi
                        push format_2
                        call [printf]
                        add esp, 8
                        popad
                        mov edi, esi
                        pushad
                        
                        push dword edx
                        push format_1
                        call [printf]
                        add esp, 8
                        
                        push new_line
                        call [printf]
                        add esp, 4
                        popad
                        mov dl, 0
                        here:
            
                loop start_loop
            the_end:
        
        error_fopen:
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program

