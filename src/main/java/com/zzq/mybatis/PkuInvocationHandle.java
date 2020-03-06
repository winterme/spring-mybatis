package com.zzq.mybatis;

import com.zzq.mybatis.annotation.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PkuInvocationHandle implements InvocationHandler {

    private Logger LOG = LoggerFactory.getLogger(PkuInvocationHandle.class);

    private static final JdbcHelper jdbcHelper = new JdbcHelper("jdbc:mysql://127.0.0.1:3306/licm?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=Hongkong&zeroDateTimeBehavior=convertToNull", "root", "root");

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (method.isAnnotationPresent(Select.class)) {
            String sql = method.getAnnotation(Select.class).value();

            Pattern compile = Pattern.compile("\\{.*?}");
            Matcher matcher = compile.matcher(sql);

            if(matcher.find()){
                StringBuffer  newSql = new StringBuffer();
                String[] split = sql.split("\\{.*?}");
                for (int i = 0; i < split.length; i++) {
                    newSql.append(split[i]).append("\""+ args[i] +"\"");
                }
                sql = newSql.toString();
            }

            Object list = jdbcHelper.list(sql);

            LOG.info("sql ===> " + sql);

            return list;
        }

        if(method.getName().equals("toString")){
            return proxy.getClass().getInterfaces()[0].getName();
        }

        return null;
    }


}
