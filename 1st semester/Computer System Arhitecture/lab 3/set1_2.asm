bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 4
    b dw 10
    c dd 20
    d dq 30
segment code use32 class=code
    ;(b+c+d)-(a+a)
    start:
        
        mov ax, [b]
        cwd
        
        mov bx, word [c]
        mov cx, word [c+2]  ;cx:bx = c
        
        
        add ax, bx
        adc dx,cx  ;dx:ax = b+c = 30
        
        push dx
        push ax
        pop eax  ;eax = b + c = 30
        cdq
        
        add eax, dword [d]
        adc eax, dword [d+2]    ;edx:eax = b+c+d = 60
        
        mov ebx, eax
        mov ecx, edx    ;ecx:ebx = b+c+d = 60

        mov al, [a]
        add al, byte [a]    ;al = a+a = 8
        cbw
        cwd
        push dx
        push ax
        pop eax     ;eax= a+a = 8
        
        sub ebx, eax
        sbb ecx, edx    ;ecx:ebx = (b+c+d)-(a+a) = 52
        
        
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
