;Se da un sir de dublucuvinte (in segmentul de date). 
;Sa se afiseze pe ecran, in baza 16, cuvintele cel mai putin semnificative care sunt siP negative si multipli de 16.


bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf               
import exit msvcrt.dll    
import printf msvcrt.dll


; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    sir dd 1234A689h, 12345680h,  1AC3B47Dh, 0FEDC9896h
    len equ $-sir
    format db "%x ", 0
    nr db 0

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov esi, sir
        mov ecx, len
        jecxz the_end
            start_loop:
                lodsd
                mov [nr], al
                mov ah, 0
                mov dl, byte 16
                div dl
                cmp ah, 0
                jne not_good
                    mov al, [nr]
                    conv2:
                        
                        mov ah, 0
                        mov dl, byte 2
                        div dl
                        cmp al, 0
                        je end_transf
                    jmp conv2
                    end_transf:
                    cmp ah, 1
                    jne not_good
                        pushad
                    
                        mov eax, 0
                        mov al, [nr]
                        push eax
                        push format
                        call [printf]
                        add esp, 8
                        
                        popad
                
                not_good:
            
            loop start_loop
        
        
        the_end:
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
