bits 32

global prim

prim:
    mov ebx, [esp + 4]
    mov edx, 1
    cmp bl, byte 2
    jae here
        mov edx, 0
        jmp to_end
    here:
    
    cmp bl, byte 2
    jne here22
        mov edx, 1
        jmp to_end
    here22:
    
    mov cl, 1
    loop_for:
        inc cl
        mov ax, 0
        mov al, bl
        div cl
        cmp ah, 0
        jne here2
            mov edx, 0
            jmp to_end
        here2:
        dec bl
        cmp cl, bl
        jge end_for
        inc bl
    
    jmp loop_for
    
    end_for:
    
    
    
    
    to_end:
    mov eax, edx
    ret