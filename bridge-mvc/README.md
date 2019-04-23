
# 桥接 Spring MVC 与 Stripes

    这两个框架都是前端的MVC框架，Spring MVC是单例的，而Stripes的ActionBean是prootype类型的。
    
    由于历史原因，老旧的工程使用了Stripes作为MVC框架，如果要接入公司的微服务环境则需要做重构，这会花费非常多的精力，
    
    所以这里做了一个桥接改造，用Spring MVC拦截到请求后再转发给Stripes的ActionBean做处理。
    
    具体请参考[链接](https://www.cnblogs.com/ocean234/p/9663255.html)

