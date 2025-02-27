package controller;

import org.json.JSONObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Controller {
    void process(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
