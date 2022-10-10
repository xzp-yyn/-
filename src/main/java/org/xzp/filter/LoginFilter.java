package org.xzp.filter;

import com.alibaba.fastjson.JSON;
import org.springframework.util.AntPathMatcher;
import org.xzp.common.R;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/9 17:25
 * @Version 1.0
 */
@WebFilter
public class LoginFilter implements Filter {

    private static final String[] pattern=new String[]{
            "/employee/login",
            "/employee/logout",
            "/backend/**"
    };

    private AntPathMatcher pathMatcher=new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest)servletRequest;
        HttpServletResponse response= (HttpServletResponse)servletResponse;

        String uri=request.getRequestURI();
        for(String str:pattern){
            boolean b = pathMatcher.match(str, uri);
            if(b){
                filterChain.doFilter(request,response);
                return;
            }
        }
        if(request.getSession().getAttribute("employee")!=null){
            filterChain.doFilter(request,response);
            return;
        }

        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }
}
