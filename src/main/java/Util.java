/**
 * @author pxy
 * @date 2023/7/8 10:46:09
 * @description
 */
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Util {
    /**
     * 以post方式调用对方接口方法
     *
     * @param pathUrl
     */
    public static void Post(String pathUrl, String data) {
        OutputStreamWriter out = null;
        BufferedReader br = null;
        String result = "";
        try {
            URL url = new URL(pathUrl);

            // 打开和url之间的连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // 设定请求的方法为"POST"，默认是GET
            // post与get的不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。
            conn.setRequestMethod("POST");

            // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false;
            conn.setDoOutput(true);
            // 设置是否从httpUrlConnection读入，默认情况下是true;
            conn.setDoInput(true);

            // Post请求不能使用缓存
            conn.setUseCaches(false);

            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive"); // 维持长链接
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            // 身份认证信息
            conn.setRequestProperty("EngineCode", "");
            conn.setRequestProperty("EngineSecret", "");

            conn.connect();

            /**
             * 调用第三方http接口
             */
            // 获取URLConnection对象对应的输出流
            // 此处getOutputStream会隐含的进行connect(即：如同调用上面的connect()方法，所以在开发中不调用上述的connect()也可以)。
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数即数据
            out.write(data);
            // flush输出流的缓冲
            out.flush();

            /**
             * 下面的代码相当于，获取调用第三方http接口后返回的结果
             */
            // 获取URLConnection对象对应的输入流
            InputStream is = conn.getInputStream();
            StringBuilder json = new StringBuilder();
            // 构造一个字符流缓存
            br = new BufferedReader(new InputStreamReader(is));
            String str = "";
            while ((str = br.readLine()) != null) {
                result += str+ "\r\n";;
                json.append(str +  "\r\n");
            }
            while((str = br.readLine())!=null){

            }
            System.out.println(result);
            System.out.println(json);
            // 关闭流
            is.close();
            // 断开连接，disconnect是在底层tcp socket链接空闲时才切断，如果正在被其他线程使用就不切断。
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void getInternetResdoPost(String pathUrl, String data) {
        // 定义输出流 字节输出流，可以用来写转换之后的字节到文件中
        OutputStreamWriter out = null;
        // 定义输入流
        BufferedReader br = null;
        String result = "";
        FileOutputStream outfile = null;
        try {
            // 统一资源
            URL url = new URL(pathUrl);

            // 打开和url之间的连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // 设定请求的方法为"POST"，默认是GET
            // post与get的不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。
            conn.setRequestMethod("POST");

            // 设置30秒连接超时
            conn.setConnectTimeout(30000);
            // 设置30秒读取超时
            conn.setReadTimeout(30000);

            // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false;
            conn.setDoOutput(true);
            // 设置是否从httpUrlConnection读入，默认情况下是true;
            conn.setDoInput(true);

            // Post请求不能使用缓存
            conn.setUseCaches(false);

            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive"); // 维持长链接
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            // 必须要有的身份认证参数 在组织 界面 钉钉右上角 我的头像点击 后 系统管理》系统集成中有这两个字段
            conn.setRequestProperty("EngineCode", "fs1tkeu2ap4kb4hp");
            conn.setRequestProperty("EngineSecret", "Ed0JouQbydoe1seTTuETeqK7VbJqTX2jEWOUCnnfgZc0kzLg18nZFw==");

            // 连接，从上述url.openConnection()至此的配置必须要在connect之前完成，
            conn.connect();

            /**
             * 下面的三句代码，就是调用第三方http接口
             */
            // 获取URLConnection对象对应的输出流
            // 此处getOutputStream会隐含的进行connect(即：如同调用上面的connect()方法，所以在开发中不调用上述的connect()也可以)。
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数即数据
            out.write(data);
            // flush输出流的缓冲
            out.flush();

            /**
             * 下面的代码相当于，获取调用第三方http接口后返回的结果
             */
            // 获取URLConnection对象对应的输入流
            InputStream is = conn.getInputStream();

            // 获取文件类型 例如：返回的格式是：attachment;filename=1.png
            String headerField = conn.getHeaderField("Content-Disposition");
            headerField = new String(headerField.getBytes("GB2312"));
            headerField = new String(headerField.getBytes("utf-8"));
            // 截取得到正确的文件名
            String filename = substringGetType(headerField);
            byte[] NEWdata = getByteData(is);

            // 建立存储的目录、保存的文件名
            File file = new File("C:\\Users\\authine\\Desktop\\" + "文件下载");
            if (!file.exists()) {
                file.mkdirs();
            }
            // 修改文件名
            File res = new File(file + File.separator + filename);
            // 写入输出流
            outfile = new FileOutputStream(res);
            outfile.write(NEWdata);
            System.out.println("下载成功!");
            // 关闭流
            is.close();
            // 断开连接，disconnect是在底层tcp socket链接空闲时才切断，如果正在被其他线程使用就不切断。
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("下载失败!");
        } finally {
            try {
                if (out != null) {
                    out.close();
                    outfile.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @Title: getByteData
     * @Description: 从输入流中获取字节数组
     */
    private static byte[] getByteData(InputStream in) throws IOException {
        byte[] b = new byte[1024];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len = 0;
        while ((len = in.read(b)) != -1) {
            bos.write(b, 0, len);
        }
        if (null != bos) {
            bos.close();
        }
        return bos.toByteArray();
    }

    /**
     *
     * @Title: substringGetPng
     * @Description: 截取字符串得到正确的文件类型
     *
     */
    public static String substringGetType(String str) {
        // 获得第=号的位置
        int index = str.indexOf("=");
        String result = str.substring(index + 1);
        // 输出结果
        System.out.println("下载文件名:" + result);
        return result;
    }
}
