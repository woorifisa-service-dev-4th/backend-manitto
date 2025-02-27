package manitto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manitto.servlet.listener.DBConnectionPoolListener;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet("/db-status")
public class DBPoolStatusServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        try {
            DataSource ds = (DataSource) getServletContext().getAttribute("DBDataSource");

            if (ds == null) {
                out.println("❌ DataSource가 설정되지 않았습니다.");
                return;
            }

            // ✅ Tomcat JDBC Connection Pool 상태 조회
            PoolConfiguration poolConfig = ds.getPoolProperties();

            out.println("✅ Tomcat JDBC Connection Pool 상태:");
            out.println("-------------------------------------");
            out.println("최대 커넥션 개수 (maxTotal): " + poolConfig.getMaxActive());
            out.println("현재 사용 중인 커넥션 개수 (numActive): " + ds.getNumActive());
            out.println("유휴 상태 커넥션 개수 (numIdle): " + ds.getNumIdle());
            out.println("최대 유휴 커넥션 개수 (maxIdle): " + poolConfig.getMaxIdle());
            out.println("최소 유휴 커넥션 개수 (minIdle): " + poolConfig.getMinIdle());
            out.println("초기 커넥션 개수 (initialSize): " + poolConfig.getInitialSize());
            out.println("최대 대기 시간 (maxWaitMillis): " + poolConfig.getMaxWait());
            out.println("제거된 커넥션 수 (removeAbandonedTimeout): " + poolConfig.getRemoveAbandonedTimeout());
            out.println("-------------------------------------");

            // ✅ 커넥션 풀에서 커넥션을 가져와 테스트
            try (Connection conn = ds.getConnection()) {
                out.println("✅ 커넥션 풀에서 정상적으로 커넥션을 가져왔습니다.");
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            out.println("❌ 오류 발생: " + e.getMessage());
            e.printStackTrace(out);
        }
    }
}