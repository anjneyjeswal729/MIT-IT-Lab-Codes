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
    c = 0;
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
                printf("eh");
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
    return 0;
}
