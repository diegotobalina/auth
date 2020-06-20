package com.spring.auth.logs.infrastructure;

import com.spring.auth.shared.util.StringUtil;
import org.bouncycastle.util.io.TeeOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import java.io.*;
import java.util.*;

@Component
public class HttpLoggingFilter implements Filter {

  private static final Logger log = LoggerFactory.getLogger(HttpLoggingFilter.class);

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  private boolean isLoggableUri(final HttpServletRequest httpServletRequest) {
    return httpServletRequest.getRequestURL().toString().contains("/api/")
        && !httpServletRequest.getMethod().equals(HttpMethod.OPTIONS);
  }

  private List<String> getHeaders(HttpServletRequest httpServletRequest) {
    Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
    List<String> headers = new ArrayList<>();
    if (headerNames != null) {
      while (headerNames.hasMoreElements()) {
        String headerName = headerNames.nextElement();
        String headerValue = httpServletRequest.getHeader(headerName);
        headers.add(String.format("%s : %s", headerName, headerValue));
      }
    }
    return headers;
  }

  private List<String> getHeaders(HttpServletResponse httpServletResponse) {
    Collection<String> headerNames = httpServletResponse.getHeaderNames();
    List<String> headers = new ArrayList<>();
    for (String headerName : headerNames) {
      String headerValue = httpServletResponse.getHeader(headerName);
      headers.add(String.format("%s : %s", headerName, headerValue));
    }
    return headers;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    try {
      HttpServletRequest httpServletRequest = (HttpServletRequest) request;
      HttpServletResponse httpServletResponse = (HttpServletResponse) response;
      Map<String, String> requestMap = this.getTypesafeRequestMap(httpServletRequest);
      BufferedRequestWrapper bufferedRequest = new BufferedRequestWrapper(httpServletRequest);
      BufferedResponseWrapper bufferedResponse = new BufferedResponseWrapper(httpServletResponse);
      long requestMillisStart = System.currentTimeMillis();
      if (isLoggableUri(httpServletRequest)) {

        log.info("Request method: {}", StringUtil.removeRowJumps(httpServletRequest.getMethod()));
        log.info("Request url: {}", StringUtil.removeRowJumps(httpServletRequest.getRequestURI()));
        log.info("Request params: {}", StringUtil.removeRowJumps(requestMap.toString()));
        String requestBody = bufferedRequest.getRequestBody();
        if (requestBody != null && !requestBody.isEmpty())
          log.info("Request body: {}", StringUtil.removeRowJumps(requestBody));
        log.info(
            "Request headers: {}",
            StringUtil.removeRowJumps(getHeaders(httpServletRequest).toString()));
      }
      chain.doFilter(bufferedRequest, bufferedResponse);
      long responseMillisStart = System.currentTimeMillis();
      if (isLoggableUri(httpServletRequest)) {
        log.info("Response Code: {}", httpServletResponse.getStatus());
        log.info(
            "Response Headers: {}",
            StringUtil.removeRowJumps(getHeaders(httpServletResponse).toString()));
        String responseBody = bufferedResponse.getContent();
        if (responseBody != null && !responseBody.isEmpty())
          log.info("Response Body Text: {}", StringUtil.removeRowJumps(responseBody));
        log.info("Total Request Time: {} ms", responseMillisStart - requestMillisStart);
      }
    } catch (Throwable a) {
      log.error(a.getMessage());
    }
  }

  private Map<String, String> getTypesafeRequestMap(HttpServletRequest request) {
    Map<String, String> typesafeRequestMap = new HashMap<String, String>();
    Enumeration<?> requestParamNames = request.getParameterNames();
    while (requestParamNames.hasMoreElements()) {
      String requestParamName = (String) requestParamNames.nextElement();
      String requestParamValue;
      if (requestParamName.equalsIgnoreCase("password")) {
        requestParamValue = "********";
      } else {
        requestParamValue = request.getParameter(requestParamName);
      }
      typesafeRequestMap.put(requestParamName, requestParamValue);
    }
    return typesafeRequestMap;
  }

  @Override
  public void destroy() {}

  private static final class BufferedRequestWrapper extends HttpServletRequestWrapper {

    private ByteArrayInputStream bais = null;
    private ByteArrayOutputStream baos = null;
    private BufferedServletInputStream bsis = null;
    private byte[] buffer = null;

    public BufferedRequestWrapper(HttpServletRequest req) throws IOException {
      super(req);
      // Read InputStream and store its content in a buffer.
      InputStream is = req.getInputStream();
      this.baos = new ByteArrayOutputStream();
      byte buf[] = new byte[1024];
      int read;
      while ((read = is.read(buf)) > 0) {
        this.baos.write(buf, 0, read);
      }
      this.buffer = this.baos.toByteArray();
    }

    @Override
    public ServletInputStream getInputStream() {
      this.bais = new ByteArrayInputStream(this.buffer);
      this.bsis = new BufferedServletInputStream(this.bais);
      return this.bsis;
    }

    String getRequestBody() throws IOException {
      BufferedReader reader = new BufferedReader(new InputStreamReader(this.getInputStream()));
      String line = null;
      StringBuilder inputBuffer = new StringBuilder();
      do {
        line = reader.readLine();
        if (null != line) {
          inputBuffer.append(line.trim());
        }
      } while (line != null);
      reader.close();
      return inputBuffer.toString().trim();
    }
  }

  private static final class BufferedServletInputStream extends ServletInputStream {

    private ByteArrayInputStream bais;

    public BufferedServletInputStream(ByteArrayInputStream bais) {
      this.bais = bais;
    }

    @Override
    public int available() {
      return this.bais.available();
    }

    @Override
    public int read() {
      return this.bais.read();
    }

    @Override
    public int read(byte[] buf, int off, int len) {
      return this.bais.read(buf, off, len);
    }

    @Override
    public boolean isFinished() {
      return false;
    }

    @Override
    public boolean isReady() {
      return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {}
  }

  public class TeeServletOutputStream extends ServletOutputStream {

    private final TeeOutputStream targetStream;

    public TeeServletOutputStream(OutputStream one, OutputStream two) {
      targetStream = new TeeOutputStream(one, two);
    }

    @Override
    public void write(int arg0) throws IOException {
      this.targetStream.write(arg0);
    }

    public void flush() throws IOException {
      super.flush();
      this.targetStream.flush();
    }

    public void close() throws IOException {
      super.close();
      this.targetStream.close();
    }

    @Override
    public boolean isReady() {
      return false;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {}
  }

  public class BufferedResponseWrapper implements HttpServletResponse {

    HttpServletResponse original;
    TeeServletOutputStream tee;
    ByteArrayOutputStream bos;

    public BufferedResponseWrapper(HttpServletResponse response) {
      original = response;
    }

    public String getContent() {
      return bos.toString();
    }

    public PrintWriter getWriter() throws IOException {
      return original.getWriter();
    }

    public ServletOutputStream getOutputStream() throws IOException {
      if (tee == null) {
        bos = new ByteArrayOutputStream();
        tee = new TeeServletOutputStream(original.getOutputStream(), bos);
      }
      return tee;
    }

    @Override
    public String getCharacterEncoding() {
      return original.getCharacterEncoding();
    }

    @Override
    public void setCharacterEncoding(String charset) {
      original.setCharacterEncoding(charset);
    }

    @Override
    public String getContentType() {
      return original.getContentType();
    }

    @Override
    public void setContentType(String type) {
      original.setContentType(type);
    }

    @Override
    public void setContentLength(int len) {
      original.setContentLength(len);
    }

    @Override
    public void setContentLengthLong(long l) {
      original.setContentLengthLong(l);
    }

    @Override
    public int getBufferSize() {
      return original.getBufferSize();
    }

    @Override
    public void setBufferSize(int size) {
      original.setBufferSize(size);
    }

    @Override
    public void flushBuffer() throws IOException {
      tee.flush();
    }

    @Override
    public void resetBuffer() {
      original.resetBuffer();
    }

    @Override
    public boolean isCommitted() {
      return original.isCommitted();
    }

    @Override
    public void reset() {
      original.reset();
    }

    @Override
    public Locale getLocale() {
      return original.getLocale();
    }

    @Override
    public void setLocale(Locale loc) {
      original.setLocale(loc);
    }

    @Override
    public void addCookie(Cookie cookie) {
      original.addCookie(cookie);
    }

    @Override
    public boolean containsHeader(String name) {
      return original.containsHeader(name);
    }

    @Override
    public String encodeURL(String url) {
      return original.encodeURL(url);
    }

    @Override
    public String encodeRedirectURL(String url) {
      return original.encodeRedirectURL(url);
    }

    @SuppressWarnings("deprecation")
    @Override
    public String encodeUrl(String url) {
      return original.encodeUrl(url);
    }

    @SuppressWarnings("deprecation")
    @Override
    public String encodeRedirectUrl(String url) {
      return original.encodeRedirectUrl(url);
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
      original.sendError(sc, msg);
    }

    @Override
    public void sendError(int sc) throws IOException {
      original.sendError(sc);
    }

    @Override
    public void sendRedirect(String location) throws IOException {
      original.sendRedirect(location);
    }

    @Override
    public void setDateHeader(String name, long date) {
      original.setDateHeader(name, date);
    }

    @Override
    public void addDateHeader(String name, long date) {
      original.addDateHeader(name, date);
    }

    @Override
    public void setHeader(String name, String value) {
      original.setHeader(name, value);
    }

    @Override
    public void addHeader(String name, String value) {
      original.addHeader(name, value);
    }

    @Override
    public void setIntHeader(String name, int value) {
      original.setIntHeader(name, value);
    }

    @Override
    public void addIntHeader(String name, int value) {
      original.addIntHeader(name, value);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setStatus(int sc, String sm) {
      original.setStatus(sc, sm);
    }

    @Override
    public String getHeader(String arg0) {
      return original.getHeader(arg0);
    }

    @Override
    public Collection<String> getHeaderNames() {
      return original.getHeaderNames();
    }

    @Override
    public Collection<String> getHeaders(String arg0) {
      return original.getHeaders(arg0);
    }

    @Override
    public int getStatus() {
      return original.getStatus();
    }

    @Override
    public void setStatus(int sc) {
      original.setStatus(sc);
    }
  }
}
