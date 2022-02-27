bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s db 'a', 'A', 'b', 'B', '2', '%', 'x', 'M' ; declare the string of bytes
	l equ $-s ; compute the length of the string in l
	d times l db 0 ; reserve l bytes for the destination string and initialize it
segment code use32 class=code
    start:
        ;A character string S is given. Obtain the string D that contains all capital letters of the string S.
        ;Example:
        ;S: 'a', 'A', 'b', 'B', '2', '%', 'x', 'M'
        ;D: 'A', 'B', 'M' 
        mov ecx, l ; we put the length l in ECX in order to make the loop
        mov esi, 0
        mov ebx, 0
        
        jecxz overtheloop
            start_loop:
                mov al, [esi + s]  ; move the characters to al
                cmp al, 'A'
                jl too_small
                    cmp al, 'Z'
                    jg too_big
                        mov [d+ebx], al 
                        inc ebx
                    too_big:
                too_small:
                inc esi
            loop start_loop
        overtheloop:
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program