#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>

int main(){
    pid_t pidd;
    pidd=fork();
    if (pidd==0){
        printf("This is child process, pid:%d ppid:%d\n",getpid(),getppid());
    }
    else if (pidd>0){
        printf("This is parent process, pid:%d ppid:%d\n",getpid(),getppid());
    }
    return 0;

}
