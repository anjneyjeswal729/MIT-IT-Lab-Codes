//Server
#include<string.h>
#include<unistd.h>
#include<sys/socket.h>
#include<sys/types.h>
#include<netinet/in.h>
#include<stdlib.h>
#include<stdio.h>

int main() 
{
  int s, r, recb, sntb, x, ns, a = 0;
  socklen_t len;
  struct sockaddr_in server, client;
  char buff[50], buff2[50];
  s = socket(AF_INET, SOCK_STREAM, 0);
  if (s == -1) 
  {
    printf("\nSocket creation error.");
    exit(0);
  }
  printf("\nSocket created.");
  server.sin_family = AF_INET;
  server.sin_port = htons(3388);
  server.sin_addr.s_addr = htonl(INADDR_ANY);
  r = bind(s, (struct sockaddr * ) & server, sizeof(server));
  if (r == -1) 
  {
    printf("\nBinding error.");
    exit(0);
  }
  printf("\nSocket binded.");
  r = listen(s, 1);
  if (r == -1) 
  {
    close(s);
    exit(0);
  }
  printf("\nSocket listening.");
  len = sizeof(client);
  ns = accept(s, (struct sockaddr * ) & client, & len);
  if (ns == -1) 
  {
    close(s);
    exit(0);
  }
  printf("\nSocket accepting.\n\n");
  int b_size=10,p_size,cur=0,i=1,j=0,k=1;
  while(1){
  		recb=recv(ns,&p_size,sizeof(p_size),0);
  		if(recb==-1){
  			printf("Message receive error");
  			close(s);
  			close(ns);
  			exit(0);
  		}
  		if(p_size==0);
  		else if(cur+p_size>b_size){
  			printf("Packet %d rejected due to congestion\n",k++);
  			strcpy(buff,"Packet rejected\n");
  			sntb=send(ns,buff,sizeof(buff),0);
  		}
  		else{
  			cur+=p_size;
  			printf("Packet %d of size %d accepted\n",k++,p_size);
  			strcpy(buff,"Packet accepted\n");
  			sntb=send(ns,buff,sizeof(buff),0);
  		}
 	cur--;
 	printf("1 byte data released\n");
 	printf("Current data size: %d\n",cur);
  	printf("Time: %d seconds\n\n",i++);
  	if(cur==0){
  		printf("Process complete\n");
  		break;
  	}
  }
  close(s);
  close(ns);
  exit(0);
}
