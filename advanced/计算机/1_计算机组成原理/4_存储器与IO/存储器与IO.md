

### SRAM 与 DRAM

    SRAM：静态存储器，一个bit数据需要6~8个晶体管来存储，电路简单访问快速，但存储密度不高，常用来做CPU的高速缓存（L1/L2/L3）。
    
    DRAM：动态存储器，由一个晶体管+一个电容来存储一个bit，但是电容会不断漏电，所以要求定时刷新充电才能保持数据不丢失，常用来做内存。
    
### SSD 与 HDD

    Solid-state disk
    
    Hard Disk Drive
    

### 局部性原理与缓存
```
int[] arr = new int[64 * 1024 * 1024];
// 循环 1
for (int i = 0; i < arr.length; i++) arr[i] *= 3;
// 循环 2（Cache Line的大小一般为64Byte，所以这里步长为16）
for (int i = 0; i < arr.length; i += 16) arr[i] *= 3
```
如上程序，循环1与循环2的执行时间相差不大。
    
CPU与高速缓存L1/L2/L3的映射关系：
    1、直接映射，通常采用mod运算（实际是h&(n-1)，类似HashMap），缓存行中用组标记（Tag）存储取模截取后留下的高位，来解决取模冲突的问题。
    2、全相连映射
    3、组相连映射
高速缓存写策略：
    1、写直达（Write-Through），即就算Cache Block里有数据，也每次都要写入主内存（类似volatile的效果）
    2、写回（Write-Back），即判断是否Cache Block有数据且原数据是否为脏数据，如果是把源数据写回内存，并把当前数据写入Cache Block，并标记为脏数据
    
    