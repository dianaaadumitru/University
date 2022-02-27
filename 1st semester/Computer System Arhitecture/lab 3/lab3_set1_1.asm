bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)

;(a+b-d)+(a-b-d)
segment data use32 class=data
    a db 25
    b dw 12
    d dq 6
   

; our code starts here
segment code use32 class=code
    start:
        mov al, [a]
        mov ah, 0   ;AX: 0019
        
        sub ax, [b] ; AX = a - b = 25 - 12 = 13
        
        mov bx, ax  ;BX: 13
        
        mov eax, 0
        mov ax, bx  
        
        mov edx, 0  ;edx:eax = a-b = 13
        sub eax, dword[d]
        sbb edx, dword[d+4] ;edx:eax = a-b-d = 13 - 6 = 7
        
        mov ebx, eax
        mov ecx, edx
        
        mov al, [a]
        mov ah, 0   ;AX: 0019
        
        add ax, [b] ; AX = a + b = 25 + 12 = 37
        
        mov dx, ax  ;BX: 37
        
        mov eax, 0
        mov ax, dx  
        
        mov edx, 0  ;edx:eax = a+b = 37
        sub eax, dword[d]
        sbb edx, dword[d+4] ;edx:eax = a+b-d = 37 - 6 = 31
        
        sub eax, ebx
        sbb ecx, edx    ;eax:ecx = 31 - 7 = 24
        
        
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
