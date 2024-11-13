#include <stdio.h>
#include <stdlib.h>


void sort(int *a,int n){
    for (int i=0;i<n-1;i++){
        for (int j=0;j<n-i-1;j++){
            if(a[j]>a[j+1]){
                int temp=a[j];
                a[j]=a[j+1];
                a[j+1]=temp;
            }
        }
    }
}
int main()
{

    int n, h;
    printf("Enter Number of disc:");
    scanf("%d", &n);
    printf("Enter Max of disc:");
    int max;
    scanf("%d", &max);
    printf("Enter Head");
    scanf("%d", &h);
    printf("Enter Order");
    int a[n],b[n];
    for (int i = 0; i < n; i++)
    {
        scanf("%d", &a[i]);
        b[i]=a[i];
    }
    sort(b,n);
    while (1)
    {
        printf("1.FCFS\n2.SSTF\n3.SCAN\n4.CSCAN\n5.LOOK\n6.CLOOK\n");
        int ch;
        scanf("%d", &ch);
        int visited[n];
        int hh = h;
        int seek = 0;
        int mid=0;
        switch (ch)
        {
            case 1:
                printf("FCFS\n");
                // for (int i=0;i<n;i++){
                //     printf("%d\n",a[i]);
                // }
                for (int i = 0; i < n; i++)
                {
                    printf("%d->",a[i]);

                    seek += abs(hh - a[i]);
                    hh = a[i];
                }
                printf("Seek Time:%d\n", seek);
                break;
            case 2:
                printf("SSTF\n");
                // for (int i=0;i<n;i++){
                //     printf("%d\n",a[i]);
                // }
                for (int i = 0; i < n; i++)
                {
                    visited[i] = 0;
                }
                for (int i = 0; i < n; i++)
                {
                    int min = 100000;
                    int index = -1;
                    for (int j = 0; j < n; j++)
                    {
                        if (visited[j] == 0 && abs(hh - a[j]) < min)
                        {
                            min = abs(hh - a[j]);
                            index = j;
                        }
                    }
                    seek += abs(hh - a[index]);
                    printf("%d->",a[index]);
                    hh = a[index];
                    visited[index] = 1;
                }
                printf("Seek Time:%d\n", seek);
                break;
            case 3:
                printf("SCAN\n");
                
                // for (int i=0;i<n;i++){
                //     printf("%d\n",b[i]);
                // }
                for (int i=0;i<n;i++){
                    if(b[i]>=hh){
                        mid=i;
                        break;
                    }
                }
                for (int i=mid;i<n;i++){
                    seek+=abs(hh-b[i]);
                    printf("%d->",b[i]);
                    hh=b[i];
                }
                seek+=abs(hh-max+1);
                printf("%d->",max-1);

                hh=max-1;
                for(int i=mid-1;i>=0;i--){
                    seek+=abs(hh-b[i]);
                    printf("%d->",b[i]);
                    hh=b[i];
                }
                printf("Seek Time:%d\n", seek);

                break;
            case 4:
                printf("CSCAN\n");
                for (int i=0;i<n;i++){
                    if(b[i]>=hh){
                        mid=i;
                        break;
                    }
                }
                for (int i=mid;i<n;i++){
                    seek+=abs(hh-b[i]);
                    printf("%d->",b[i]);
                    hh=b[i];
                }
                seek+=abs(hh-max+1);
                seek+=max-1;
                hh=0;
                for(int i=0;i<mid;i++){
                    seek+=abs(hh-b[i]);
                    printf("%d->",b[i]);
                    hh=b[i];
                }
                printf("Seek Time:%d\n", seek);

                break;
            case 5:
                printf("LOOK\n");
                for (int i=0;i<n;i++){
                    if(b[i]>=hh){
                        mid=i;
                        break;
                    }
                }
                for (int i=mid;i<n;i++){
                    seek+=abs(hh-b[i]);
                    printf("%d->",b[i]);
                    hh=b[i];
                }
                for(int i=mid-1;i>=0;i--){
                    seek+=abs(hh-b[i]);
                    printf("%d->",b[i]);
                    hh=b[i];
                }
                printf("Seek Time:%d\n", seek);
                break;
            case 6:
                printf("CLOOK\n");
                for (int i=0;i<n;i++){
                    if(b[i]>=hh){
                        mid=i;
                        break;
                    }
                }
                for (int i=mid;i<n;i++){
                    seek+=abs(hh-b[i]);
                    printf("%d->",b[i]);
                    hh=b[i];
                }
                seek+=abs(hh-b[0]);
                hh=b[0];
                for(int i=0;i<mid;i++){
                    seek+=abs(hh-b[i]);
                    printf("%d->",b[i]);
                    hh=b[i];
                }
                printf("Seek Time:%d\n", seek);
                break;
            default:
                printf("Invalid Choice\n");
                return 0;
        }
    }
    return 0;
}
