bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
        text db 3, 4, 4, 2, 8
        len equ $-text
        s times len dd -1
        two db 2
; our code starts here
segment code use32 class=code
    start:
        ; ...

        mov ecx, len    ; we put the length len in ECX in order to make the loop
        mov esi, text      ;in eds:esi we will store the FAR address of the string "s"
        cld             ;parse the string from left to right(DF=0)
        mov edi, 0
        
        jecxz the_end
            start_loop:
                lodsb       ;in eax we will have the curremt doubleword from the string
                mov ebx, 0
                transf:
                    mov ah, 0
                    div byte [two]
                    add bl, ah
                    ror ebx, 1
                    cmp al, 0
                    jz end_tranfs   ;if the quotient (from al) is 0 it means we obtained all the digits and we can leave the loop "transf"
                                    ;else prepare the quotient for a new iteration 
                    mov ah, 0
                jmp transf
                end_tranfs:
            loop start_loop
        the_end:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
