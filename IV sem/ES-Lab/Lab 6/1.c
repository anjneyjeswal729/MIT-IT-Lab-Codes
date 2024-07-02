// 8 Bit binary Up Down Counter
#include<LPC17xx.h>

unsigned int i,j,c=0,f=0;;
unsigned long LED = 0x00000000;

int main(void)
{
	LPC_PINCON->PINSEL0 &= 0xFF0000FF;
	LPC_GPIO0->FIODIR |= 0X00000FF0;
	
	while(1)
	{
		LPC_GPIO0->FIOPIN=LED<<4;
		for(i=0;i<5000;i++);
		if(LED==255)
			f=1;
		
		LED++;
		if(f==1)
		{
			LED=0;
		}

		
	}
}