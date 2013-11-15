
import com.hzs.Session;
import java.util.TreeMap;
import org.hzs.json.JSONArray;
import org.hzs.json.JSONObject;
import org.hzs.lang.ID;
import org.hzs.logging.error;
import org.hzs.sql.连接池;

public class 混淆入口 {

    private static String ji_s;
    private static JSONArray d1;
    private static ID d2;
    private static JSONObject d3;
    private static int i1;
    private static TreeMap<Integer, Session> mm;
    private static 连接池 nn;
    private static int ji_i;

    public static void main(String[] args) throws error {
        com.hzs.__.g解锁(null, null);
        com.hzs.__.g解锁表(null, null, null);
        com.hzs.__.g解锁记录(null, null, null);
        com.hzs.__.g锁定表(null, null, null);
        com.hzs.__.g锁定记录(null, null, null);

        com.hzs.Session session = (com.hzs.Session) com.hzs.Session.d副本();
        ji_i = session.i登入进度_i;
        ji_s = session.i手机号_s;
        d1 = session.i指令团_ArrayJSON;
        d3 = session.i指令_JSON;
        d2 = session.i操作员_ID;
        ji_s = session.i账套模_s;
        session.set(ji_s, null);
        mm.put(i1, session);
        mm.get(i1);
        nn = com.hzs.__.d连接池;

        com.hzs.医学_Session session1 = com.hzs.医学_Session.d副本();
        session1.set(ji_s, null);
        session1.toJSON(null);
        com.hzs.农学_Session session2 = com.hzs.农学_Session.d副本();
        session2.set(ji_s, null);
        session2.toJSON(null);
    }
}
