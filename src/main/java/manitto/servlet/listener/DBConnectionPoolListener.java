package manitto.servlet.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.SQLException;

@WebListener  // 서블릿 컨텍스트 리스너 등록
public class DBConnectionPoolListener implements ServletContextListener {
    public static DataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("✅ Tomcat JDBC Connection Pool 초기화 중...");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("❌ MySQL JDBC 드라이버를 찾을 수 없습니다!", e);
        }

        // 커넥션 풀 설정
        PoolProperties p = new PoolProperties();
        p.setUrl("jdbc:mysql://localhost:3306/ManitoDB?serverTimezone=UTC&characterEncoding=UTF-8");
        p.setDriverClassName("com.mysql.cj.jdbc.Driver");
        p.setUsername("root");
        p.setPassword("1234");
        p.setMaxActive(20);
        p.setMinIdle(5);
        p.setMaxIdle(10);
        p.setInitialSize(5);
        p.setMaxWait(30000);
        p.setRemoveAbandonedTimeout(60);
        p.setRemoveAbandoned(true);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");

        // 커넥션 풀 생성
        dataSource = new DataSource();
        dataSource.setPoolProperties(p);

        // DataSource를 서블릿 컨텍스트에 저장하여 모든 서블릿에서 접근 가능하게 설정
        sce.getServletContext().setAttribute("DBDataSource", dataSource);

        System.out.println("✅ Tomcat JDBC Connection Pool 초기화 완료.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("🛑 Tomcat JDBC Connection Pool 종료 중...");
        if (dataSource != null) {
            dataSource.close();
        }
        System.out.println("🛑 Tomcat JDBC Connection Pool 종료 완료.");
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("❌ DataSource가 아직 초기화되지 않았습니다!");
        }
        return dataSource.getConnection();
    }
}