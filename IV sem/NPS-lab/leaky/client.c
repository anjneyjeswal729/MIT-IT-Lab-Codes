//Client
#include<string.h>
#include<arpa/inet.h>
#include<stdlib.h>
#include<stdio.h>
#include<unistd.h>
#include<sys/socket.h>
#include<sys/types.h>
#include<netinet/in.h>
#include<fcntl.h>
#include<sys/stat.h>

int main() 
{
  int s, r, recb, sntb, x;
  struct sockaddr_in server;
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
  server.sin_addr.s_addr = inet_addr("127.0.0.1");
  r = connect(s, (struct sockaddr * ) & server, sizeof(server));
  if (r == -1) 
  {
    printf("\nConnection error.");
    exit(0);
  }
  printf("\nSocket connected.");
  printf("\n\n");
  int slots[11]={1,2,3,5,6,8,11,12,15,16,19};
  int size,i=1,j=0,k=1;
  while(1){
  	if(i==slots[j]){
  	        size=4;
  		sntb=send(s,&size,sizeof(size),0);
  		if(sntb==-1){
  			printf("Message sending error");
  			close(s);
  			exit(0);
  		}
  		printf("Packet %d sent\n",k++);
  		j++;
  	}
  	else{
  	    size=0;
  	    sntb=send(s,&size,sizeof(size),0);
  	    i++;
  	    continue;
  	}
  	recb=recv(s,buff,sizeof(buff),0);
  	if(recb==-1){
  	  printf("Message receive error");
  	  close(s);
  	  exit(0);
  	}
  	puts(buff);
  	i++;
  }
}
