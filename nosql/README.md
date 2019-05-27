
## nosql
    对nosql的学习和研究

## 缓存旁路问题：Cache Aside Pattern
    DB与cache双写，先写DB还是先写cache？ （不能一概而论，应视情况而定，软件没有银弹）

### 先写DB再写缓存
    一般写cache的时候做delete操作，而不是set操作，以防止写cache成功而DB事务回滚
    如果DB做了主从高可用，一般要订阅binlog，从库同步完再delete一次cache，防止cache读到从库未同步的旧数据
    
### 先写缓存再写DB
    一般不建议先cache再DB，因为内存一断电就会丢失
    就算delete也还是有问题，如果并发情况下，delete完马上又有其他线程cache miss了，会直接读库，最后造成DB和cache数据不一致

业界一般采用第一种方案：先写DB，再delete缓存，并且订阅从库的biglog来再次更新缓存