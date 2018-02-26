#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<signal.h>
#include<sys/wait.h>

void create_process();


int main() {
    create_process();
    return 0;
}

void create_process() {

    int p1, p2;
    while((p1 = fork()) == -1);

    if (p1 == 0) {
        // child process one
        printf("process 1\n");
        wait(0);

        printf("1");
    } else {  // main process

        while((p2 = fork()) == -1);

        if (p2 == 0) {
            // child process two
            printf("process 2\n");
            wait(0);
            printf("2");
        } else {   // main process
            printf("process parent \n");
        }

    }
}