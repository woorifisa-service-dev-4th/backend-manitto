import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import jakarta.servlet.ServletRegistration;
import jakarta.servlet.ServletContext;
import java.awt.*;
import java.io.File;
import java.net.URI;
import java.util.Map;

public class EmbeddedTomcat {
    public static void main(String[] args) {
        try {
            // 1️⃣ Tomcat 인스턴스 생성
            Tomcat tomcat = new Tomcat();

            // 2️⃣ Connector 설정
            Connector connector = new Connector();
            connector.setPort(8091); // ✅ 포트 설정
            tomcat.setConnector(connector);

            // 3️⃣ 웹 애플리케이션 경로 설정
            String webAppDir = new File("src/main/webapp").getAbsolutePath();
            Context ctx = tomcat.addWebapp("", webAppDir);

            // 4️⃣ 🔥 서블릿이 있는 모든 경로를 Tomcat에 강제로 등록
            String[] paths = {
                    "build/classes/java/main",   // 서블릿 빌드 경로
                    "src/main/java"              // 서블릿 원본 코드 경로
            };

            WebResourceRoot resources = new StandardRoot(ctx);
            for (String path : paths) {
                File dir = new File(path);
                if (dir.exists()) {
                    System.out.println("✅ Adding Servlet Path: " + dir.getAbsolutePath());
                    resources.addPreResources(new DirResourceSet(
                            resources, "/WEB-INF/classes", dir.getAbsolutePath(), "/"
                    ));
                } else {
                    System.out.println("❌ ERROR: " + dir.getAbsolutePath() + " not found!");
                }
            }
            ctx.setResources(resources);

            // 5️⃣ Tomcat 실행
            tomcat.start();
            System.out.println("🚀 Tomcat 내장 서버 시작 중... http://localhost:8091");

            // 6️⃣ 🔥 등록된 서블릿 목록 출력
            printServletMappings(ctx);

            // 7️⃣ 브라우저 자동 실행
            openBrowser("http://localhost:8091");

            // 8️⃣ Tomcat 실행 유지 (종료 방지)
            tomcat.getServer().await();

        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }

    // 🔥 등록된 서블릿 및 URL 매핑을 출력하는 메서드
    private static void printServletMappings(Context ctx) {
        ServletContext servletContext = (ServletContext) ctx.getServletContext();
        System.out.println("\n🛠️ 등록된 서블릿 매핑 목록:");

        Map<String, ? extends ServletRegistration> servletRegistrations = servletContext.getServletRegistrations();
        if (servletRegistrations.isEmpty()) {
            System.out.println("❌ 등록된 서블릿이 없습니다.");
        } else {
            for (Map.Entry<String, ? extends ServletRegistration> entry : servletRegistrations.entrySet()) {
                System.out.println("✅ 서블릿: " + entry.getKey());
                for (String mapping : entry.getValue().getMappings()) {
                    System.out.println("   🔗 매핑 경로: " + mapping);
                }
            }
        }
        System.out.println();
    }

    // 브라우저 실행 메서드
    private static void openBrowser(String url) {
        if (Desktop.isDesktopSupported()) {
            new Thread(() -> {
                try {
                    Desktop.getDesktop().browse(new URI(url));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            System.out.println("❌ 브라우저 자동 실행을 지원하지 않는 환경입니다.");
        }
    }
}
