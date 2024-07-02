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
	MOV R0,#28
	MOV R1,#69
	
while CMP R0,R1
	BHI gret
	BLO low
	BEQ done
gret SUB R0,R0,R1
	B while
low SUB R1,R1,R0
	B while
	
done MOV R5,R1
STOP
	B STOP
	END