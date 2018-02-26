#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<signal.h>
#include<sys/wait.h>

void pipe_interaction();


int main() {

    pipe_interaction();
    return 0;
}

/*
子进程1每隔1秒通过管道向子进程2发送数据: I send you x times (x初值为1，每次发送后做加一操作)，子进程2从管道读出信息，并显示在屏幕上。
*/
void pipe_interaction() {
    int i, r;
    int p1, p2;
    int fd[2];

    char buf[50], s[50];

    pipe(fd);

    while((p1 = fork()) == -1);

    if (p1 == 0) {
        int x = 1;
        while (x < 10) {
            // lockf(fd[1], 1, 0);
            sprintf(buf, "I send you %d times\n", x);
            printf("process p1.\n");

            write(fd[1], buf, 50);
            x++;

            sleep(1);
            wait(0);
            // lockf(fd[1], 0, 0);
        }
        exit(0);

    } else {
        while((p2 = fork()) == -1);

        if (p2 == 0) {
            int y = 1;
            // lockf(fd[1], 1, 0);

            while ((r = read(fd[0], s, 50)) != -1) {
                printf(" I receive %d times\n", y);
                printf("%s\n", s);
                y++;
                wait(0);
            }

            sleep(1);
            // lockf(fd[1], 0, 0);
            exit(0);
        }


    }
}