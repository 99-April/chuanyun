/**
 * @author pxy
 * @date 2023/7/8 10:52:47
 * @description
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.*;

public class Exec {
    /**
     * 氚云低代码平台提供的接口：ActionName
     * LoadBizObject ： 查询单条业务数据
     * LoadBizObjects ：批量查询业务数据
     * CreateBizObject ：创建单条业务数据
     * CreateBizObjects ：批量创建业务数据
     * UpdateBusinessData ：更新业务数据
     * RemoveBizObject ：删除业务数据
     * 上传文件
     * 下载文件
     * 其余接口需要自定义
     */
    /* 查询单条业务数据 */
    public static void SelectOne() {
        // 配置请求参数
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("ActionName", "LoadBizObject"); // 调用的方法名
        paramMap.put("SchemaCode", ""); // 表单编码
        paramMap.put("BizObjectId", ""); // 每行数据的唯一标识id
        String select = JSONObject.valueToString(paramMap);
        Util.Post("https://www.h3yun.com/OpenApi/Invoke", select);
    }

    /* 批量查询业务数据 */
    public static void SelectMore() {
        // 配置请求参数
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("ActionName", "LoadBizObjects");
        paramMap.put("SchemaCode", "");
        // 过滤条件。默认返回前500条数据
        // FromRowNum分页查询，从第几条开始
        // RequireCount查询的总行数
        // ReturnItems返回的字段，不填返回所有
        // SortByCollection排序字段，目前不支持使用，默认置空
        // ToRowNum分页查询，第几条结束
        // Matcher查询条件 F0000001为控件id，特殊的OwnId和DepId需要自定义接口查询到该控件编码对应的编码值
        paramMap.put("Filter",
                "{\"FromRowNum\":0," + "\"RequireCount\": false," + "\"ReturnItems\": [],   "
                        + "\"SortByCollection\": []," + "\"ToRowNum\": 500,   "
                        + "\"Matcher\": { \"Type\": \"And\",   \"Matchers\": [{'F0000001':1}]}}");
        String select = JSONObject.valueToString(paramMap);
        Util.Post("https://www.h3yun.com/OpenApi/Invoke", select);
    }

    /* 创建单条业务数据 */
    public static void CreateBusinessData() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("ActionName", "CreateBizObject");
        paramMap.put("SchemaCode", "");
        // F0000001为控件id,特殊的OwnId和DepId需要自定义接口查询到该控件id对应的id值
        paramMap.put("BizObject", "{\"F0000001\":\"添加数据测试\",\"F0000002\":\"ctes\"}");
        // 为true时创建生效数据，false 为草稿数据 默认为false
        paramMap.put("IsSubmit", "true");
        String CreateStr = JSONObject.valueToString(paramMap);
        // 请求接口
        Util.Post("https://www.h3yun.com/OpenApi/Invoke", CreateStr);
    }

    /* 批量创建业务数据 */
    public static void CreateBusinessDatas() {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("ActionName", "CreateBizObjects");
        paramMap.put("SchemaCode", "");

        List<String> Childerdd = new ArrayList<String>();// BizObject[]对象的 成员
        Childerdd.add("{\"F0000001\":\"ceee002229\",\"F0000002\":\"ctes5\"}");
        Childerdd.add("{\"F0000001\":\"cesd00099222\",\"F0000002\":\"ctes6\"}");
        paramMap.put("BizObjectArray", Childerdd.toArray());// BizObject[]对象的json数组
        paramMap.put("IsSubmit", "true");
        String CreateStr = JSONObject.valueToString(paramMap);
        // 请求接口
        Util.Post("https://www.h3yun.com/OpenApi/Invoke", CreateStr);

    }

    /* 更新业务数据 */
    public static void UpdateBusinessData() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("ActionName", "UpdateBizObject");
        paramMap.put("SchemaCode", "");
        paramMap.put("BizObjectId", "");
        // 即需要修改的数据的控件的键和值
        paramMap.put("BizObject", "{\"F0000001\":\"TS-000322\",\"F0000002\":\"cte1s\"}");
        String UpdateStr = JSONObject.valueToString(paramMap);
        // 请求接口
        Util.Post("https://www.h3yun.com/OpenApi/Invoke", UpdateStr);
    }

    /* 删除业务数据 */
    public static void DeleteBusinessDate() {
        // 添加请求参数
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("ActionName", "RemoveBizObject");
        paramMap.put("SchemaCode", "D0015994821985e8b434394bc0737ffb22a0584");
        paramMap.put("BizObjectId", "46961303-6fcf-486b-9260-dcaacf1b5802");
        String DeleteStr = JSONObject.valueToString(paramMap);
        // 请求接口
        Util.Post("https://www.h3yun.com/OpenApi/Invoke", DeleteStr);
    }


    /**
     * 上传文件
     * @param path 要上传文件的路径
     * @return
     */
    public static String SendPostUplodFile(String path) {
        DataOutputStream out = null;// 定义输出流
        BufferedReader in = null;
        String result = "";
        try {
            // 填入对应的SchemaCode、FilePropertyName、BizObjectId
            URL realUrl = new URL(
                    "https://www.h3yun.com/OpenApi/UploadAttachment?SchemaCode=&FilePropertyName=&BizObjectId=");
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 构建请求头
            String BOUNDARY = "----WebKitFormBoundary07I8UIuBx6LN2KyY";
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("connection", "Keep-Alive");

            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            // 身份信息认证
            conn.setRequestProperty("EngineCode", "");
            conn.setRequestProperty("EngineSecret", "");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();

            // 设置输出流
            out = new DataOutputStream(conn.getOutputStream());

            // 添加参数file
            File file = new File(String.valueOf(path));
            StringBuffer sb = new StringBuffer();
            sb.append("--");
            sb.append(BOUNDARY);
            sb.append("\r\n");
            // 媒体类型上传的类型
            //sb.append("Content-Disposition: form-data; name=\"media\";filename=\"").append(fileName).append(typeName);
            //（Instream）流文件上传的时候要指定filename的值
            sb.append("Content-Disposition: form-data;name=\"file_data\";filename=\"" + file.getName() + "\"");
            sb.append("\r\n");
            sb.append("Content-Type: application/octet-stream");
            sb.append("\r\n");
            sb.append("\r\n");
            // 发送请求参数即数据
            out.write(sb.toString().getBytes());

            FileInputStream in1 = new FileInputStream(file);
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in1.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            out.write("\r\n".getBytes());
            in1.close();

            byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(end_data);
            out.flush();// flush输出流的缓冲
            /**
             * 下面的代码相当于，获取调用第三方http接口后返回的结果
             */
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
    public static void DownUrlPost() {

        Util util = new Util();
        // 添加请求参数
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("attachmentId", "");
        paramMap.put("EngineCode", "");
        String Down = JSONObject.valueToString(paramMap);
        // 请求接口
        util.getInternetResdoPost("https://www.h3yun.com/Api/DownloadBizObjectFile", Down);
    }

    /**
     * 自定义接口，需要调用氚云后端自定义的接口
     */
    public static void CustomInterface()
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("ActionName", ""); // 自定义接口的方法名字
        paramMap.put("AppCode", "");// 应用编码
        paramMap.put("Controller", "");// 自定义接口的类名称
        paramMap.put("param", "");// 自定义请求条件的参数
        String select = JSONObject.valueToString(paramMap);
        Util.Post("https://www.h3yun.com/OpenApi/Invoke", select);

    }
}
