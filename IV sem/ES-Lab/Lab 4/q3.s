    AREA    RESET, DATA, READONLY
    EXPORT  __Vectors

__Vectors 
    DCD  0x100000FF     ; stack pointer value when stack is empty
    DCD  Reset_Handler  ; reset vector
  
    ALIGN

    AREA mycode, CODE, READONLY
    EXPORT Reset_Handler
    ENTRY
Reset_Handler
    LDR R0, =NUM        ; Load address of the hex number
    LDR R1, [R0] 
    MOV R2, #10         ; Divisor for conversion
    MOV R5, #0
	MOV R4, R1; Initialize R5 to count iterations
LOOP
    CMP R1, R2
	BLS DONE
    ADD R5, R5, #1      ; Increment loop counter
    SUBS R1, R1, #10    ; Subtract 10 from R1
    BNE LOOP              ; Repeat loop
DONE
    MLS R4,R2,R5,R4
	LSL R5,#4
	ADD R5,R4
STOP
    B STOP
NUM DCD 0x40           ; Example hex number (40 in hex)
    AREA mydata, DATA, READWRITE
RES DCD 0               ; Storage for result
    END
