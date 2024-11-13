// This has FIFO, Optimal and LRU page Replacement

#include <stdio.h>
#include <stdlib.h>


int pages[100];

int main(){
    int n;
    printf("Enter number of pages:");
    scanf("%d",&n);
    printf("Enter pages:");
    for (int i=0;i<n;i++){
        scanf("%d",&pages[i]);
    }
    int w;
    printf("Enter window size:");
    scanf("%d",&w);
    while(1){
        printf("1. FIFO\n2. LRU\n3. Optimal\n4. Exit\n");
        int ch,faults=0;
        scanf("%d",&ch);
        int frames[w];
        for (int i = 0; i < w; i++) {
            frames[i] = -1;
        }
        int m=0;
        switch (ch)
        {
        case 1:
            printf("FIFO\n");
            for (int i=0;i<n;i++){
                int flag=0;
                for (int j=0;j<w;j++){
                    if(frames[j]==pages[i]){
                        flag=1;
                        break;
                    }
                }
                if(flag==0){
                    frames[m]=pages[i];
                    m=(m+1)%w;
                    faults++;
                }
                for (int j=0;j<w;j++){
                    printf("%d ",frames[j]);
                }
                printf("\n");
            }
            printf("Faults:%d\n",faults);
            break;
        case 2:
            printf("LRU\n");
            for (int i=0;i<n;i++){
                int flag=0;
                for (int j=0;j<w;j++){
                    if(frames[j]==pages[i]){
                        flag=1;
                        break;
                    }
                }
                if(flag==0){
                    
                    for (int j=0;j<w;j++){
                        if(frames[j]==-1){
                            frames[j]=pages[i];
                            flag=1;
                            break;
                        }
                    }
                    if(flag==0){
                        int ind=-1;
                        int mini=100000;
                        for(int j=0;j<w;j++){
                            int k;
                            for(k=i-1;k>=0;k--){
                                if(frames[j]==pages[k]){
                                    break;
                                }
                            }
                            if(k<mini){
                                mini=k;
                                ind=j;
                            }
                        }
                        frames[ind]=pages[i];
                    }

                    faults++;
                    
                }
                for (int j=0;j<w;j++){
                    printf("%d ",frames[j]);
                }
                printf("\n");
            }
            printf("Faults:%d\n",faults);
            break;
        case 3:
            printf("Optimal\n");
            for(int i=0;i<n;i++){
                int flag=0;
                for(int j=0;j<w;j++){
                    if(pages[i]==frames[j]){
                        flag=1;
                        break;
                    }
                }
                if(!flag){
                    if(m<w){
                        frames[m]=pages[m];
                        m++;
                        faults++;
                    }
                    else{
                        int max=0;
                        int ind=-1;
                        for(int j=0;j<w;j++){
                            int fla=0;
                            for (int k=i+1;k<n;k++){
                                if(pages[k]==frames[j]){
                                    fla=1;
                                    if(max<k){
                                        max=k;
                                        ind=j;
                                    }
                                }
                            }
                            if(fla==0){
                                ind=j;
                                break;
                            }
                        }
                        frames[ind]=pages[i];
                        faults++;
                    }
                }
                for (int j=0;j<w;j++){
                    printf("%d ",frames[j]);
                }
                printf("\n");
            }
            printf("Faults:%d\n",faults);
            break;
        case 4:
            exit(0);
            break;
        
        default:
            break;
        }
    }
    return 0;
}
