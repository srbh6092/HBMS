package com.srbh.hbms.jwt;

import com.srbh.hbms.service.jwtUserDetail.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthTokenFilter extends OncePerRequestFilter{
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            //Get JWT from request
            String jwt = parseJwt(request);

            //If JWT is not null and is valid
            if (jwt != null && jwtUtil.validateJwtToken(jwt) ) {

                //Fetch username from JWT claims
                final String username = jwtUtil.getUserNameFromJwtToken(jwt);

                //Generate UserDetails using username
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                //Authenticate user
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                //Set authentication for this request
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //Apply authentication to the context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e){
            logger.error("Cannot set user authentication: {}"+ e);
        }
        filterChain.doFilter(request,response);
    }

    private String parseJwt(HttpServletRequest request) {

        //Getting the value from request header "Authorization"
        String headerAuth = request.getHeader("Authorization");

        //If header has something staring with "Bearer "
        //Remove "Bearer " and return the string
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7,headerAuth.length());
        }

        //Else return null
        return null;
    }
}
