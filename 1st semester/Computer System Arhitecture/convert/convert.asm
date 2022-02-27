bits 32

global convert


convert:
    mov ebx, [esp+8]
    mov al, bl
    mov ah, 0
    mov ecx, [esp+4]
    mov edi, 7
    mov bl, 2
    here:
        div bl
        add ah, byte "0"
        mov dl, al
        mov al, ah
        mov [ecx+edi], al
        dec edi
        mov al, dl
        cmp al, 0
        je end_conv
        mov ah, 0
    
    
    
    jmp here
    end_conv:
    here2:
    cmp edi, -1
    je the_end
        mov al, byte "0"
        mov [ecx+edi], al
        dec edi
        jmp here2
    the_end:
    ret