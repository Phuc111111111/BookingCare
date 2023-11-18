package bookingcare.security.Jwt.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;



import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenFilter extends OncePerRequestFilter {
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getJwt(request);
            if(token !=null &&jwtProvider.validateToken(token)){
                String username = jwtProvider.getUerNameFromToken(token);//giải mã token JWT và trích xuất tên người dùng từ mã token
                
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);//để tải thông tin về người dùng từ cơ sở dữ liệu,UserDetails chứa thông tin xác thực của người dùng
                
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken( // Đối tượng authenticationToken sẽ đại diện cho quá trình xác thực của người dùng.
                        userDetails, null, userDetails.getAuthorities()); 
                
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));//đặt thông tin bổ sung về quá trình xác thực (như địa chỉ IP và thông tin trình duyệt) vào đối tượng authenticationToken.
                
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);// Khi thông tin xác thực đã được đặt vào SecurityContextHolder, người dùng sẽ có quyền truy cập các phần bảo mật của ứng dụng và sẽ được xác định là người dùng đã đăng nhập khi gửi các yêu cầu trong phiên làm việc của họ
            }
        } catch (Exception e){
            logger.error("Can't set user authentication -> Message: {}",e);
        }
        filterChain.doFilter(request,response);
    }
    private String getJwt(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if(authHeader !=null && authHeader.startsWith("Bearer")){
            return authHeader.replace("Bearer", "");
        }
        return null;
    }
}
