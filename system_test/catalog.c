/*
编程实现目录查询功能：

- 功能类似ls -lR
- 查询指定目录下的文件及子目录信息，显示文件的类型、大小、时间等信息
- 递归显示子目录中的所有文件信息

*/

#include<stdio.h>
#include <errno.h>
#include<stdlib.h>
#include<string.h>
#include <dirent.h>
#include <sys/stat.h>
#include<time.h>

#define MAX_PATH 1024


// #include <dirent.h>
// struct dirent *readdir(DIR *dirp);

// struct dirent {
//     ino_t d_ino; /* 该文件的结点数目 */
//     off_t d_off; /* 是文件在目录中的编移 */
//     unsigned short d_reclen; /* 文件的长度   */
//     unsigned char d_type; /* 文件类型 */
//     char d_name[256]; /* 文件名字 */
// };



// int stat(const char *restrict pathname, struct stat *restrict buf);提供文件名字，获取文件对应属性。

// int fstat(int filedes, struct stat *buf);通过文件描述符获取文件对应的属性。

// int lstat(const char *restrict pathname, struct stat *restrict buf);连接文件描述命，获取文件属性。2 文件对应的属性

// struct stat {

//         mode_t     st_mode;       //文件对应的模式，文件，目录等

//         ino_t      st_ino;       //inode节点号

//         dev_t      st_dev;        //设备号码

//         dev_t      st_rdev;       //特殊设备号码

//         nlink_t    st_nlink;      //文件的连接数

//         uid_t      st_uid;        //文件所有者

//         gid_t      st_gid;        //文件所有者对应的组

//         off_t      st_size;       //普通文件，对应的文件字节数

//         time_t     st_atime;      //文件最后被访问的时间

//         time_t     st_mtime;      //文件内容最后被修改的时间

//         time_t     st_ctime;      //文件状态改变时间

//         blksize_t st_blksize;    //文件内容对应的块大小

//         blkcnt_t   st_blocks;     //文件内容对应的块数量

//       };


// 打印文件信息
void print_file_info(char *pathname);  
// 递归展示文件或文件夹
void display_file(char *pathname);

void display_file(char *pathname) {
    DIR *dfd;
    char name[MAX_PATH];
    struct dirent *dp;
    if ((dfd = opendir(pathname)) == NULL) {
        printf("can't open %s\n %s", pathname,strerror(errno));
        return;
    }
    while ((dp = readdir(dfd)) != NULL) {
        // 跳过当前目录和上一层目录以及隐藏文件
        if (strncmp(dp->d_name, ".", 1) == 0)
            continue; 
        if (strlen(pathname) + strlen(dp->d_name) + 2 > sizeof(name)) {
            printf("name %s %s too long\n", pathname, dp->d_name);
        } else {
            memset(name, 0, sizeof(name));
            sprintf(name, "%s/%s", pathname, dp->d_name);
            print_file_info(name);
        }
    }
    closedir(dfd);

}
void print_file_info(char *pathname) {
    struct stat filestat;
    if (stat(pathname, &filestat) == -1) {
        printf("cannot read the file %s", pathname);
        return;
    }

    if ((filestat.st_mode & S_IFMT) == S_IFDIR) {
        printf("%s st_mode: dir, st_mtime: %s, size: %8ld\n", pathname, asctime(gmtime(&filestat.st_mtime)), filestat.st_size);
        display_file(pathname);
    } else {
        printf("%s st_mode: file, st_mtime: %s, size: %8ld\n", pathname, asctime(gmtime(&filestat.st_mtime)), filestat.st_size);
    }
}

int main(int argc, char *argv[]) {
    if (argc == 1) {
        display_file("..");
    } else {
        display_file(argv[1]);
    }
    return 0;
}
