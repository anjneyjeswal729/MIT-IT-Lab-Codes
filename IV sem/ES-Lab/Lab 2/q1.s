	AREA RESET, DATA, READONLY
	EXPORT __Vectors
__Vectors
    DCD 0x10001000 ; stack pointer value when stack is empty
    DCD Reset_Handler ; reset vector
    ALIGN
    AREA MYCODE, CODE, READONLY
    ENTRY
    EXPORT Reset_Handler
Reset_Handler
	MOV R0,#10
	MOV R2,#1
LOOP
	MLA R1,R2,R0,R1
	SUBS R0,R0,#1
	BNE LOOP
STOP
	B STOP
	END