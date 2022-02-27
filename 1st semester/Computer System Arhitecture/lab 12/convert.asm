bits 32

; indicate to the assembler that the function _convert should be available to other compile units
global _convert

; the linker may use the public data segment for external data
segment data public data use32

; the code written in assembly language resides in a public segment, that may be shared with external code
segment code use32 public code

;int _convert(int n);
;convention cdecl
_convert: ; int _stdcall _convert(int n)
    ; create a stack frame
    push ebp
    mov ebp, esp
    
    ; retreive the function's arguments from the stack
    ; [ebp+4] contains the return value 
    ; [ebp] contains the ebp value for the caller

    mov eax, [ebp + 8]  ; eax <- a
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
    pop eax     ; we save our number into a doubleword
                ; the return value of the function should be in EAX
                
    ; restore the stack frame
    mov esp, ebp
    pop ebp

	ret ; cdecl call convention - the caller will remove the parameters from the stack

