#include <LPC17xx.h>

unsigned int i, j,f;
unsigned long LED = 0;
unsigned int SW2 = 0;

int main(void) {
    SystemInit();
    SystemCoreClockUpdate();
    
    // Configure pin P2.12/EINT2 as input
    LPC_PINCON->PINSEL4 &= ~(3 << 24);
    LPC_GPIO2->FIODIR |= (1 << 12);

    // Configure LED pins as output
    LPC_PINCON->PINSEL0 &= 0xFF0000FF;
    LPC_GPIO0->FIODIR |= 0x00000FF0;
    
    while (1) {
        // Read the state of SW2
        SW2 = (LPC_GPIO2->FIOPIN >> 12) & 1;
        
        // If SW2 is pressed (1), decrement the counter
        if (SW2 == 1) {
					LPC_GPIO0->FIOPIN=LED<<4;
					
					if(LED==0)
						f=1;
					
					LED--;
					if(f==1)
					{
						LED=255;
						f=0;
					}
            
        } 
        // If SW2 is not pressed (0), increment the counter
        else {
            LPC_GPIO0->FIOPIN=LED<<4;
						
						if(LED==255)
							f=1;
						
						LED++;
						if(f==1)
						{
							LED=0;
							f=0;
						}
        }
    }
}
