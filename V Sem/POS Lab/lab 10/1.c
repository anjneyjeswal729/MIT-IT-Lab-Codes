//FIFO completed but Optimal not yet finished

#include <stdio.h>
#include <stdlib.h>

struct LL
{
    int p;
    struct LL *next;
};

struct LL *head = NULL;

void printll()
{
    struct LL *curr = head;
    while (curr != NULL)
    {
        printf("%d\t", curr->p);
        curr = curr->next;
    }
    printf("\n");
}

int check(int n)
{
    struct LL *curr = head;
    while (curr != NULL)
    {
        if (curr->p == n)
        {
            return 1;
        }
        curr = curr->next;
    }
    return 0;
}

int find_optimal(struct LL *pages[], int frame_count, int p, int current_index) {
    int farthest = -1, index_to_replace = -1;

    for (int i = 0; i < frame_count; i++) {
        int j;
        for (j = current_index; j < p; j++) {
            if (pages[i]->p == j) {
                if (j > farthest) {
                    farthest = j;
                    break;
                }
            }
        }
        if (j == p) {
            return i;
        }
    }

    return index_to_replace; 
}

int main(int argc, char const *argv[])
{
    int f, p;
    printf("Enter number of frames and pages: ");
    scanf("%d %d", &f, &p);

    int c = 0;
    printf("FIFO:\n");
    int pf = 0;
    for (int i = 0; i < p; i++)
    {
        int pid;
        printf("Enter Pno: ");
        scanf("%d", &pid);

        int n = check(pid);
        if (!n)
        {
            if (c < f)
            {
                struct LL *node = (struct LL *)malloc(sizeof(struct LL));
                node->p = pid;
                node->next = head;
                head = node;
                c++;
            }
            else
            {

                struct LL *oldest = head;
                while (oldest->next->next != NULL)
                {
                    oldest = oldest->next;
                }
                oldest->next->p = pid;
                oldest->next->next = head;
                head = oldest->next;
                oldest->next = NULL;
            }
        }
        else
        {
            pf++;
        }
        printll();
    }
    printf("Page faults:%d\n", pf);
    pf = 0;
    struct LL *curr = head;
    while (curr != NULL)
    {
        struct LL *temp = curr;
        curr = curr->next;
        free(temp);
    }
    head = NULL;
    printf("Enter number of frames and pages: ");
    scanf("%d %d", &f, &p);
    printf("Optimal\n");
    struct LL *pages[f]; 
    for (int i = 0; i < f; i++) {
        pages[i] = NULL;
    }

    int page_numbers[p]; 
    for (int i = 0; i < p; i++) {
        printf("Enter Pno: ");
        scanf("%d", &page_numbers[i]);
    }

    for (int i = 0; i < p; i++) {
        int pid = page_numbers[i];
        if (!check(pid)) { 
            if (pf < f) { 
                struct LL *node = (struct LL *)malloc(sizeof(struct LL));
                node->p = pid;
                node->next = head;
                head = node;
                pages[pf] = node;
                pf++;
            } else {
                int replace_index = find_optimal(pages, f, p, i);
                if (replace_index != -1) {
                    struct LL *node = (struct LL *)malloc(sizeof(struct LL));
                    node->p = pid;
                    node->next = head;
                    head = node;

                    pages[replace_index] = node; 
                }
            }
            printll(); 
        }
    }
    printf("Page faults: %d\n", pf);

    curr = head;
    while (curr != NULL) {
        struct LL *temp = curr;
        curr = curr->next;
        free(temp);
    }
    return 0;

    return 0;
}
