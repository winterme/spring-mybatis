# spring-mybatis

模拟mybatis自定义一个 mebatis，并将mapper 交给spring管理,还提供基本的增删查改，service 层

BaseService
BaseServiceImpl

例子：

    public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements UserInfoService {}

使用方法：

1. application.properties 修该数据库连接参数
2. com.zzq.mybatis.AutoConfiguration(或者启动类上 Application) 上修改 @MapperScanner 扫描mapper路径
3. 在mapper包下添加 Mapper类，并加上 @com.zzq.mybatis.annotation.Mapper注解
4. Select 查询在方法上使用 @com.zzq.mybatis.annotation.Select 注解，里面写 sql语句
    > 条件查询
    方法中的参数一定要跟sql语句中的 {key} 一致
