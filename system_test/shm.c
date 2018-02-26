#include<stdio.h>
#include<stdlib.h>
#include <sys/shm.h>
#include<sys/types.h>
#include <sys/ipc.h>
#include <sys/sem.h>
#include <string.h>
#include <unistd.h>
#include <wait.h>

#define BUF_SIZE 32
int semid;

union semun {
    int              val;    /* Value for SETVAL */
    struct semid_ds *buf;    /* Buffer for IPC_STAT, IPC_SET */
    unsigned short  *array;  /* Array for GETALL, SETALL */
};

void P(int semid, int index)     //用于请求资源
{
    struct sembuf sem;
    sem.sem_num = index;    //表示是第几个灯
    sem.sem_op = -1;        //加到当前信号灯的值
    sem.sem_flg = 0;       //：操作标记：0或IPC_NOWAIT等
    semop(semid, &sem, 1);               //1:表示执行命令的个数
}

void V(int semid, int index)     //用于释放资源
{
    struct sembuf sem;
    sem.sem_num = index;
    sem.sem_op =  1;
    sem.sem_flg = 0;
    semop(semid, &sem, 1);
}


int main()
{
    //获得源文件和目标文件的文件指针,输入来指定文件名
    FILE *infp, *outfp;
    if ((infp = fopen("origin.txt", "r")) == NULL)
    {
        printf("canot find the source file!\n");
        exit(0);
    }
    if ((outfp = fopen("target.txt", "w")) == NULL)
    {
        printf("canot open the output file!\n");
        exit(0);
    }

    //get共享内存
    int shm_s;
    char *s;
    shm_s = shmget(10, sizeof(char) * BUF_SIZE, IPC_CREAT | 0666);
    s = (char *)shmat(shm_s, NULL, SHM_R | SHM_W);

    //获得两个信号灯
    union semun semopts;
    semid = semget(11, 2, IPC_CREAT | 0666);
    semopts.val = 1;   // 让读取进程先执行
    semctl(semid, 0, SETVAL, semopts);
    semopts.val = 0;   // 让写入进程后执行
    semctl(semid, 1, SETVAL, semopts);

    int get, put;
    if ((get = fork()) == 0) //用于将文件中内容读入共享内存s的进程
    {
        int shm_s;
        char *s;

        shm_s = shmget(10, sizeof(char) * BUF_SIZE, IPC_CREAT | 0666);
        s = (char *)shmat(shm_s, NULL, SHM_R | SHM_W); // 绑定到共享内存块

        int i = 0;
        do
        {
            P(semid, 0);
            printf("It is getting the txt from origin.txt\n");
            while ((s[i] = fgetc(infp)) != EOF)
            {
                printf("%c", s[i]);
                i++;
                if (i == BUF_SIZE)
                {
                    V(semid, 1);
                    i = 0;
                    break;
                }

            }
        }
        while (s[i] != EOF);


    }
    else if ((put = fork()) == 0) //用于将共享内存t中的写入到目标文件的进程
    {
        int shm_s;
        char *s;
        shm_s = shmget(10, sizeof(char) * BUF_SIZE, IPC_CREAT | 0666);
        s = (char *)shmat(shm_s, NULL, SHM_R | SHM_W); // 绑定到共享内存块t
        int j = 0;
        do
        {
            P(semid, 1);
            printf("It is putting the message into target.txt\n");
            while ( s[j] != EOF)
            {
                fputc(s[j], outfp);
                printf("%c", s[j]);
                j++;
                if (j == BUF_SIZE)
                {
                    V(semid, 0);
                    j = 0;
                    break;
                }
            }

        }
        while (s[j] != EOF);

    }

    get = wait(&get);
    put = wait(&put);

    //销毁共享内存
    shmctl(shm_s, IPC_RMID, 0);

    //销毁信号灯
    semctl(semid, 2, IPC_RMID, 0);

    fclose(infp);
    fclose(outfp);

    return 0;
}
