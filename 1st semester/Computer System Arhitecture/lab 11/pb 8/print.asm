bits 32

global print

;define the function
print: ; int _stdcall print(int n)

    mov ebx, 8
    mov cl, 0
    mov ch, 0
    mov edi, 0
    ;we know that a byte has maximum 3 digits so we save each digit
    loopDigits:
        mov edx, 0
        div ebx ;divide the number by 8 in order to obtain the last digit; this digit will be in EDX
        cmp edi, 0
        jne not_equal
            mov ch, dl  ;we save the last digit of the number in ch
            inc edi
        not_equal:
        inc edi
        cmp ax, 0
        je equal
        mov cl, dl  ;we save the middle digit of the number in cl
        
        equal:
        cmp eax, 0
    jne loopDigits  ;if the quotient (from eax) is 0 it means we obtained all the digits and we can leave the loop "loopDigits"
    
    
    cmp edi, 4  ;we verify if the number hs 2 or 3 digits, if it has only 2 it jumps
    jne two_digits  ;we compute the number in the right order
        mov al, dl  ;first digit
        mov dl, 100
        mul dl
        mov dx, ax
        mov al, cl  ;second digit
        mov cl, 10
        mul cl
        add dx, ax
        mov al, ch  ;third digit
        mov ah, 0
        add dx, ax
        jmp the_end
    two_digits:
    mov al, dl  ;first digit
    mov dl, 10
    mul dl
    mov dx, ax
    mov al, cl  ;second digit
    mov ah, 0
    add dx, ax
    the_end:
    mov ax, dx
    mov dx, 0
    push dx
    push ax
    pop eax ;we save our number into a doubleword

	ret 4 ; in this case, 4 represents the number of bytes that need to be cleared from the stack (the parameter of the function)

