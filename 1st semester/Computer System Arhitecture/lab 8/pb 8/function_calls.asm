
bits 32
global start        

; declare extern functions used by the program
extern exit, printf, scanf         ; add printf an scanf as extern functions           
import exit msvcrt.dll    
import printf msvcrt.dll    ; tell the assembler that function printf can be found in library msvcrt.dll
import scanf msvcrt.dll     
           
segment data use32 class=data

    format db "a+a/b = %d", 0  ; %d will be replaced with a number
				; char strings for C functions must terminate with 0
    a dd 10
    b dd 0      ; this is the variable where we store the number read from keyboard
    f db "%d", 0    ; %d <=> a decimal number (base 10)
    message db "b=", 0
    
segment  code use32 class=code
    start:
    ; will call printf(message) => will print "b="
        ; place parameters on stack
        push dword message ; ! on the stack is placed the address of the string, not its value
        call [printf]      ; call function printf for printing
        add esp, 4*1       ; free parameters on the stack; 4 = size of dword; 1 = number of parameters
        
        push  dword b       ; ! address of b, not the value
        push  f
        call  [scanf]       ; call scanf for reading
        add  esp, 4 * 2
        mov eax, [b]
        
        mov eax, [a]
        cdq
        div dword [b]       ;edx:eax = a/b
        

        add eax, dword [a]  ;eax = a + a/b
        
        ; will call printf(format, a + a/b)
        ; place parameters on the stack from right to left
        push dword eax
        push dword format ; ! on the stack is placed the address of the string, not its value
        call [printf]      ; call function printf for printing 
        add esp, 4 * 2     ; free parameters on the stack; 4 = size of dword; 2 = number of parameters

        ; exit(0)
        push dword 0      ; push on stack the parameter for exit
        call [exit]       ; call exit to terminate the programme