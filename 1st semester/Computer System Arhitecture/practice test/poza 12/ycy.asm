;Se da un sir de dublucuvinte. Se cere formarea si afisarea unui sir de biti cu urmatoarele reguli:
;Se ia cel mai semnificativ octet din cel mai putin semnificativ cuvant, iar daca este strict negativ se pune in sir.
;Eemplu: sir dd 12345678h,1234 abcdh,FF00 FE33h.
;Pe ecan se afiseaza: 1010 1011 1111 1110 (numerele gasite fiind AB,FE)





bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll 
import printf msvcrt.dll   

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    sir dd 12345678h,1234, 0abcdh,0FF00h, 0FE33h
    len equ $-sir
    text times 100 db 0
    format db "%s", 0

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov esi, sir
        mov edi, 7
        mov ecx, len
        mov dl, -1
        
        jecxz the_end
            start_loop:
                lodsd
                inc dl
                shr eax, 8
                conv2:
                mov ah, 0
                mov bl, 2
                div bl
                add ah, byte "0"
                mov [text+edi], ah
                dec edi
                cmp al, 0
                je end_transf
                mov ah, 0
                
                
                jmp conv2
                end_transf:
                cmp edi, -1
                jne not_good
                    pushad
                    mov edi, 8
                    add [text+edi], byte " "
                    push text
                    push format
                    call [printf]
                    add esp, 8
                    
                    popad
                
                not_good:
                mov edi, 7
            
            
            loop start_loop
        the_end:
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
