package cn.pa.jsoup;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.Test;

import java.net.URLDecoder;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EncodeTest {
    @Test
    public void testEncode(){
        String ASCII ="\\u4e2d\\u836f\\u7efc\\u5408100\\u4e2a\\u91cd\\u8981\\u77e5\\u8bc6\\u70b9";
        String URLCODE = "%E4%B8%AD%E8%8D%AF%E7%BB%BC%E5%90%88100%E4%B8%AA%E9%87%8D%E8%A6%81%E7%9F%A5%E8%AF%86%E7%82%B9";
        String Native;
        Native = URLDecoder.decode(URLCODE);
        System.out.println(Native);


        Native = unicodeToString(ASCII);

        System.out.println(Native);

    }

    public static String unicodeToString(String str) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;
    }
    @Test
    public void uuid(){
        String uuid  = UUID.randomUUID().toString();
        System.out.println(uuid);
        uuid = uuid.replaceAll("-","");
        System.out.println(uuid);
    }

    @Test
    public void testEquals(){
        Object o = 'a';
        System.out.println(o.equals(null));
        System.out.println(o.equals('a'));
        String s = "abc";
        System.out.println(s.equals("abc"));

    }
}
