bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import printf msvcrt.dll                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

                          
extern convert

                          
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    s db 24, 36, 42, 16
    len equ $-s
    l dd 0
    format dd"%s   ", 0
    s2 times 8 db -1

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov esi, s
        mov ecx, len
        
        jecxz the_end
            start_loop:
                mov eax, 0
                lodsb
                mov [l], ecx
                push eax
                push s2
                call convert
                
                
                pushad
                push eax
                push format
                call [printf]
                add esp, 8
                
                popad
                mov ecx, [l]
        
            loop start_loop
        the_end:
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
