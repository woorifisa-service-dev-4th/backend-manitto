/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/10.1.16
 * Generated at: 2025-02-27 06:46:21 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.jsp.*;
import java.util.Objects;

public final class register_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports,
                 org.apache.jasper.runtime.JspSourceDirectives {

  private static final jakarta.servlet.jsp.JspFactory _jspxFactory =
          jakarta.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("jakarta.servlet");
    _jspx_imports_packages.add("jakarta.servlet.http");
    _jspx_imports_packages.add("jakarta.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.util.Objects");
  }

  private volatile jakarta.el.ExpressionFactory _el_expressionfactory;
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

  public boolean getErrorOnELNotFound() {
    return false;
  }

  public jakarta.el.ExpressionFactory _jsp_getExpressionFactory() {
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

  public void _jspService(final jakarta.servlet.http.HttpServletRequest request, final jakarta.servlet.http.HttpServletResponse response)
      throws java.io.IOException, jakarta.servlet.ServletException {

    if (!jakarta.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
        return;
      }
    }

    final jakarta.servlet.jsp.PageContext pageContext;
    jakarta.servlet.http.HttpSession session = null;
    final jakarta.servlet.ServletContext application;
    final jakarta.servlet.ServletConfig config;
    jakarta.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    jakarta.servlet.jsp.JspWriter _jspx_out = null;
    jakarta.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("    <title>회원가입</title>\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"./css/style.css\">\n");
      out.write("    <!-- 회원가입 관련 AJAX 처리 JS (친구 API 사용 시) -->\n");
      out.write("    <script defer src=\"./assets/signup.js\"></script>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("<div class=\"container\">\n");
      out.write("    <h2>회원가입</h2>\n");
      out.write("\n");
      out.write("    ");

        String error = (String) session.getAttribute("error");
        if (!Objects.isNull(error)) {
    
      out.write("\n");
      out.write("    <p class=\"error-message\" id=\"errorMessage\">");
      out.print( error );
      out.write("</p>\n");
      out.write("    ");

            session.removeAttribute("error");
        }
    
      out.write("\n");
      out.write("\n");
      out.write("    <form id=\"signupForm\">\n");
      out.write("        <input type=\"hidden\" name=\"action\" value=\"register\">\n");
      out.write("        <input type=\"text\" id=\"nickname\" name=\"nickname\" placeholder=\"닉네임을 입력하세요\" required>\n");
      out.write("        <input type=\"email\" id=\"email\" name=\"email\" placeholder=\"이메일을 입력하세요\" required>\n");
      out.write("        <input type=\"password\" id=\"password\" name=\"password\" placeholder=\"비밀번호를 입력하세요\" required>\n");
      out.write("        <input type=\"password\" id=\"confirm-password\" name=\"confirmPassword\" placeholder=\"비밀번호를 다시 입력하세요\" required>\n");
      out.write("        <button type=\"submit\">회원가입</button>\n");
      out.write("    </form>\n");
      out.write("\n");
      out.write("    <p>이미 회원이신가요? <a href=\"login.jsp\">로그인</a></p>\n");
      out.write("</div>\n");
      out.write("<!-- 클라이언트 측 비밀번호 확인 검증 -->\n");
      out.write("<script>\n");
      out.write("    document.querySelector(\"form\").addEventListener(\"submit\", function(event) {\n");
      out.write("        let password = document.getElementById(\"password\").value;\n");
      out.write("        let confirmPassword = document.getElementById(\"confirm-password\").value;\n");
      out.write("        if (password !== confirmPassword) {\n");
      out.write("            alert(\"비밀번호가 일치하지 않습니다.\");\n");
      out.write("            event.preventDefault();\n");
      out.write("        }\n");
      out.write("    });\n");
      out.write("</script>\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof jakarta.servlet.jsp.SkipPageException)){
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
