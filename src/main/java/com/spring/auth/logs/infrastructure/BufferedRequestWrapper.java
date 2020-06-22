package com.spring.auth.logs.infrastructure;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

public class BufferedRequestWrapper extends HttpServletRequestWrapper implements ServletRequest {

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
