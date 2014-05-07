
import java.io.IOException;
import java.net.UnknownHostException;
import org.hzs.logging.error;

public class 混淆入口 {

   public static void main(String[] args) throws UnknownHostException, IOException, error {
      org.hzs.Mongodb mongodb = new org.hzs.Mongodb(null, 0, null);
      mongodb.g保存文档(null, null, null);
      mongodb.g删除文档(null, null);
      mongodb.i读取文档_byteArray(null, null);
   }
//
//   private static void queryAll() throws UnsupportedEncodingException {
//      System.out.println("查询users的所有数据：");
//      //db游标
//      DBCursor cur = users.find();
//      while (cur.hasNext()) {
//         System.out.println(cur.next());
//      }
//   }
//
//   private static void insert() throws UnsupportedEncodingException {
//      DBObject user = null;
//      try {
//         user = new BasicDBObject();
//         user.put("id", 0);
//         user.put("sex", "男".getBytes("UTF-8"));
//         WriteResult dddd = users.insert(user);
////         users.remove(user);
//         user = new BasicDBObject();
//         user.put("id", 1);
//         user.put("sex", "女".getBytes("UTF-8"));
//         dddd = users.insert(user);
////         users.remove(user);
////         System.out.println(users.save(user).getN());
////         users.remove(user);
////         System.out.println("count: " + users.count());
//      } finally {
//      }
//   }
//
//   private void jg存小文档(final long id, final byte[] ci文档_byteArray) {
//      DBObject user = new BasicDBObject();
//      user.put("id", id);
//      user.put("file", ci文档_byteArray);
//      users.insert(user);
//   }
//
//   private void jg删小文档(final long id) {
//      DBObject user = new BasicDBObject();
//      user.put("id", id);
//      user = users.findOne(user);
//      users.remove(user);
//   }
//
//   private byte[] i取小文档_byteArray(final long id) {
//      DBObject user = new BasicDBObject();
//      user.put("id", id);
//      user = users.findOne(user);
//      if (user == null) {
//         return null;
//      }
//      return (byte[]) user.get("file");
//   }
}
