package ru.malichenko.sobSpring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class SimpleHttpClient {
    private static class Response {
        private String httpVersion;
        private int statusCode;
        private int contentLength;
        private String body;

        public Response(InputStream input, boolean debug) throws IOException {
            String line = null;

            StringBuilder sb = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
                line = reader.readLine();
                if (debug) {
                    System.out.println(line);
                }
                parseStatusLine(line);
                do {
                    line = reader.readLine();
                    parseHeader(line);
                    if (debug) {
                        System.out.println(line);
                    }
                    if (line.isEmpty()) {
                        break;
                    }
                } while (line != null);
            }
        }

        private void parseStatusLine(String line) {
            String[] tokens = line.split("\\s+");
            httpVersion = tokens[0];
            statusCode = Integer.parseInt(tokens[1]);
        }

        private void parseHeader(String line) {
            if (line.startsWith("Content-Length")) {
                contentLength = Integer.parseInt(line.split("\\s+")[1]);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //sendJsonRequest("http://localhost/demo", 8189, HttpMethod.POST.value());
        //sendBobRequest("http://localhost/demo", 8189, HttpMethod.POST.value());
    }

    public static void sendRequest(String host, int port, String method) throws IOException {
        try (Socket socket = new Socket("localhost", 8189)) {
            StringBuilder output = new StringBuilder();
            output.append("POST /demo/hello HTTP/1.1").append("\r\n");
            output.append("Host: ").append("localhost:8189").append("\r\n");
            output.append("Accept: ").append("text/plain;charset=UTF-8").append("\r\n");
            output.append("Connection: ").append("close").append("\r\n");
            output.append("Content-Type: ").append("text/plain;charset=UTF-8").append("\r\n");
            output.append("\r\n");
            socket.getOutputStream().write(output.toString().getBytes("UTF-8"));
            socket.getOutputStream().flush();

            Response response = new Response(socket.getInputStream(), true);
        }
    }

    public static void sendBobRequest(String host, int port, String method) throws IOException {
        try (Socket socket = new Socket("localhost", 8189)) {
            StringBuilder output = new StringBuilder();
            output.append("GET /demo/get_bob HTTP/1.1").append("\r\n");
            output.append("Host: ").append("localhost:8189").append("\r\n");
            output.append("Accept: ").append("application/json").append("\r\n");
            output.append("Connection: ").append("close").append("\r\n");
            output.append("Content-Type: ").append("text/plain;charset=UTF-8").append("\r\n");
            output.append("\r\n");
            socket.getOutputStream().write(output.toString().getBytes("UTF-8"));
            socket.getOutputStream().flush();

            Response response = new Response(socket.getInputStream(), true);
        }
    }

    public static void sendJsonRequest(String host, int port, String method) throws IOException {
        String body = "{ \"name\": \"John\" }";

        try (Socket socket = new Socket("localhost", 8189)) {
            StringBuilder output = new StringBuilder();
            output.append("POST /demo/json HTTP/1.1").append("\r\n");
            output.append("Host: ").append("localhost:8189").append("\r\n");
            output.append("Accept: ").append("text/plain;charset=UTF-8").append("\r\n");
            output.append("Connection: ").append("close").append("\r\n");
            output.append("Content-Type: ").append("application/json").append("\r\n");
            output.append("Content-Length: ").append(body.length()).append("\r\n");
            output.append("\r\n");
            output.append(body);
            output.append("\r\n");
            socket.getOutputStream().write(output.toString().getBytes("UTF-8"));
            socket.getOutputStream().flush();

            Response response = new Response(socket.getInputStream(), true);
        }
    }
}
