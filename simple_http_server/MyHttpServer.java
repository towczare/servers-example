import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class MyHttpServer {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/test", new MyHandler());
        server.createContext("/joke", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            Map<String, String> paramMap = queryToMap(t.getRequestURI().getQuery());
            for (Map.Entry<String,String> e : paramMap.entrySet()) {
                System.out.print(e.getKey() + " : " + e.getValue());
            }
            String response = JokeProvider.getRandomJoke();
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        public Map<String, String> queryToMap(String query) {
            Map<String, String> result = new HashMap<>();
            for (String param : query.split("&")) {
                String[] entry = param.split("=");
                if (entry.length > 1) {
                    result.put(entry[0], entry[1]);
                }else{
                    result.put(entry[0], "");
                }
            }
            return result;
        }
    }


}

