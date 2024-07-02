
	AREA    RESET, DATA, READONLY
    EXPORT  __Vectors

__Vectors 
	DCD  0x10001000     ; stack pointer value when stack is empty
    DCD  Reset_Handler  ; reset vector
  
    ALIGN

	AREA mycode, CODE, READONLY
	ENTRY
	EXPORT Reset_Handler
	
Reset_Handler
	LDR R0,=VALUE
	MOV R1,#0
OUL
	CMP R1,#5
	BEQ DONE
	MOV R2,R1
	ADD R2,R2,#1
	MOV R3,R1
INLO
	LDR R4,[R0,R3,LSL #2]
	LDR R5,[R0,R2,LSL #2]
	CMP R4,R5
	CMPGT R4,R5
	MOVGT R3,R2
	ADD r2, r2, #1             
    CMP r2, #6      
    BNE INLO           

    LDR r4, [r0, r1, LSL #2]   
    LDR r5, [r0, r3, LSL #2]  
    STR r5, [r0, r1, LSL #2]   
    STR r4, [r0, r3, LSL #2]

    ADD r1, r1, #1           
    B OUL              
DONE
stop b stop	
	AREA MYCC,DATA,READWRITE
VALUE DCD 5,2,7,3,9,1
