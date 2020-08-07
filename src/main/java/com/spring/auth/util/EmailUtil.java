package com.spring.auth.util;

import lombok.extern.slf4j.Slf4j;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

@Slf4j
public abstract class EmailUtil {
  public static boolean doEmailExists(String email) {
    String hostName = email.split("@")[1];
    Hashtable env = new Hashtable();
    env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
    try {
      DirContext ictx = new InitialDirContext(env);
      Attributes attrs = ictx.getAttributes(hostName, new String[] {"MX"});
      Attribute attr = attrs.get("MX");
      if ((attr == null) || (attr.size() == 0)) {
        attrs = ictx.getAttributes(hostName, new String[] {"A"});
        attr = attrs.get("A");
        if (attr == null) return false;
      }
      return true;
    } catch (NamingException ex) {
      log.info(ex.getMessage());
      return false;
    }
  }
}
