package com.atguigu.scw.ui.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016101700705469";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCZYpmgHfN2SlxWHYbKghC3QUrOjUaBDedkYtXhGVkr2hwPWiLxFXLknbhrlU0mKsNud0cd5fjwWw2hDaGVgEjrzEsL5rgIwVDbV5OFDHWanbh26KjrRRlN3Fj7NpuqoVrYf3u0YOe8A0bpxqv57d3UQB34PBg/mIEPxdj/697L6lIdVJFjUnF+B5Ov2LmOZUG7cw9Zf/bYz4dAycq6URFTIO6fTFxAfoxhzxGDqwUfo0MGknWyVrVhq54xQtCNc9oCtFqJ5bFbaFKlDC5vevciUGbVTPtyZ53Es9FmhtC/YnJeRluaxn7FNmWi/4ZLeZjEnkGzGVmYYqA+8LFZNph9AgMBAAECggEAY3dYTkf98JCqLkH5fes4cc4Kd8WPmfLtQeEB93YbqF9JYP8/t30EQ5Kv3Dw2inCiyUjaOl4+v4ccYHtZdHx7TUXuyKn2X98U0xiIlngM75Yl4B5iJCdsEFy66zVvXwhBxDFvg/pvbDHn04O9VFbXSmPFy4sU4nDACP1xCgzoLu0c0Rar5gwj9d9LXy4IxCzH5FrN4tdweUVK4fxcUGo7jPme1Y5J5EpWGU7JDknM0CPlL+m7xNVA4KNQmcK87uGTOj3cosAEVRIzMK4G/qegY+G2ScWiZDn8Tyj2KndhN/FWk2sHvVNErbu/fvRUiHpO9Fb5Z20T7Jg3jLU+7nQSdQKBgQD/HAMIioI9sctAhsFJf+9FbEDevJ2h1QGgrlHn0RS47R3Ulsvc4Dd0z4ZOT+hei6sPDoOejCUL84lkn7HAWLO/y6Quo5FJEJ0kiQPXraRaIfBmvk4QQ2/BLO2xhKXtp8W6UbNGNCVrmN5yKzw2Zxy4o1yXp/dfkzCBeeNITeDkXwKBgQCZ662z7IQ4PkGVAJ22MeH+4XJUGCmgWUBhLqqqA0jpMC79xm10PB3BW1S6tuJuVE6fZXLE3rYA7kMVIxKJtjnk9HPvrvTJgfYVzHbveivFCB7uf29FblKqUYSp0Nikf5JFUVZNxjgCj1cLziS3yhtH+V5BDfR1rRrR3CX7qYjQowKBgBsFvpSDB3S9hogZPhQpBxFVfDGbzR69T2TT0j05+nX0N/qy1nlZDsbCzLky4axSIxZ/NRXwRmRTsbQE7nEvdCrWyVdfYB5bMioSkWOhMw2RFsPrWJ2EZywRNERfhqv734fy64h+MZCKKtXxGnmwxmwQcdhmpF9tWe/9F0Qra60FAoGANS1Ze9nw7EWF/uaRlkqxm49kIm2epAlx1qF60PYFkJrXAVI2qnvCg/T71u4sWJXD/Anelumi07t9baYKV42fftx92YcGhz/hNzLGvIHm8VGW7isU1FYDNViojsrarips7InnWUNsQmYnWqrDwMOQnVMoHy2lEarWoz592O5/mCUCgYBnZEGVl5k2awhhc+VL0/H+bkiG//59kbxq77BLQkTKXW459VnAMdL3FJtCVr/p73URpk4b/OLJsg4huo61qIZEz3wl3NnGIj6Kyf29mQgEUhV5Lu7XZETwzrwl6FAKeuWxMtSf5qDfKYTHYuzJin9p80vu7/CZWDnRkJzyDKA8ag==";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoqspR9J+rAU7A0DD5qCh5UJLzTm+oQfO8pUxteR0RYnmB82DBRohHRcCpSvVGLgXi5jPMMgipWLFRvRQcAPwmiQNzV6b6p5gpdaDSUGHVo/VmT2KrkxkW9TGTtbwNtJP7sUxSptSYqbvQswihEpyC/QefzMke6/j7bjMplynz318xttrzXD2dxPDW4ohXdWzz3kvKUpou5l32MY5QMnbO368nKJqydW+ChWQ57qT9KzYGo8nXBya57SCg4Ze//GGFqP/2dScEa2F4tnXYNNyRJ0GOoVK773S9XYAUVRCixTkZOMzyLvGL3umpbBdSgSdneXRqm1aG22j3er+E5NOtwIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://qe27519715.qicp.vip/order/notify_url";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://qe27519715.qicp.vip/order/return_url";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 日志保存路径
	public static String log_path = "G:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

