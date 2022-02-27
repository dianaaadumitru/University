bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dw 1110100011011100b
    b dw 1110000110110110b
    c db 0
    d dd 0
segment code use32 class=code
    start:
        ;Given the words A and B, compute the byte C as follows:
        ;the bits 0-5 are the same as the bits 5-10 of A
        ;the bits 6-7 are the same as the bits 1-2 of B
        
        mov bl, 0   ; we compute the result in bx
        mov ax, [a]     
        and ax, 0000011111100000b   ; we isolate bits 5-10 of A
        mov cl, 5
        ror ax, cl  ; we rotate 5 positions to the right
        or bl, al   ; we put the bits into the result
        
        mov ax, [b]
        and ax, 0000000000000110b
        
        mov cl, 5   ; we rotate 5 positions to the left
        rol al, cl  
        or bl, al   ; we put the bits into the result
        mov [c], bl ; we move the result from the register to the result variable
        
        ;Compute the doubleword D as follows:
        ;the bits 8-15 are the same as the bits of C
        ;the bits 0-7 are the same as the bits 8-15 of B
        ;the bits 24-31 are the same as the bits 0-7 of A
        ;the bits 16-23 are the same as the bits 8-15 of A.
        
        mov bx, 0
        mov dx, 0   ; we compute the result in dx:bx
        mov ax, [b]
        and ax, 1111111100000000b   ; we isolate bits 8-15 of B
        mov cl, 8   ; we rotate 8 positions to the right
        ror ax, cl
        or bl, al   ; we put the bits into the result
        
        mov al, [c]
        mov ah, 0   ;unsigned conversion from byte to word 
        mov cl, 8   ; we rotate 8 positions to the right
        ror ax, cl
        or bx, ax   ; we put the bits into the result
        
        mov ax, [a]
        and ax, 1111111100000000b   ; we isolate bits 8-15 of A
        mov cl, 8   ; we rotate 8 positions to the right
        ror ax, cl
        or dl, al   ; we put the bits into the result
        
        mov ax, [a]
        and ax, 0000000011111111b   ; we isolate bits 0-7 of A
        mov cl, 8   ; we rotate 8 positions to the right
        ror ax, cl
        or dx, ax   ; we put the bits into the result
        
        push dx
        push bx
        pop eax
        
        mov [d], eax    ; we move the result from the register to the result variable
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program