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
	LDR R0,=N1
	LDR R1,[R0]
	AND R2,R1,#0xF0
	LSR R2,#4
	AND R3,R1,#0x0F
	CMP R2,#0x09
	ADDHI R2,R2,#0x37
	ADDLS R2,R2,#0x30
	CMP R3,#0x09
	ADDHI R3,R3,#0x37
	ADDLS R3,R3,#0x30
	MOV R5,R2
	LSL R5,#8
	ADD R5,R5,R3
	LDR R0,=DEST
	STR R5,[R0]
STOP
	B STOP
N1 DCD 0X64
	AREA JJ,DATA,READWRITE
DEST DCD 0
	END
