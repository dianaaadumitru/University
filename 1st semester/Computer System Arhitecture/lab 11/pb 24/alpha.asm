bits 32

global alphabett

alphabett:
    mov esi, [esp + 4*3] ;first string, s1
    mov edx, [esp + 4*4] ;the alphabet
    mov edi, [esp + 4] ;the string we form
    mov ecx, [esp + 8] ;the len of first string

    jecxz the_end
        start_loop:
            lodsb
            sub al, "a"
            mov ebx, 0
            mov bl, al
            mov al, [edx + ebx]
            stosb
            
        loop start_loop
    
    the_end:
    
    
    ret