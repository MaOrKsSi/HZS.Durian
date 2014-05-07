
public class 混淆入口 {

    public static void main(final String[] args) {
        dd d1 = new dd();
        d1.close();
        org.hzs.常用.d对象池.put(null, null);
        org.hzs.常用.d对象池.get(null);
    }
}

class dd implements org.hzs.Close {

    short jk = 90;
    public String[] d = {"", "sadfsdf"};
    private org.hzs.sql.视图[] d2 = null;
}
