#include <stdio.h>
#include <stdlib.h>

int main() {
    int p, r;

    printf("Enter number of processes and resources: ");
    scanf("%d %d", &p, &r);
    
    int avai[r], maxi[p][r], allot[p][r], need[p][r], finish[p];
    
    printf("Enter resources available: ");
    for (int i = 0; i < r; i++) {
        scanf("%d", &avai[i]);
    }

    printf("Enter max resources for each process:\n");
    for (int i = 0; i < p; i++) {
        for (int j = 0; j < r; j++) {
            scanf("%d", &maxi[i][j]);
        }
        finish[i] = 0; 
    }

    printf("Enter initial allocation:\n");
    for (int i = 0; i < p; i++) {
        for (int j = 0; j < r; j++) {
            scanf("%d", &allot[i][j]);
            need[i][j] = maxi[i][j] - allot[i][j];
            avai[j] -= allot[i][j]; 
        }
    }

    printf("Computing Safe Sequence:\n");
    int safestate[p];
    int f = 0;

    while (f < p) {
        int found = 0;
        for (int i = 0; i < p; i++) {
            if (finish[i] == 0) { 
                int s = 0;
                for (int j = 0; j < r; j++) {
                    if (need[i][j] > avai[j]) {
                        s = 1; 
                        break;
                    }
                }
                if (s == 0) {
                    safestate[f] = i;
                    f++;
                    finish[i] = 1; 
                    for (int j = 0; j < r; j++) {
                        avai[j] += allot[i][j]; 
                    }
                    found = 1; 
                }
            }
        }
        if (!found) {
            printf("No safe sequence found. System is not in a safe state.\n");
            return 0;
        }
    }

    printf("Safe sequence is: ");
    for (int j = 0; j < p; j++) {
        printf("P%d ", safestate[j]);
    }
    printf("\n");

    return 0;
}
