package main.java.sample.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class WebUtils {

    private Map<String, String> headers = new HashMap<String, String>();

    public WebUtils(HttpServletRequest request) {

        getHeadersInfo(request);
    }

    private void getHeadersInfo(HttpServletRequest request) {

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            headers.put(key, value);
        }
    }

    public String getHeader(String hdr) {

        return headers.get(hdr);
    }

}
