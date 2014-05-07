package org.hzs.安全;

import java.math.BigInteger;
import java.util.Random;

public class ECC {

   private E e;//椭圆曲线
   private Pare pare;//椭圆上的已知点
   private long privatekey;//私钥--随机
   private Pare publickey;//公钥

   public ECC() {
      Random rand = new Random();
      this.e = new E(BigInteger.probablePrime(31, rand).longValue(), rand.nextLong(), rand.nextLong());
      this.privatekey = rand.nextLong();//私钥--随机
      this.pare = new Pare(rand.nextLong(), rand.nextLong());
      this.publickey = this.pare.multiply(privatekey);//new Pare();
   }

   public org.hzs.json.JSONObject i公钥_JSON(final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
      org.hzs.json.JSONObject ji_JSON = null;
      try {
         ji_JSON = new org.hzs.json.JSONObject();
         ji_JSON.put("x", this.publickey.x, ci_error);
         ji_JSON.put("y", this.publickey.y, ci_error);
         return ji_JSON;
      } finally {
      }
   }

   public org.hzs.json.JSONArray i用公钥加密_byteArray(final byte[] ci待加密數據_byteArray, org.hzs.json.JSONObject ci公钥_JSON, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
      Pare ji公钥 = null;
      org.hzs.json.JSONArray ji_ArrayJSON = null;
      try {
         ji_ArrayJSON = new org.hzs.json.JSONArray();
         ji公钥 = new Pare(ci公钥_JSON.getLong("x", ci_error), ci公钥_JSON.getLong("y", ci_error));
         Pare[] words = Str2Pares(ci待加密數據_byteArray);
         for (int i = 0; i < words.length; i++) {
            words[i] = words[i].add(ji公钥);
            ji_ArrayJSON.put(words[i].toJSON(ci_error));
         }
         return ji_ArrayJSON;
      } finally {
         ji公钥 = null;
         ji_ArrayJSON = null;
      }
   }

   public byte[] i用私钥解密_byteArray(org.hzs.json.JSONArray ci_ArrayJSON, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
      org.hzs.json.JSONObject ji_JSON = null;
      Pare ji公钥 = null;
      ji公钥 = new Pare();
      int ji_i = ci_ArrayJSON.size();
      for (int ji1_i = 0; ji1_i < ji_i; ji1_i++) {
         ji_JSON = ci_ArrayJSON.getJSONObject(ji1_i, ci_error);
         ji公钥.x = ji_JSON.getLong("x", ci_error);
         ji公钥.y = ji_JSON.getLong("y", ci_error);

      }
      return null;
   }

   /**
    * 将字符串转换为 值对
    */
   private Pare[] Str2Pares(byte[] string) {
      Pare[] jipare_PareArray;
      long jix_l, jiy_l;
      if (string.length % 8 != 0) {
         jipare_PareArray = new Pare[string.length / 8 + 1];
      } else {
         jipare_PareArray = new Pare[string.length / 8];
      }
      int ji_i = 0, ji1_i;;
      for (ji_i = 0; ji_i < string.length / 8; ji_i++) {
         ji1_i = ji_i * 8;
         jix_l = string[ji1_i];
         jix_l = jix_l << 8 + string[ji1_i + 1];
         jix_l = jix_l << 8 + string[ji1_i + 2];
         jix_l = jix_l << 8 + string[ji1_i + 3];
         jiy_l = string[ji1_i + 4];
         jiy_l = jiy_l << 8 + string[ji1_i + 5];
         jiy_l = jiy_l << 8 + string[ji1_i + 6];
         jiy_l = jiy_l << 8 + string[ji1_i + 7];
         jipare_PareArray[ji_i] = new Pare(jix_l, jiy_l);
      }
      if (string.length % 8 != 0) {
         ji1_i = ji_i * 8;
         jix_l = string[ji1_i];
         if (ji1_i + 1 >= string.length) {
            jix_l = jix_l << 8;
         } else {
            jix_l = jix_l << 8 + string[ji1_i + 1];
         }
         if (ji1_i + 2 >= string.length) {
            jix_l = jix_l << 8;
         } else {
            jix_l = jix_l << 8 + string[ji1_i + 2];
         }
         if (ji1_i + 3 >= string.length) {
            jix_l = jix_l << 8;
         } else {
            jix_l = jix_l << 8 + string[ji1_i + 3];
         }
         jiy_l = ji1_i + 4 >= string.length ? 0 : string[ji1_i + 4];
         if (ji1_i + 5 >= string.length) {
            jiy_l = jiy_l << 8;
         } else {
            jiy_l = jiy_l << 8 + string[ji1_i + 5];
         }
         if (ji1_i + 6 >= string.length) {
            jiy_l = jiy_l << 8;
         } else {
            jiy_l = jiy_l << 8 + string[ji1_i + 6];
         }
         jiy_l = jiy_l << 8;
         jipare_PareArray[ji_i] = new Pare(jix_l, jiy_l);
      }
      return jipare_PareArray;
   }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

   class E {// 表示椭圆曲线方程  

      Long p;//模p的椭圆群  
      Long a;
      Long b;

      public E(long p, long a, long b) {
         super();
         this.p = p;
         this.a = a;
         this.b = b;
      }
   }

   class Message {//传送消息的最小单元  

      Pare pa;
      Pare pb;

      public Message(Pare pa, Pare pb) {
         this.pa = pa;
         this.pb = pb;
      }

      @Override
      public String toString() {
         return this.pa.toString() + " " + this.pb.toString();
      }
   }

   class Pare {//椭圆曲线上的点(x,y)  

      long x, y;

      public Pare() {
      }

      public Pare(long x, long y) {
         this.x = x;
         this.y = y;
      }

      /**
       * 加法
       */
      public Pare add(Pare pare) {
         if (this.x == Long.MAX_VALUE) {//为无穷大时O+P=P  
            return pare;
         }
         Pare res = new Pare();
         if (this.y == pare.y && this.x == pare.x) {//相等时  
            long d = moddivision(3 * this.x * this.x + e.a, e.p, 2 * this.y);

            res.x = d * d - 2 * this.x;
            res.x = mod(res.x, e.p);

            res.y = d * (this.x - res.x) - this.y;
            res.y = mod(res.y, e.p);
         } else if (pare.x - this.x != 0) {
            long d = moddivision(pare.y - this.y, e.p, pare.x - this.x);
            res.x = d * d - this.x - pare.x;
            res.x = mod(res.x, e.p);

            res.y = d * (this.x - res.x) - this.y;
            res.y = mod(res.y, e.p);
         } else {//P Q互逆,返回无穷大  
            res.x = Long.MAX_VALUE;
            res.y = Long.MAX_VALUE;
         }

         return res;
      }

      /**
       * 减法
       */
      public Pare less(Pare p) {
         p.y *= -1;
         return add(p);
      }

      /**
       * 乘法
       */
      public Pare multiply(long num) {
         Pare p = new Pare(this.x, this.y);
         for (long i = 1; i < num; i++) {
            p = p.add(this);
         }
         return p;
      }

      /**
       * 求余,解决负号问题
       */
      public long mod(long a, long b) {
         a = a % b;
         while (a < 0) {
            a += b;
         }
         return a;
      }

      /**
       * 求余取商(a mod b)/c
       */
      /*public long moddivision(long a, long b, long c) { 
       a = mod(a,b); 
       while(a%c != 0) { 
       a += b; 
       } 
       a = a/c; 
       return a; 
       }*/
      public long moddivision(long a, long b, long c) {
         a = mod(a, b);
         c = mod(c, b);
         a = a * exgcd(c, b);
         return mod(a, b);
      }

      /**
       * 扩展的欧几里得算法求逆元，如果有返回值，没有返回-1
       */
      public long exgcd(long a, long b) {
         long x1 = 1, x2 = 0, x3 = b, y1 = 0, y2 = 1, y3 = a;
         while (true) {
            if (y3 == 0) {
               return -1;
            }
            if (y3 == 1) {
               return y2 > 0 ? y2 : y2 + b;
            }
            long t1, t2, t3;
            long q = x3 / y3;
            t1 = x1 - q * y1;
            t2 = x2 - q * y2;
            t3 = x3 - q * y3;
            x1 = y1;
            x2 = y2;
            x3 = y3;
            y1 = t1;
            y2 = t2;
            y3 = t3;
         }
      }

      public org.hzs.json.JSONObject toJSON(final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
         org.hzs.json.JSONObject ji_JSON = null;
         try {
            ji_JSON = new org.hzs.json.JSONObject();
            ji_JSON.put("x", this.x, ci_error);
            ji_JSON.put("y", this.y, ci_error);
            return ji_JSON;
         } finally {
            ji_JSON = null;
         }
      }
   }
}
