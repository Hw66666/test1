import com.sun.corba.se.impl.orbutil.CorbaResourceUtil;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author shkstart
 * @create 2022-03-23 17:17
 */
public class Test01 {
    @Test
    public void test01(){
        Jedis jedis = new Jedis("localhost",6379);
        jedis.set("username","james");
        jedis.set("address","CHA");
        jedis.set("age","20");
        System.out.println(jedis.get("age"));
        System.out.println(jedis.get("username"));
        System.out.println(jedis.get("address"));
    }
    @Test
    public void test02(){
        Jedis jedis = new Jedis("localhost",6379);
        jedis.hset("myhash","name","kobe");
        jedis.hset("myhash","age","33");
        jedis.hdel("myhash","age");
        System.out.println(jedis.hget("myhash","name"));
        Map<String,String> map = jedis.hgetAll("myhash");
        Set<Map.Entry<String,String>> entries =map.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            System.out.println(entry.getKey()+"-------"+entry.getValue());
        }
    }
    @Test
    public void test03(){
        Jedis jedis = new Jedis("localhost",6379);
        jedis.lpush("mylist","a","b","c");
        jedis.rpush("mylist","o","p");
        jedis.lpop("mylist");
        jedis.rpop("mylist");
        List<String> mylist = jedis.lrange("mylist", 0, -1);
        System.out.println(mylist);
    }
    @Test
    public void test04(){
        Jedis jedis = new Jedis("localhost",6379);
        jedis.sadd("myset","a","b","c","d","e");
        jedis.srem("myset","a","m","z");
        Set<String> myset = jedis.smembers("myset");
        System.out.println(myset);
    }
    @Test
    public void test05(){
        Jedis jedis = new Jedis("localhost",6379);
        jedis.zadd("mysortset",80,"miller");
        jedis.zadd("mysortset",56,"kobe");
        jedis.zadd("mysortset",66,"durant");
        jedis.zadd("mysortset",98,"green");
        jedis.zrem("mysortset","Green");
        Set<String> mysortset = jedis.zrange("mysortset", 0, -1);
        System.out.println(mysortset);
    }
    @Test
    public void test06(){
        JedisPoolConfig config = new JedisPoolConfig();//创建一个配置对象
        config.setMaxTotal(50);//设置最大的连接数量
        config.setMaxIdle(10);//最大的空闲连接
        JedisPool jedisPool =new JedisPool(config,"localhost",6379);
        Jedis jedis = jedisPool.getResource();
        jedis.zadd("mysortset",80,"miller");
        jedis.zadd("mysortset",56,"kobe");
        jedis.zadd("mysortset",66,"durant");
        jedis.zadd("mysortset",98,"green");
        jedis.zrem("mysortset","Green");
        Set<String> set = jedis.zrange("mysortset",0,-1);
        System.out.println(set);
        jedis.close();

    }
}
