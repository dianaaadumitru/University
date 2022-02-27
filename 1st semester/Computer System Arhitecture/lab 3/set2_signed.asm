bits 32 ; assembling for the 32 bits architecture

global start        

extern exit               
import exit msvcrt.dll    
                          
segment data use32 class=data
    a dw 1
    b dw 5
    c db 15
    d db 4
    e dd 40
    x dq 25
segment code use32 class=code
    ;1/a+200*b-c/(d+1)+x/a-e
    start:
        ;for computing 1/a, we convert 1 from byte to doubleword, so that we can divide it by the word a
        mov al, 1   ;al = 1
        cbw     ;signed conversion from al to ax
        cwd     ;signed conversion from ax to dx:ax, dx:ax = 1
        idiv word[a]    ;ax = 1/a = 1
        cwd     ;signed conversion from ax to dx:ax, dx:ax = 1
        mov bx, ax    ;we save 1/a in bx:cx so that we can use ax for multiplying b by 200
        mov cx, dx
        
        mov ax, 200
        imul word [b]    ;dx:ax = b * 200 = 1000
        
        add bx, ax
        adc cx, dx      ;bx:cx = 1/a+200*b = 1001
        
        mov al, [c]
        cbw     ;signed conversion from al to ax, ax = c = 15
        mov dl, [d]
        add dl, 1   ;dl = d+1 = 5
        idiv dl     ;al = c/(d+1) = 15/5 = 3
        cbw         ;signed conversion from al to ax
        cwd         ;signed conversion from ax to dx:ax, dx:ax = c/(d+1) = 15/5 = 3
        
        sub bx, ax
        sbb cx, dx  ;bx:cx = 1/a+200*b-c/(d+1) = 1001-3 = 998
        
        push cx
        push bx
        pop ebx    ;ebx = 1/a+200*b-c/(d+1) = 1001-3 = 998
        
        mov ax, [a]     ;ax = a = 1
        cwd     ;signed conversion from ax to dx:ax, dx:ax = a = 1
        push dx
        push ax
        pop ecx     ;ecx = a = 1
        
        mov eax, dword [x]
        mov edx, dword [x+2]
        idiv ecx     ;edx:eax = x/a = 25
        
        add ebx, eax    ;ebx = 1/a+200*b-c/(d+1)+x/a = 998+25 = 1023
        sub ebx, dword [e]  ;ebx = 1/a+200*b-c/(d+1)+x/a-e = 1023-40 = 983
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
