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
    new_line db 10,10,0
    space db 0
    text2 times len db 0
    cuv times 20 db 0
    format_4 db "propozitia: %s, nr cuvinte: %d, primul cuvant %sare %d litere", 0 
    

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
            mov edi, 0

            cld
            jecxz the_end
                start_loop:
                    lodsb
                    cmp al, "."
                    je same_sen
                        mov [text2+edx], al
                        inc edx
                        mov bl, [space]
                        cmp bl, 0
                        jne not_first
                            mov [cuv+edi], al
                            inc edi
                    
                        not_first:
                        cmp al, " "
                        jne not_another_word
                            mov bl, [space]
                            inc bl
                            mov [space], bl
                        not_another_word:
                        jmp here
                    same_sen:
                    
                    pushad

                    mov eax, 0
                    mov al, [space]
                    inc al
                    
                    dec edi

                    push edi
                    push cuv
                    push eax
                    push text2
                    push format_4
                    call [printf]
                    add esp, 4*5
                    
                    push new_line
                    call [printf]
                    add esp, 4
                    popad
                    
                    mov al, -1
                    mov [space], al
                    mov edi, 0
                    mov edx, 0
                    here:
                loop start_loop
            the_end:
        
        error_fopen:
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program

