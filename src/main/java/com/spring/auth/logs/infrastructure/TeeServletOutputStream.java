package com.spring.auth.logs.infrastructure;

import org.bouncycastle.util.io.TeeOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.IOException;
import java.io.OutputStream;

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
