import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class Test01 {

    @Test
    public void test01(){
        String image="http://image.leyou.com/images/1/0/1524297321974.jpg";
        String res=StringUtils.replace(image,"image.leyou.com","image.shop.com");
        System.out.println(res);
    }
}
