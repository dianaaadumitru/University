bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dw 1
    b dw 5
    c db 15
    d db 4
    e dd 40
    x dq 25
segment code use32 class=code
    start:
    ;1/a+200*b-c/(d+1)+x/a-e
        ;for computing 1/a, we convert 1 from byte to doubleword, so that we can divide it by the word a
        mov al, 1
        mov ah, 0   ;unsigned conversion from al to ax
        mov dx, 0   ;unsigned conversion from ax to dx:ax, dx:ax = 1
        div word [a]   ;ax = 1/a = 1
        
        mov bx, ax    ;we save 1/a in bx so that we can use ax for multiplying b by 200
        
        mov ax, 200
        mul word [b]    ;dx:ax = b * 200 = 1000
        
        push dx
        push ax
        pop eax     ; eax = b*200 = 1000
        
        mov ecx, 0
        mov cx, bx  ;ecx = 1/a = 1
        
        add eax, ecx    ;eax = 1/a+200*b = 1001
        
        mov ebx, eax    ;we save 1/a+200*b in ebx so that we can use ax for c divided by (d+1)
        
        mov al, [c]
        mov ah, 0   ;ax = c
        mov cl, [d]
        add cl, 1   ;cl = d+1 = 5
        div cl  ;al = c/(d+1) = 15/5 = 3
        
        mov ah, 0   ;ax = c/(d+1) = 15/5 = 3
        mov dx, 0   ;dx:ax = c/(d+1) = 15/5 = 3
        push dx
        push ax
        pop eax     ;eax = c/(d+1) = 15/5 = 3
        
        sub ebx, eax    ;ebx = 1/a+200*b-c/(d+1) = 1001-3 = 998
    
        mov ax, [a] ; ax = a = 1
        mov dx, 0
        push dx
        push ax
        pop ecx     ;ecx = a = 1
        
        mov eax, dword [x]
        mov edx, dword [x+2]
        div ecx     ;edx:eax = x/a = 25
        
        add ebx, eax    ;ebx = 1/a+200*b-c/(d+1)+x/a = 998+25 = 1023
        sub ebx, dword [e]  ;ebx = 1/a+200*b-c/(d+1)+x/a-e = 1023-40 = 983
        
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
