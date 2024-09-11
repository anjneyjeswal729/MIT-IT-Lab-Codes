#include <stdio.h>
#include <stdlib.h>

typedef struct {
    int pid;
    int at;  // Arrival Time
    int bt;  // Burst Time
    int remtime; // Remaining Time
    int priority; // Priority
    int wat;
    int tat;
} proc;

proc* inputs(int* num_processes) {
    printf("Number of processes: ");
    scanf("%d", num_processes);
    proc* p = (proc*)malloc(*num_processes * sizeof(proc));
    for (int i = 0; i < *num_processes; i++) {
        printf("Enter Id, AT, BT, Priority: ");
        scanf("%d %d %d %d", &p[i].pid, &p[i].at, &p[i].bt, &p[i].priority);
        p[i].remtime = p[i].bt;
        p[i].wat=0;
    }
    return p;
}

void rr() {
    int n;
    proc* p = inputs(&n);
    int queue[50];
    int arr[n];
    for (int i=0;i<n;i++){
        arr[i]=0;
    }
    int f=-1;
    int r=-1;
    int q; // Time Quantum
    printf("Enter time quantum: ");
    scanf("%d", &q);

    int done = 0, time = 0;
    int TAT = 0, WAT = 0;
    while(f==-1 && r==-1){
        for (int i=0;i<n;i++){
            if (p[i].at<=time){
                queue[++f]=i;
                r+=2;
            }
        }
        if (f==-1 && r==-1)
            time++;
    }

    while (f<r){
        int p1=queue[f++];

        int count=q;
        arr[p1]=0;
        while(p[p1].remtime>0 && p[p1].at<=time && count>0){
            time++;
            p[p1].remtime--;
            count--;
            for (int i=0;i<n;i++){
                if (i==p1){
                    continue;
                }
                if(p[i].at==time && arr[i]!=1){
                    queue[r++]=i;
                    arr[i]=1;
                }
            }
        }
        if(p[p1].remtime!=0){
            queue[r++]=p1;
            
        }
        else{
            TAT+=(time-p[p1].at);
            p[p1].tat=time-p[p1].at;
            p[p1].wat=time-p[p1].at-p[p1].bt;
            WAT+=(time-p[p1].at-p[p1].bt);
        }
        printf("%d\n",p1+1);
    }
    for (int i=0;i<n;i++){
        printf("Pid:%d, Waiting Time:%d, Turnaround Time:%d\n",i+1,p[i].wat,p[i].tat);
    }
    
    printf("Average Waiting Time: %f\n", (float)WAT / n);
    printf("Average Turn Around Time: %f\n", (float)TAT / n);
    free(p);
}

void SJFNon(){
    int n;
    proc* p = inputs(&n);
    int done=0;
    int time=0;
    while(done<n){
        int min=9999;
        int mini=-1;
        for (int i=0;i<n;i++){
            if(p[i].remtime>0 && p[i].at<=time && p[i].bt<min){
                min=p[i].bt;
                mini=i;
            }
        }
        time=time+p[mini].bt;
        p[mini].remtime=0;
        done++;
        p[mini].wat=(time-p[mini].bt-p[mini].at);
        p[mini].tat=(time-p[mini].at);
        printf("PID:%d\n",mini+1);
    }
    int TAT=0,WAT=0;
    for (int i=0;i<n;i++){
        printf("Pid:%d, Waiting Time:%d, Turnaround Time:%d\n",i+1,p[i].wat,p[i].tat);
        TAT+=p[i].tat;
        WAT+=p[i].wat;
    }
    printf("Average Waiting Time: %f\n", (float)WAT / n);
    printf("Average Turn Around Time: %f\n", (float)TAT / n);
}

int mini(proc* p,int n,int time){
    int min=9999;
        int m=-1;
        for(int i=0;i<n;i++){
            if(p[i].at<=time && p[i].remtime>0 && p[i].remtime<min){
                min=p[i].remtime;
                m=i;
            }
        }
    return m;
}

void SJFpre(){
    int n;
    proc* p = inputs(&n);
    int time=0;
    int done=0;
    while(done<n){
        int m=mini(p,n,time);
        int k=m;
        while(k==m){
            time++;
            p[m].remtime--;
            k=mini(p,n,time);
        }
        printf("%d\n",m+1);
        if(p[m].remtime==0){
            done++;
            p[m].tat=time-p[m].at;
            p[m].wat=time-p[m].at-p[m].bt;
        }
    }
    int TAT=0,WAT=0;
    for (int i=0;i<n;i++){
        printf("Pid:%d, Waiting Time:%d, Turnaround Time:%d\n",i+1,p[i].wat,p[i].tat);
        TAT+=p[i].tat;
        WAT+=p[i].wat;
    }
    printf("Average Waiting Time: %f\n", (float)WAT / n);
    printf("Average Turn Around Time: %f\n", (float)TAT / n);

}

void priority() {
    int n;
    proc* p = inputs(&n);
    int time = 0;
    int done = 0;

    while (done < n) {
        int prior = 9999;
        int pi = -1;

        for (int i = 0; i < n; i++) {
            if (p[i].priority < prior && p[i].at <= time && p[i].remtime > 0) {
                pi = i;
                prior = p[i].priority;
            }
        }

        if (pi == -1) {
            time++;
            continue;
        }

        printf("%d\n", pi + 1);
        time += p[pi].remtime;
        p[pi].remtime = 0;
        p[pi].tat = time - p[pi].at;
        p[pi].wat = time - p[pi].at - p[pi].bt;
        done++;
    }

    int TAT = 0, WAT = 0;
    for (int i = 0; i < n; i++) {
        printf("Pid:%d, Waiting Time:%d, Turnaround Time:%d\n", i + 1, p[i].wat, p[i].tat);
        TAT += p[i].tat;
        WAT += p[i].wat;
    }
    printf("Average Waiting Time: %f\n", (float)WAT / n);
    printf("Average Turnaround Time: %f\n", (float)TAT / n);
}

int main() {
    int c;
    while (1) {
        printf("1. Round Robin\n2. SJF Preemptive\n3. SJF Non-Preemptive\n4. Priority\n5. Exit\n");
        scanf("%d", &c);
        switch (c) {
            case 1:
                rr();
                break;
            case 2:
                SJFpre();
                break;
            case 3:
                SJFNon();
                break;
            case 4:
                priority();
                break;
            case 5:
                exit(0);
            default:
                printf("Invalid choice. Please try again.\n");
                break;
        }
    }
    return 0;
}
