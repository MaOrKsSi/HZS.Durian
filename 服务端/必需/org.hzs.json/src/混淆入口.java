
import java.math.BigDecimal;
import java.math.BigInteger;
import org.hzs.json.JSONArray;
import org.hzs.json.JSONObject;
import org.hzs.logging.error;

public class 混淆入口 {

    public static void main(String[] args) throws CloneNotSupportedException, error {
        org.hzs.json.JSONObject jd_JSON = null;
        jd_JSON = org.hzs.json.JSONObject.d副本();
        jd_JSON.set("{a:'sdfdsfa',b:./a}", null);
//        jd_JSON.length();
//        jd_JSON.getDate("", null);
        jd_JSON.getBigDecimal("", null);
        jd_JSON.getBigInteger("", null);
        jd_JSON.get("");
        jd_JSON.getShort("", null);
        jd_JSON.getBoolean("", null);
        jd_JSON.getDouble("", null);
        jd_JSON.getInt("", null);
        jd_JSON.getJSONArray("", null);
        jd_JSON.getJSONObject("", null);
        jd_JSON.getLong("", null);
        jd_JSON.getString("", null);
        jd_JSON.getByte("", null);
        jd_JSON.clear();
//        jd_JSON.remove(null);
        jd_JSON.removeall();
        jd_JSON.put(null, null);
        jd_JSON.put("", "", null);
        jd_JSON.put("", new Object(), null);
        jd_JSON.put("", true, null);
        jd_JSON.put("", 0D, null);
        jd_JSON.put("", 0, null);
        jd_JSON.put("", 0L, null);
        jd_JSON.put("", new java.lang.Short("0"), null);
        jd_JSON.put("", BigDecimal.ZERO, null);
        jd_JSON.put("", BigInteger.ZERO, null);
        jd_JSON.put("", new java.util.Date(), null);
        jd_JSON.put("", new Byte("d"), null);
        JSONObject.d副本();
        jd_JSON.toString(null);
        jd_JSON.toString();
        jd_JSON.keys();
//        jd_JSON.p存在循环引用();
        //
        org.hzs.json.JSONArray jd_ArrayJSON = null;
        jd_ArrayJSON = new org.hzs.json.JSONArray();
        jd_ArrayJSON.set(null, null);
//        jd_ArrayJSON.length();
        jd_ArrayJSON.clear();
        jd_ArrayJSON.removeall();
        jd_ArrayJSON.get(0);
//        jd_ArrayJSON.getDate(0, null);
        jd_ArrayJSON.getBigDecimal(0, null);
        jd_ArrayJSON.getBigInteger(0, null);
        jd_ArrayJSON.getBoolean(0, null);
        jd_ArrayJSON.getDouble(0, null);
        jd_ArrayJSON.getInt(0);
        jd_ArrayJSON.getJSONArray(0, null);
        jd_ArrayJSON.getJSONObject(0, null);
        jd_ArrayJSON.getLong(0);
        jd_ArrayJSON.getString(0);
        jd_ArrayJSON.put(new Object());
        jd_ArrayJSON.put(true);
        jd_ArrayJSON.put(0D);
        jd_ArrayJSON.put(0);
        jd_ArrayJSON.put(0L);
        jd_ArrayJSON.put(new java.util.Date());
        jd_ArrayJSON.put(BigDecimal.ZERO);
        jd_ArrayJSON.put(BigInteger.ZERO);
        jd_ArrayJSON.put(0, new Object(), null);
        jd_ArrayJSON.put(0, true, null);
        jd_ArrayJSON.put(0, 0D, null);
        jd_ArrayJSON.put(0, 0, null);
        jd_ArrayJSON.put(0, 0L, null);
        jd_ArrayJSON.put(0, new java.util.Date(), null);
        jd_ArrayJSON.put(0, BigDecimal.ZERO, null);
        jd_ArrayJSON.put(0, BigInteger.ZERO, null);
        jd_ArrayJSON.append(jd_ArrayJSON);
        JSONArray.d副本();
        jd_ArrayJSON.toString(null);
        jd_ArrayJSON.toString();
//        jd_ArrayJSON.p存在循环引用();
    }
}
