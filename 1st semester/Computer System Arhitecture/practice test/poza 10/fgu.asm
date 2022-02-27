;Problema 1:
;Se da un sir de dublucuvinte (in segmentul de date). 
;Se cere formarea si scrierea in fisier pozitiile octetilor de valoare maxima din fiecare dublucuvant. (evident considerandule fare semn).
;Sa se afiseze si suma acestor octeti (consideranduse cu semn).
;Exemplu: dd 12 34 A6 78h , 12 34 56 78h , 1A C3 B4 7Dh, FE DC 98 76h .
;Sirul format din pozitiile octetilor este: "3421".


bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        


extern exit, printf               
import exit msvcrt.dll   
import printf msvcrt.dll 

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    sir dd 1234A678h, 12345678h, 1AC3B47Dh, 0FEDC9876h 
    len equ $-sir
    text times 10 db 0
    pos db 0
    format db "%d", 0
    

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov esi, sir
        mov edi, 0
        mov ecx, len
        mov bl, 0
        mov dl, 4
        jecxz the_end
            start_loop:
                lodsb
                cmp al, bl
                jb not_greather
                    mov bl, al
                    mov [pos], dl
                
                not_greather:
                dec dl
                cmp dl, 0
                jne end_word
                    pushad
                    
                    mov bl, [pos]
                    mov bh, 0
                    mov dx, 0
                    push dx
                    push bx
                    pop eax
                    
                    push eax
                    push format
                    call [printf]
                    add esp, 8
                    
                    popad
                    mov bl, 0
                    mov dl, 4
                end_word:
            
            loop start_loop
        
        the_end:
       
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
