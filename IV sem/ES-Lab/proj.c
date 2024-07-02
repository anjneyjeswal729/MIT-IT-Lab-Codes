#include <LPC17xx.h>

unsigned char Msg1[36]={"Temperature C:"};
unsigned char Msg2[16]={""};
unsigned char Msg3[16]={""};


unsigned int i = 0, j = 0, k = 0;
unsigned long int var1 = 0, var2 = 0,var3=0;
unsigned long int val = 23;
float t2 = 0, res = 0;
unsigned int i1=0;


unsigned int flag;

void clear_ports()
{
	LPC_GPIO0->FIOCLR =0xF<<15;
	LPC_GPIO0->FIOCLR =1<<19;
	LPC_GPIO0->FIOCLR =1<<20;
}

void delay_lcd(unsigned int r)
{
	unsigned int t;
	for(t=0;t<r;t++);
}

void write(int temp2,int type)
{
	clear_ports();
	LPC_GPIO0->FIOPIN=temp2;
	if(!type)
	{
		LPC_GPIO0->FIOCLR = 1<<19;
	}
	else
	{
		LPC_GPIO0->FIOSET = 1<<19;
	}
	LPC_GPIO0->FIOSET = 1<<20;
	delay_lcd(25);
	LPC_GPIO0->FIOCLR = 1<<20;
	return;
}

void lcd_comdata(int temp1,int type)
{
	int temp2 = temp1&0xF0;
	temp2 <<= 11;
	write(temp2,type);
	temp2=temp1&0x0F;
	temp2 <<= 15;
	write(temp2,type);
	delay_lcd(1000);
	return ;
}

void lcd_init()
{
	LPC_GPIO0->FIODIR |= 0xF<<15|1<<19|1<<20;
	clear_ports();
	delay_lcd(3200);
	
	lcd_comdata(0x33,0);
	delay_lcd(30000);
	
	lcd_comdata(0x32,0);
	delay_lcd(30000);
	
	lcd_comdata(0x28, 0);
	delay_lcd(30000);
	
	lcd_comdata(0x0c, 0);
	delay_lcd(800);

	lcd_comdata(0x06, 0);
	delay_lcd(800);

	lcd_comdata(0x01, 0);
	delay_lcd(10000);
	
	return;
}

void lcd_puts(unsigned char* str)
{
	unsigned int temp3,i=0;
	while(str[i])
	{
		temp3=str[i];
		lcd_comdata(temp3,1);
		i++;
		if(i==16)
		{
			lcd_comdata(0xC0,0);
		}
	}
	return;
}

void delay(unsigned int r1) {
    unsigned int r;
    for (r = 0; r < r1; r++);
    return;
}

void anti_clock_wise1 (){
var1 = 0x00000100;
for(i=0;i<=3;i++){
var1 = var1>>1;
LPC_GPIO0->FIOPIN = var1;
for(k=0;k<900;k++); // for step speed variation
}
return;
}
void motor1(float res) {

LPC_PINCON->PINSEL0 = 0xFFFF00FF;
LPC_GPIO0->FIODIR = 0x000000F0;
for (j = 0; j < 100; j++) {
anti_clock_wise1();
	
}
return;
}
void anti_clock_wise2 (int res){
var1 = 0x00000100;
for(i=0;i<=3;i++){
var1 = var1>>1;
LPC_GPIO0->FIOPIN = var1;
for(k=0;k<res;k++);
}
return;
}
void motor2(int res) {
LPC_PINCON->PINSEL0 = 0xFFFF00FF;
LPC_GPIO0->FIODIR = 0x000000F0;
for (j = 0; j < 100; j++) {
anti_clock_wise2(res);
}
return;
}
void anti_clock_wise3 (){
var1 = 0x00000100;
for(i=0;i<=3;i++){
var1 = var1>>1;
LPC_GPIO0->FIOPIN = var1;
for(k=0;k<9000;k++);
}
return;
}
void motor3(float res) {
	sprintf(Msg2, "%.2f", res);
    lcd_comdata(0xC0,0);
    delay(80000);
    lcd_puts(&Msg2[0]);
LPC_PINCON->PINSEL0 = 0xFFFF00FF;
LPC_GPIO0->FIODIR = 0x000000F0;
	for (j = 0; j < 100; j++) {
anti_clock_wise3();
}
return;
}





void ADC_IRQHandler(void) {
    t2 = (LPC_ADC->ADDR0 & (0xFFF << 4) >> 4);
    t2 = (t2 / 12.41) / 12;
    res = t2;
    if (res < val)
		{
			LPC_ADC->ADCR &= ~(1 << 24);
			return;
}
	sprintf(Msg2, "%.2f", res);
     lcd_comdata(0xC0,0);
    delay(80000);
    lcd_puts(&Msg2[0]);
    //motor2(res);
		if (res >= 18 && res <24)
		motor2(30000);
		else if(res >= 24 && res <25)
		motor2(20000);
	 else if (res >= 26 && res <=35)
		motor2(9000);
		 LPC_ADC->ADCR &= ~(1 << 24);
}



int main(void)
{
	SystemInit();
	SystemCoreClockUpdate();
	lcd_init();
	lcd_comdata(0x80,0);
	delay_lcd(800);
	lcd_puts(Msg1);
	lcd_comdata(0xC0,0);
	delay_lcd(800);
	 LPC_PINCON->PINSEL1 |= 1 << 14; // P1.31 as AD0.5
    LPC_SC->PCONP |= (1 << 12);
    LPC_ADC->ADCR = (1 << 0 | 1 << 24 | 1 << 21);
    LPC_ADC->ADINTEN = (1 << 0);
	 flag = 1;
    var1 = 0x80;
    lcd_comdata(var1,0);
    delay(80000);
    NVIC_EnableIRQ(ADC_IRQn);
    while (1) {
        LPC_ADC->ADCR = (1 << 0 | 1 << 24 | 1 << 21);
	//delay(100000);
    }
	return 0;
}


