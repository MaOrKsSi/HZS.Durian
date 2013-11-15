package javafxswingapplication5;

public class JavaFXSwingApplication5 extends org.hzs.web_client.Application {

    public static void main(String[] args) {
        org.hzs.json.JSONArray ji_ArrayJSON = null;
        org.hzs.json.JSONObject ji_JSON = null;
        try {
            ji_ArrayJSON = org.hzs.json.JSONArray.d副本();
            ji_JSON = org.hzs.json.JSONObject.d副本();
            ji_JSON.put("服务器IP_s", "192.168.0.113");
            ji_JSON.put("服务器端口_i", 8080);
            ji_ArrayJSON.put(ji_JSON);
            ji_JSON = org.hzs.json.JSONObject.d副本();
            ji_JSON.put("端口偏移_i", 1);
            ji_JSON.put("业务模块_s", "示例");
            ji_JSON.put("标题_s", "示例");
            ji_JSON.put("服务器列表_ArrayJSON", ji_ArrayJSON);//集群分配节点的服务器列表
            ji_JSON.put("本地服务端口_i", 8090);
            ji_JSON.put("图标用图片URL", JavaFXSwingApplication5.class.getResource("/javafxswingapplication5/large-loading.gif"));
            g开始(ji_JSON);
        } finally {
            ji_ArrayJSON = null;
            ji_JSON = null;
        }
    }
}
