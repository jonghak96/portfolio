/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.5.58
 * Generated at: 2020-12-03 04:51:27 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.main;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class menu_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSP들은 오직 GET, POST 또는 HEAD 메소드만을 허용합니다. Jasper는 OPTIONS 메소드 또한 허용합니다.");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("    \n");
      out.write("<script type=\"text/javascript\">\n");
      out.write("\tfunction fighterPageMove() {\n");
      out.write("\t\tlocation.href = \"http://localhost:8080/KeyWar/fighter/fighterPage.jsp\";\n");
      out.write("\t}\n");
      out.write("\tfunction boardPageMove() {\n");
      out.write("\t\tlocation.href = \"/KeyWar/board/boardSearch.do\";\n");
      out.write("\t}\n");
      out.write("\tfunction gymInfoPageMove() {\n");
      out.write("\t\tlocation.href = \"http://localhost:8080/KeyWar/gym/gymPage.jsp\";\n");
      out.write("\t}\n");
      out.write("\tfunction freeBoardPageMove() {\n");
      out.write("\t\tlocation.href = \"http://localhost:8080/KeyWar/freeboard/freeboardSearch.do\";\n");
      out.write("\t}\n");
      out.write("</script>\n");
      out.write("\n");
      out.write("<style>\n");
      out.write("\t#menuContainer {\n");
      out.write("\t\twidth: 100%;\n");
      out.write("\t}\n");
      out.write("\t\n");
      out.write("\t#title {\n");
      out.write("\t\tfont-size: 40px;\n");
      out.write("\t\tfont-family: bold;\n");
      out.write("\t}\n");
      out.write("\t\n");
      out.write("#bt {\n");
      out.write("  background-color: grey;\n");
      out.write("  border-radius: 10px;\n");
      out.write("  color: white;\n");
      out.write("  padding: 15px 30px;\n");
      out.write("  text-align: center;\n");
      out.write("  text-decoration: none;\n");
      out.write("  display: inline-block;\n");
      out.write("  font-size: 16px;\n");
      out.write("  margin: 4px 2px;\n");
      out.write("  cursor: pointer;\n");
      out.write("}\n");
      out.write("\n");
      out.write("</style>\n");
      out.write("<center>\n");
      out.write("<div id=\"menuContainer\">\n");
      out.write("\t<button id=\"bt\" onclick=\"fighterPageMove()\">선수 정보</button> &emsp;&emsp;\n");
      out.write("\t<button id=\"bt\" onclick=\"boardPageMove()\">결투영상</button>&emsp;&emsp; \n");
      out.write("\t<span id=\"title\"> KeyWar </span> &emsp;&emsp;\n");
      out.write("\t<button id=\"bt\" onclick=\"gymInfoPageMove()\">체육관</button>&emsp;&emsp;\n");
      out.write("\t<button id=\"bt\" onclick=\"freeBoardPageMove()\">자유게시판</button>\n");
      out.write("</div>\n");
      out.write("</center>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}